package com.ss.scrumptious_auth.entity;


import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
