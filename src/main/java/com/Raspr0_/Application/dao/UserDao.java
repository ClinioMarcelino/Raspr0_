package com.Raspr0_.Application.dao;

import com.Raspr0_.Application.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<UserModel, Long> {
}
