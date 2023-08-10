package com.example.indatacore_backend.services;

import com.example.indatacore_backend.entities.RoleEntity;

public interface AccountService {
	public RoleEntity saveRole(RoleEntity role);
	public void addRoleToUser(String email,String rolename);

}
