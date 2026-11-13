package com.ssm.file.service.impl;

import com.ssm.file.entity.SysUser;
import com.ssm.file.mapper.UserMapper;
import com.ssm.file.service.UserService;
import com.ssm.file.util.PasswordUtil;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public SysUser login(String username, String password) {
        if (username == null || username.trim().isEmpty() || password == null) {
            return null;
        }
        SysUser user = userMapper.findByUsername(username.trim());
        if (user == null) {
            return null;
        }
        if (!PasswordUtil.matches(password, user.getSalt(), user.getPassword())) {
            return null;
        }
        return user;
    }

    @Override
    public SysUser findById(Integer id) {
        return userMapper.findById(id);
    }
}
