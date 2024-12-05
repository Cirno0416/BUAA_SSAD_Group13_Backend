package com.innoshare.mapper;

import com.innoshare.model.po.User;
import com.innoshare.model.po.UserInfo;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;


//继承 BaseMapper<T> 接口，即可使用 MyBatis-Plus 提供的通用 CRUD 操作方法。
@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {

    @Update("UPDATE user_info SET full_name=#{fullName}, email=#{email}, phone_number=#{phoneNumber}, " +
            "institution=#{institution}, nationality=#{nationality}, field_of_study=#{fieldOfStudy}, " +
            "experience=#{experience}, updated_at=now() " +
            "WHERE user_id=#{userId}")
    void updateUserInfo(UserInfo userInfo);

    @Select("SELECT * FROM user_info WHERE user_id=#{userId}")
    UserInfo getUserInfoById(String userId);

    @Update("UPDATE users SET avatar_url=#{avatarURL}, updated_at=now() WHERE user_id=#{userId}")
    void updateAvatar(int userId, String avatarURL);

    @Update("UPDATE users SET is_verified=1 WHERE user_id=#{uid}")
    void verified(int uid);
}