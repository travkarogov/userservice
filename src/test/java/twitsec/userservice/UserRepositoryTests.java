package twitsec.userservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import twitsec.userservice.entity.Profile;
import twitsec.userservice.entity.User;
import twitsec.userservice.repository.ProfileRepository;
import twitsec.userservice.repository.UserRepository;

import java.util.HashSet;

@DataJpaTest
@ExtendWith(SpringExtension.class)
class UserRepositoryTests {

    @Autowired
    private TestEntityManager testEntityManager;

    private static final int userId = 1;

    private static int userCount;

    @BeforeEach
    public void setup(){
        var users = new HashSet<User>();

        users.add(new User(userId, "test1@test.com", new Profile("test1", "Le test bio")));
        users.add(new User(userId, "test2@test.com", new Profile("test2", "Le test bio")));
        users.add(new User(userId, "test3@test.com", new Profile("test3", "Le test bio")));

        for (var user : users){
            testEntityManager.persist(user);
        }

        testEntityManager.flush();

        userCount = users.size();
    }
}
