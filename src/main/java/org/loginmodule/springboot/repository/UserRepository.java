package org.loginmodule.springboot.repository;

import org.loginmodule.springboot.domain.Users;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<Users, Long>{
	Users findByEmail(String email);
	Users findByUsername(String username);
}
