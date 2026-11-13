package com.ssm.file.mapper;

import com.ssm.file.entity.SysUser;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {

    SysUser findByUsername(@Param("username") String username);

    SysUser findById(@Param("id") Integer id);
}
