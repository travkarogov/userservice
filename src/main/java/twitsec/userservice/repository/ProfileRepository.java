package twitsec.userservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import twitsec.userservice.entity.Profile;

import java.util.Optional;

public interface ProfileRepository extends PagingAndSortingRepository<Profile, Integer> {

    @Override
    @RestResource(exported = false)
    Page<Profile> findAll(Pageable pageable);

    @Override
    @RestResource(exported = false)
    Optional<Profile> findById(Integer integer);
}
