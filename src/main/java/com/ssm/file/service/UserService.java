package com.ssm.file.service;

import com.ssm.file.entity.SysUser;

public interface UserService {

    /** 登录成功返回用户，失败返回 null。 */
    SysUser login(String username, String password);

    SysUser findById(Integer id);
}
