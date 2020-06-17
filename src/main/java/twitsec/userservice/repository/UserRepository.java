package twitsec.userservice.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import twitsec.userservice.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Integer> {

    @Override
    @RestResource(exported = false)
    Optional<User> findById(Integer integer);

    @RestResource(exported = false)
    Optional<User> findByEmail(String email);
}
