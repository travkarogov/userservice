package twitsec.userservice.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import twitsec.userservice.model.Role;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotNull
    private String email;

    private String password;

    //TODO: postman testen met role
    private Role role;

    @OneToOne(cascade = CascadeType.ALL)
    private Profile profile;
}
