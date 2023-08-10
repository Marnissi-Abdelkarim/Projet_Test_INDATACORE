package com.example.indatacore_backend.repositories;

import com.example.indatacore_backend.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {
	UserEntity findByEmail(String email);
	UserEntity findByUserId(String userId);
	UserEntity findByUsername(String username);

	Page<UserEntity> findAll(Pageable page);
	Page<UserEntity> findByUsernameIgnoreCaseContaining(Pageable pageable,String name);
}
