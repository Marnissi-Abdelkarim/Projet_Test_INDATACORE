package com.example.indatacore_backend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "appusers")
public class UserEntity implements Serializable {
	private static final long serialVersionUID = 5309393251036110443L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(nullable = false,unique = true)
	private String userId;
	@Column(nullable = false, length = 50,unique = true)
	private String username;
	@Column(nullable = false, length = 120, unique = true)
	private String email;
	@Column(nullable = false)
	private String encryptedPassword;
	@Column(nullable = true)
	private String emailVerificationToken;
	@Column(nullable = false)
	private Boolean emailVerificationStatus = false;
	@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable( 
        name = "users_roles", 
        joinColumns = @JoinColumn(
          name = "user_id", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(
          name = "role_id", referencedColumnName = "id")) 
    private Collection<RoleEntity> roles=new ArrayList<>();
}
