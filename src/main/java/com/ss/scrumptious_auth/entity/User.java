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
@Table(name ="user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements UserDetails {

    // ! having @column(name = "userId") will invoke the springnamingstrategy and change the actual mysql table to user_id,
    // ! keeping everything lowercase will fix this issue

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "BINARY(16)", name = "userid", updatable = false)
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
    @Column(name = "userrole")
    private UserRole userRole = UserRole.DEFAULT;

    @Column(name="createdat", updatable = false)
    @CreationTimestamp
    private ZonedDateTime creationDateTime;

    @UpdateTimestamp
	@Column(name="updatedat")
    private ZonedDateTime lastModifiedDateTime;

	@Builder.Default
    @Column(name = "accountnonexpired")
	private boolean accountNonExpired = true;

	@Builder.Default
    @Column(name = "accountnonlocked")
	private boolean accountNonLocked = true;	

	@Builder.Default
    @Column(name = "credentialsnonexpired")
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
