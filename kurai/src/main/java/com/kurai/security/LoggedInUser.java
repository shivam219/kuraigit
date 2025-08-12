package com.kurai.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
/* Copyright (c) 2023-2024 Enhanced software solution private limited,
 * All rights reserved
 * 
 * @author          
 * Shivam Choudhary  
 * 25-Jan-2024
 */

@Target({ ElementType.PARAMETER ,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@AuthenticationPrincipal
public @interface LoggedInUser {

}
