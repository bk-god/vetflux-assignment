package com.bkg.vetflux_assignment.user.domain;

import java.util.List;
import java.util.Optional;

public interface UserReporitory {
    User save(User user);
    long countByLoginId(String loginId);
    Optional<User> findByLoginId(String loginId);
    List<User> findByUserIdNot(long userId);
}
