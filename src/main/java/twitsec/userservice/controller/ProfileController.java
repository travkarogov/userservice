package twitsec.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import twitsec.userservice.communication.TweetServiceCommunication;
import twitsec.userservice.controller.exception.NotAuthorizedException;
import twitsec.userservice.entity.Profile;
import twitsec.userservice.repository.ProfileRepository;
import twitsec.userservice.service.JwtTokenComponent;


import java.util.Optional;

@RestController("ProfileController")
@RequestMapping("/profiles")
@CrossOrigin("*")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileRepository profileRepository;
    private final TweetServiceCommunication tweetServiceCommunication;
    private final JwtTokenComponent tokenComponent;

    @GetMapping("/{id}")
    public Optional<Profile> findById(@RequestHeader("Authorization") final String token, @PathVariable("id") final int profileId){
        if(tokenComponent.validateJwt(token)){
            var profile = profileRepository.findById(profileId);

            profile.ifPresent(value -> value.setTweets(tweetServiceCommunication.getTweets(profileId)));

            return profile;
        }

        throw new NotAuthorizedException("Not authorized to perform this action");
    }
}
