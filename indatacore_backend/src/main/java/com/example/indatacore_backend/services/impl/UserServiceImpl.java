package com.example.indatacore_backend.services.impl;


import com.example.indatacore_backend.entities.RoleEntity;
import com.example.indatacore_backend.entities.UserEntity;
import com.example.indatacore_backend.repositories.RoleRepository;
import com.example.indatacore_backend.repositories.UserRepository;
import com.example.indatacore_backend.services.AccountService;
import com.example.indatacore_backend.services.UserService;
import com.example.indatacore_backend.shared.Utils;
import com.example.indatacore_backend.shared.dtos.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;



@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	AccountService accountService;
	BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
	@Autowired
	Utils util;
	@Override
	public UserDto createUser(UserDto user) {
		System.out.println(user);
		UserEntity checkUser = userRepository.findByEmail(user.getEmail());
		UserEntity checkUser2 = userRepository.findByUsername(user.getUsername());
		if (checkUser != null) {
			throw new RuntimeException("Email Already Exists!");
		}
		if (checkUser2 != null) {
			throw new RuntimeException("Username Already Exists");
		}

		ModelMapper modelMapper = new ModelMapper();
		UserEntity userEntity= modelMapper.map(user, UserEntity.class);
		userEntity.setUserId(util.generateString(32));
		userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		RoleEntity role=roleRepository.findByRoleName("USER");
		if(role==null) { role=accountService.saveRole(new RoleEntity(null,"USER",null)); }
		Set<RoleEntity> roles=new HashSet<>();
		roles.add(role);
		userEntity.setRoles(roles);
		UserEntity newUser = userRepository.save(userEntity);
		UserDto userDto =modelMapper.map(newUser, UserDto.class);
		return userDto;
	}
	@Override
	public UserDto createAdmin(UserDto user) {
		System.out.println(user);
		UserEntity checkUser = userRepository.findByEmail(user.getEmail());
		if (checkUser != null) {
			throw new RuntimeException("Email Already Exists");
		}
		ModelMapper modelMapper = new ModelMapper();
		UserEntity userEntity= modelMapper.map(user, UserEntity.class);
		userEntity.setUserId(util.generateString(32));
		userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		RoleEntity role=roleRepository.findByRoleName("ADMIN");
		if(role==null) { role=accountService.saveRole(new RoleEntity(null,"ADMIN",null)); }
		Collection<RoleEntity> roles=new ArrayList<>();
		roles.add(role);
		userEntity.setRoles(roles);
		UserEntity newUser = userRepository.save(userEntity);
		UserDto userDto =modelMapper.map(newUser, UserDto.class);
		return userDto;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		UserEntity userEntity = userRepository.findByEmail(email);
		
		if(userEntity==null) throw new UsernameNotFoundException(email);
		
		Collection<GrantedAuthority> authorities=new ArrayList<>();
		userEntity.getRoles().forEach(r->{
			authorities.add(new SimpleGrantedAuthority(r.getRoleName()));
		});
		return new User(userEntity.getEmail(),userEntity.getEncryptedPassword(),authorities);
	}

	@Override
	public UserDto getUser(String email) {
		
		UserEntity userEntity = userRepository.findByEmail(email);
		if(userEntity==null) throw new UsernameNotFoundException(email);
		ModelMapper modelMapper = new ModelMapper();
		UserDto userDto= modelMapper.map(userEntity, UserDto.class);//
		return userDto;
	}

	@Override
	public UserDto getUserByUserId(String userId) {
		UserEntity userEntity = userRepository.findByUserId(userId);
		if(userEntity==null) throw new UsernameNotFoundException("user with userId "+userId+" does not exist");
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userEntity,userDto );
		return userDto;
	}

	@Override
	public UserDto updateUser(String userId,UserDto userDto) {
		UserEntity userEntity = userRepository.findByUserId(userId);
		if(userEntity==null) throw new UsernameNotFoundException("user with userId "+userId+" does not exist");

		userEntity.setUsername(userDto.getUsername());
		userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
		UserEntity updatedUser= userRepository.save(userEntity);
		ModelMapper modelMapper = new ModelMapper();
		UserDto userDto2 =modelMapper.map(updatedUser, UserDto.class);
		
		return userDto2;
	}

	@Override
	public void deleteUser(String userId) {
		UserEntity userEntity = userRepository.findByUserId(userId);
		if(userEntity==null) throw new UsernameNotFoundException("user with userId "+userId+" does not exist");
		userRepository.delete(userEntity);
		
	}

	@Override
	public List<UserDto> getUsers(int page, int limit,String search) {
		if(page>0) page -=1;
		
		List<UserDto> usersDto = new ArrayList<UserDto>();
		Pageable pageableRequest =PageRequest.of(page, limit);
		Page<UserEntity> userPage ;
		
		if (search.isEmpty()) {
			 userPage = userRepository.findAll(pageableRequest);
		} else {
			 userPage = userRepository.findByUsernameIgnoreCaseContaining(pageableRequest,search);
		}
		
		List<UserEntity> userEntities = userPage.getContent();
		for(UserEntity userEntity:userEntities) {
			
			ModelMapper modelMapper=new ModelMapper();
			UserDto userDto= modelMapper.map(userEntity, UserDto.class);
			
			usersDto.add(userDto);
		}
		return usersDto;
	}


	@Override
	public UserEntity findUserByEmail(String email) {
		UserEntity userEntity = userRepository.findByEmail(email);
		if(userEntity==null) throw new UsernameNotFoundException("user with this email does not exist");
		
		return userEntity;
	}

}
