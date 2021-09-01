package com.ss.scrumptious_auth.dao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ss.scrumptious_auth.entity.RestaurantOwner;

public interface RestaurantOwnerRepository extends JpaRepository<RestaurantOwner, UUID>{

}
