package com.example.indatacore_backend.repositories;

import com.example.indatacore_backend.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public  interface RoleRepository extends JpaRepository<RoleEntity,Long> {
	RoleEntity findByRoleName(String rolename);
}
