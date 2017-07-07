package com.konex.netty.service;

import com.konex.netty.dao.UserDao;
import com.konex.netty.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by vitaliy on 06.07.2017.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;

    @Override
    public User getUserById(Long id) {
        return userDao.findOne(id);
    }
}
