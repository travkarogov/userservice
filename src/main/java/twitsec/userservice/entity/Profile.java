package twitsec.userservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import twitsec.userservice.model.Tweet;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "profile")
@AllArgsConstructor
@NoArgsConstructor
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;

    @ManyToMany
    private List<Profile> following;

    @ManyToMany
    private List<Profile> followers;

    private String bio;

    private String websiteUrl;


    private Date createdAt = new Date();

    @Transient
    private Set<Tweet> tweets;
}
