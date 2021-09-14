package com.ss.scrumptious_auth.dao;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ss.scrumptious_auth.entity.Customer;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    //Optional<Customer> findByEmail(String email);
}
