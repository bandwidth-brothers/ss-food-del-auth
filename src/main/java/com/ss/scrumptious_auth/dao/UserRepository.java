package com.ss.scrumptious_auth.dao;

import java.util.Optional;
import java.util.UUID;

import com.ss.scrumptious.common_entities.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;




@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

}
