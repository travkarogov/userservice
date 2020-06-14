package twitsec.userservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import twitsec.userservice.entity.User;
import twitsec.userservice.repository.UserRepository;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@CrossOrigin("*")
public class UserController {

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private final UserRepository userRepository;

    @PostMapping("/create")
    public ResponseEntity<User> create(@RequestBody User user) {
        User createdUser = userRepository.save(user);
        if (createdUser == null){
            return ResponseEntity.notFound().build();
        }
        else{
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdUser.getId()).toUri();
            return ResponseEntity.created(uri).body(createdUser);
        }
    }

    @GetMapping("/{id}")
    public Optional<User> findById(@PathVariable("id") int id){
        var user = userRepository.findById(id);

        return user;
    }
}
