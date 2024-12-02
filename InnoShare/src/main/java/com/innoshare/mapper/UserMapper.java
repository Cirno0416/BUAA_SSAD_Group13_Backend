package com.innoshare.mapper;

import com.innoshare.model.domain.User;
import com.innoshare.model.request.UserRequest;


import com.innoshare.model.response.UserResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;


//继承 BaseMapper<T> 接口，即可使用 MyBatis-Plus 提供的通用 CRUD 操作方法。
@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {

    @Select("SELECT")
    UserResponse findUserInfoById(@Param("userId") String userId);
}