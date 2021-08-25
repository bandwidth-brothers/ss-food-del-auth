package com.smoothstack.scrumptious_auth.entity;

import java.sql.Date;

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


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customer")
@Builder
public class Customer {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "customerId", updatable = false)
	private Integer customerId;
	
	@OneToOne
	@JoinColumn(name = "userId", referencedColumnName = "userId")
	private User user;
	
	@NotBlank
	@Column(name = "firstName", nullable = false)
	private String firstName;
	
	@NotBlank
	@Column(name = "lastName", nullable = false)
	private String lastName;
	
	@NotBlank
	@Column(name = "phone", nullable = false)
	private String phone;
	
	@NotBlank
	@Column(name = "email", nullable = false)
	private String email;
	
	//@NotBlank
	@Column(name = "dob")//, nullable = false)
	private Date dob;
	
	@Builder.Default
	private Integer loyaltyPoints = 0;
	
}
