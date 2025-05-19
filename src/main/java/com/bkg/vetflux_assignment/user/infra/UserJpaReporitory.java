package com.bkg.vetflux_assignment.user.infra;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bkg.vetflux_assignment.user.domain.User;
import com.bkg.vetflux_assignment.user.domain.UserReporitory;

public interface  UserJpaReporitory extends JpaRepository<User, Long>, UserReporitory {
    
}
