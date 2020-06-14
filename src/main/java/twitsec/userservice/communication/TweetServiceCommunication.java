package twitsec.userservice.communication;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Component;
import twitsec.userservice.model.Tweet;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashSet;
import java.util.Set;

@Component
public class TweetServiceCommunication {

    private final HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();

    public Set<Tweet> getTweets(int id){
        var page = 0;
        var size = 25;

        var url = "http://localhost:59501/tweets/search/byProfileId?page=" + page + "&size=" + size + "&profileId=" + id;

        Set<Tweet> tweetSet = new HashSet<Tweet>();

        try {
            HttpRequest httpRequest = HttpRequest.newBuilder().GET()
                    .uri(URI.create(url))
                    .setHeader("content-type", "application/json").build();

            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            JsonObject jsonObject = new Gson().fromJson(response.body(), JsonObject.class);

            JsonObject posts = jsonObject.get("_embedded").getAsJsonObject();
            JsonArray jsonArray = posts.getAsJsonArray("tweets");

            Tweet[] tweetList = new GsonBuilder().create().fromJson(jsonArray, Tweet[].class);

            for (Tweet tweet : tweetList){
                tweetSet.add(tweet);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return tweetSet;
    }
}
