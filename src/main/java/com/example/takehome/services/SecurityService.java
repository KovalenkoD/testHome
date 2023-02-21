package com.example.takehome.services;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * You can define custom beans like the SecurityService which can be used in the SpEl expressions.
 **/
@Service
public class SecurityService {

	public String username() {
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		if(name.equals("anonymousUser")) {
			return null;
		}
		return name;
	}

}
