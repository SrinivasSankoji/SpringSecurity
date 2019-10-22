package com.jio.ngo.security.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jio.ngo.security.model.User;

@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository
{
	@Autowired
	@Qualifier("primaryJdbcTemplate")
    private JdbcTemplate primaryJdbcTemplate;

	@Override
	public Optional<User> findByUserName(String userName) 
	{
		String hql="SELECT USERNAME,PASSWORD,ENABLED,ROLES FROM USERS WHERE USERNAME = ?";
		RowMapper<User> rowMapper = new BeanPropertyRowMapper<User>(User.class);
		User user = primaryJdbcTemplate.queryForObject(hql, rowMapper,userName);
		return Optional.of(user);
	}

}
