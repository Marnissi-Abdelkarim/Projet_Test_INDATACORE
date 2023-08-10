package com.example.indatacore_backend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "roles")
public class RoleEntity implements Serializable {
	private static final long serialVersionUID = -6222890415447301192L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String roleName;
	@ManyToMany(fetch = FetchType.EAGER, mappedBy ="roles")
	private Collection<UserEntity> users =new ArrayList<>();
}
