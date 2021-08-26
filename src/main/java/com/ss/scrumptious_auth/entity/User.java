package com.ss.scrumptious_auth.entity;

import lombok.*;
import lombok.Builder.Default;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.ZonedDateTime;
import java.util.*;

@Entity
@Table(name ="USER")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(columnDefinition = "BINARY(16)")
//    private UUID id;
//
//
//    private String email;
//    private String password;
//    private String username;
//    @Builder.Default
//    private boolean enabled = true;
//    private UserRole userRole;
//
//    @Transient
//    @Builder.Default
//    private boolean accountNonExpired = true;
//
//    @Transient
//    @Builder.Default
//    private boolean accountNonLocked = true;
//
//    @Transient
//    @Builder.Default
//    private boolean credentialsNonExpired = true;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @NotBlank
    @Email
    private String email;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @NotBlank
    private String password;

    @Default
    @Enumerated(EnumType.STRING)
    private UserRole userRole = UserRole.DEFAULT;

    @Column(updatable = false)
    @CreationTimestamp
    private ZonedDateTime creationDateTime;

    @UpdateTimestamp
    private ZonedDateTime lastModifiedDateTime;

    @Default
    private boolean accountNonExpired = true;
    @Default
    private boolean accountNonLocked = true;
    @Default
    private boolean credentialsNonExpired = true;
    @Default
    private boolean enabled = true;
    @Default
    private boolean confirmed = false;


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
