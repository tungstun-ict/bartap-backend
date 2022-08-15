package com.tungstun.security.domain.user;

import com.tungstun.common.persistence.CrudRepositoryFragment;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepositoryFragment<User, Long> {
    void delete(User id);

    Optional<User> findByUsername(String username);
}
