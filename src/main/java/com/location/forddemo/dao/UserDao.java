package com.location.forddemo.dao;


import com.location.forddemo.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<User, Integer> {
}
