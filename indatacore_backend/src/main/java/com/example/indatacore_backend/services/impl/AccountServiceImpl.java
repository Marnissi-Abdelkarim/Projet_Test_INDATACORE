package com.example.indatacore_backend.services.impl;

import com.example.indatacore_backend.entities.RoleEntity;
import com.example.indatacore_backend.entities.UserEntity;
import com.example.indatacore_backend.repositories.RoleRepository;
import com.example.indatacore_backend.repositories.UserRepository;
import com.example.indatacore_backend.services.AccountService;
import com.example.indatacore_backend.shared.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	UserRepository userRepository;
	@Override
	public RoleEntity saveRole(RoleEntity role) {
		return roleRepository.save(role);
	}

	@Override
	public void addRoleToUser(String email, String rolename) {
		UserEntity user=userRepository.findByEmail(email);
		RoleEntity role=roleRepository.findByRoleName(rolename);
		if(role==null) { role=saveRole(new RoleEntity(null,rolename,null)); }
		user.getRoles().add(role);
	}
	
	public static boolean hasRole (String roleName)
	{
	    return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
	            .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(roleName));
	}
	
	public static boolean isUser() {
		return hasRole(Constants.USER_ROLE);
	}
	public static boolean isAdmin() {
		return hasRole(Constants.ADMIN_ROLE);
	}
	
	public static boolean isSameToCurrentUser(String email) {
		String currentUserEmail=SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		if(currentUserEmail.equals(email)) {return true;}
		else {return false;}
	}
	public static Set<String> currentUserRoles(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Set<String> roles = authentication.getAuthorities().stream()
		     .map(r -> r.getAuthority()).collect(Collectors.toSet());
		return roles;
	}
	
	public static String currentUserEmail() {
		return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
	}
}
