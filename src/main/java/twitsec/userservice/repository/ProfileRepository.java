package twitsec.userservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import twitsec.userservice.entity.Profile;

import java.util.Optional;

public interface ProfileRepository extends CrudRepository<Profile, Integer> {

    @Override
    @RestResource(exported = false)
    Optional<Profile> findById(Integer integer);
}
