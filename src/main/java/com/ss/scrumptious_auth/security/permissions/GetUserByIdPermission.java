package com.ss.scrumptious_auth.security.permissions;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.security.access.prepost.PreAuthorize;

@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAnyRole('ADMIN', 'DRIVER', 'CUSTOMER', 'EMPLOYEE')"
    + " OR @customerAuthenticationManager.customerIdMatches(authentication, #customerId)")
public @interface GetUserByIdPermission {
}