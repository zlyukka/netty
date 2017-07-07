package com.konex.netty.dao;

import com.konex.netty.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by vitaliy on 06.07.2017.
 */
public interface UserDao extends JpaRepository<User, Long> {
}
