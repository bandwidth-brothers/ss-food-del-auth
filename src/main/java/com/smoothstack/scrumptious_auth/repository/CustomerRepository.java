package com.smoothstack.scrumptious_auth.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smoothstack.scrumptious_auth.entity.Customer;
import com.smoothstack.scrumptious_auth.entity.User;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    Optional<User> findByEmail(String email);
}
