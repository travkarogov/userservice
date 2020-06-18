package twitsec.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import twitsec.userservice.controller.exception.NoTokenProvidedException;
import twitsec.userservice.controller.exception.NotAuthorizedException;
import twitsec.userservice.entity.User;
import twitsec.userservice.model.Role;
import twitsec.userservice.repository.UserRepository;
import twitsec.userservice.service.JwtTokenComponent;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@CrossOrigin("*")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final JwtTokenComponent tokenComponent;

    @PostMapping("/create")
    public ResponseEntity<User> create(@RequestHeader("Authorization") final String token, @RequestBody final User user) {
        if(token.isEmpty()) throw new NoTokenProvidedException("Token is empty");

        if(tokenComponent.validateJwt(token) && tokenComponent.getRoleFromToken(token) == Role.COMMUNICATION){
            User createdUser = userRepository.save(user);

            if (user.getEmail() != null && createdUser.getEmail().equals(user.getEmail())) {
                URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdUser.getId()).toUri();
                return ResponseEntity.created(uri).body(createdUser);
            }
        }

        throw new NotAuthorizedException("Not authorized to perform this action");
    }

    @GetMapping("/{id}")
    public Optional<User> findById(@RequestHeader("Authorization") final String token, @PathVariable("id") final int id){
        if(token.isEmpty()) throw new NoTokenProvidedException("Token is empty");

        if(tokenComponent.validateJwt(token) &&
                tokenComponent.getRoleFromToken(token) == Role.COMMUNICATION ){
            return userRepository.findById(id);
        }
        else if(tokenComponent.validateJwt(token) && tokenComponent.getUserIdFromToken(token) == id){
            return userRepository.findById(id);
        }

        throw new NotAuthorizedException("Not authorized to perform this action");
    }

    @GetMapping("/email")
    public Optional<User> findByEmail(@RequestHeader("Authorization") final String token, @RequestParam("email") final String email){
        if(token.isEmpty()) throw new NoTokenProvidedException("Token is empty");

        if(tokenComponent.validateJwt(token) &&
                tokenComponent.getRoleFromToken(token) == Role.COMMUNICATION ){
            if(userRepository.findByEmail(email).isPresent()){
                return userRepository.findByEmail(email);
            }
            else{
                return Optional.empty();
            }
        }
        else if(tokenComponent.validateJwt(token) && tokenComponent.getEmailFromToken(token).equals(email)){
            if(userRepository.findByEmail(email).isPresent()){
                return userRepository.findByEmail(email);
            }
        }

        throw new NotAuthorizedException("Not authorized to perform this action");
    }

    @DeleteMapping("/{id}")
    public String delete(@RequestHeader("Authorization") final String token, @PathVariable("id") final int id){
        if(token.isEmpty()) throw new NoTokenProvidedException("Token is empty");

        if(tokenComponent.validateJwt(token) && tokenComponent.getUserIdFromToken(token) == id){
            //TODO: send message to tweetservice to delete all tweets based on profile id (make queue; twitsec.tweet.delete)
            //TODO: send message to authservice to delete TOTP entry (make queue; twitsec.auth.delete)
            //TODO: delete user and profile
            return "Account deleted";
        }

        throw new NotAuthorizedException("Not authorized to perform this action");
    }
}
