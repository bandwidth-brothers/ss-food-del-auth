package com.ss.scrumptious_auth.entity;


import lombok.*;
import lombok.Builder.Default;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import java.time.ZonedDateTime;
import java.util.*;

@Entity
@Table(name ="USER")
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
    @JsonIgnore
    @NotBlank
    private String password;

    @Default
    @JsonIgnore
    @Enumerated(EnumType.STRING)
    private UserRole userRole = UserRole.DEFAULT;

    @Column(name="createdAt", updatable = false)
    @CreationTimestamp
    private ZonedDateTime creationDateTime;

    @UpdateTimestamp
	@Column(name="updatedAt")
    private ZonedDateTime lastModifiedDateTime;

	@Builder.Default
	private boolean accountNonExpired = true;
	@Builder.Default
	private boolean accountNonLocked = true;	
	@Builder.Default
    private boolean credentialsNonExpired = true;
    @Builder.Default
    private boolean enabled = true;
    @Builder.Default
    private boolean confirmed = false;
	
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        HashSet<GrantedAuthority> set = new HashSet<>();
        if (userRole != null) {
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
