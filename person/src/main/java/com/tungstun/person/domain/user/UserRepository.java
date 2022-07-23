package com.tungstun.person.domain.user;

import com.tungstun.common.persistence.CrudRepositoryFragment;

import java.util.Optional;

public interface UserRepository extends CrudRepositoryFragment<User, Long> {
    Optional<User> findByIdAndBarId(Long id, Long barId);
}
