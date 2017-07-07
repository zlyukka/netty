package com.konex.netty.service;

import com.konex.netty.entity.User;

/**
 * Created by vitaliy on 06.07.2017.
 */
public interface UserService {
    User getUserById(Long id);
}
