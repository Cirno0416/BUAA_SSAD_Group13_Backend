package com.innoshare.mapper;

import com.innoshare.model.domain.User;
import com.innoshare.model.request.UserRequest;


import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;


//继承 BaseMapper<T> 接口，即可使用 MyBatis-Plus 提供的通用 CRUD 操作方法。
@Repository
public interface UserMapper extends BaseMapper<User> {

	int insert(UserRequest user);
}