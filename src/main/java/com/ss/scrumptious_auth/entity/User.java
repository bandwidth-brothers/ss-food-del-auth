package com.ss.scrumptious_auth.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import java.sql.Timestamp;
import java.util.*;

@Entity(name = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "BINARY(16)", name = "userId", updatable = false)
    private UUID userId;


    @OneToOne(mappedBy = "user")
	private Customer customer;
    
    @NotBlank
	@Email
    private String email;
    
    @ToString.Exclude
	@EqualsAndHashCode.Exclude
	@NotBlank
    private String password;
	
    private UserRole userRole;

	
	@Column(name="createdAt", nullable = false, updatable = false)
	@CreationTimestamp
	private Timestamp createdAt;
	
	@Column(name="updatedAt", nullable = false)
	@UpdateTimestamp
	private Timestamp updatedAt;
    
	@Builder.Default
    @Transient
    private boolean accountNonExpired = true;

	@Builder.Default
    @Transient
    private boolean accountNonLocked = true;

	@Builder.Default
    @Transient
    private boolean credentialsNonExpired = true;

	@Builder.Default
	private boolean enabled = true;
	
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        HashSet<GrantedAuthority> set = new HashSet<>();
        if (userRole != null){
            GrantedAuthority authority = new SimpleGrantedAuthority(userRole.getRole());
            set.add(authority);
        }
        return set;
    }
    
    @Override
    public String getUsername() {
      return email;
    }
}
