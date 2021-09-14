package com.ss.scrumptious_auth.entity;

import java.sql.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: get rid of everything

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customer")
@Builder
public class Customer {


	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(columnDefinition = "BINARY(16)", name = "customerid", updatable = false)
	private UUID customerId;
	
	@NotBlank
	@Column(name = "firstname", nullable = false)
	private String firstName;
	
	@NotBlank
	@Column(name = "lastname", nullable = false)
	private String lastName;
	
	@NotBlank
	@Column(name = "phone", nullable = false)
	private String phone;
	
	//@NotBlank
	@Column(name = "dob")//, nullable = false)
	private Date dob;
	
	@Builder.Default
	@Column(name = "loyaltypoints", nullable = false)
	private Integer loyaltyPoints = 0;
	
}
