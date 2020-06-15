package twitsec.userservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import twitsec.userservice.entity.User;

import java.util.Optional;

public interface UserRepository extends PagingAndSortingRepository<User, Integer> {

    @Override
    @RestResource(exported = false)
    Page<User> findAll(Pageable pageable);

    @Override
    @RestResource(exported = false)
    Optional<User> findById(Integer integer);
}
