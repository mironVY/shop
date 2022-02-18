package com.mironovvlad.shop.users;

import com.mironovvlad.shop.exception.UserExistException;
import com.mironovvlad.shop.rest.dto.User;

public interface UserService {
    void addUser(User user) throws UserExistException;
    Long getUserId(String number);
}
