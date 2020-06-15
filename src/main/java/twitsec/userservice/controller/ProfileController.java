package twitsec.userservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import twitsec.userservice.communication.TweetServiceCommunication;
import twitsec.userservice.entity.Profile;
import twitsec.userservice.repository.ProfileRepository;


import java.net.URI;
import java.util.Optional;

@RestController("ProfileController")
@RequestMapping("/profiles")
@CrossOrigin("*")
public class ProfileController {

    public ProfileController(ProfileRepository profileRepository){ this.profileRepository = profileRepository; }

    private final ProfileRepository profileRepository;

    private TweetServiceCommunication tweetServiceCommunication = new TweetServiceCommunication();

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

       profile.get().setTweets(tweetServiceCommunication.getTweets(id));

        return profile;
    }

    @GetMapping("/")
    public ResponseEntity<Object> healthy(){
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
