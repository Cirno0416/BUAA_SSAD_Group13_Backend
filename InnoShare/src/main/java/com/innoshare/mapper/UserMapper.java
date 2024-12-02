package com.innoshare.mapper;

import com.innoshare.model.domain.User;
import com.innoshare.model.domain.UserInfo;


import com.innoshare.model.response.UserResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;


//继承 BaseMapper<T> 接口，即可使用 MyBatis-Plus 提供的通用 CRUD 操作方法。
@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {

    @Update("UPDATE user_info set full_name=#{fullName}, email=#{email}, phone_number=#{phoneNumber}, " +
            "institution=#{institution}, nationality=#{nationality}, field_of_study=#{fieldOfStudy}, experience=#{experience} " +
            "WHERE user_id=#{userId}")
    void updateUserInfo(UserInfo userInfo);

    @Select("SELECT * FROM user_info where user_id=#{userId}")
    UserInfo getUserInfoById(String userId);
}