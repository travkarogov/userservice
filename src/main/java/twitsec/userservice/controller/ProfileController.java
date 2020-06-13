package twitsec.userservice.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import twitsec.userservice.entity.Profile;
import twitsec.userservice.model.Tweet;
import twitsec.userservice.repository.ProfileRepository;


import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController("ProfileController")
@RequestMapping("/profiles")
@CrossOrigin("*")
public class ProfileController {

    public ProfileController(ProfileRepository profileRepository){ this.profileRepository = profileRepository; }

    private final ProfileRepository profileRepository;

    private final HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();

    @PostMapping("/create")
    public ResponseEntity<Profile> create(@RequestBody Profile profile) {
        Profile createdProfile = profileRepository.save(profile);

        if(createdProfile == null){
            return ResponseEntity.notFound().build();
        }
        else {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdProfile.getId()).toUri();
            return ResponseEntity.created(uri).body(createdProfile);
        }
    }

    @GetMapping("/{id}")
    public Optional<Profile> findById(@PathVariable("id") int id){
        var profile = profileRepository.findById(id);

        var page = 0;
        var size = 25;

        var url = "http://localhost:59501/tweets/search/byProfileId?page=" + page + "&size=" + size + "&profileId=" + profile.get().getId();

        try {
            HttpRequest httpRequest = HttpRequest.newBuilder().GET()
                    .uri(URI.create(url))
                    .setHeader("content-type", "application/json").build();

            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            JsonObject jsonObject = new Gson().fromJson(response.body(), JsonObject.class);

            JsonObject posts = jsonObject.get("_embedded").getAsJsonObject();
            JsonArray jsonArray = posts.getAsJsonArray("tweets");

            Tweet[] tweetList = new GsonBuilder().create().fromJson(jsonArray, Tweet[].class);

            Set<Tweet> tweetSet = new HashSet<Tweet>();
            for (Tweet tweet : tweetList){
                tweetSet.add(tweet);
            }

            profile.get().setTweets(tweetSet);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return profile;
    }
}
