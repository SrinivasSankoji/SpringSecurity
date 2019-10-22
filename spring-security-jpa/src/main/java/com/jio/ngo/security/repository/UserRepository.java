package com.jio.ngo.security.repository;

import java.util.Optional;

import com.jio.ngo.security.model.User;

public interface UserRepository 
{
	 Optional<User> findByUserName(String userName);
}
