package com.innoshare.mapper;

import com.innoshare.model.po.Admin;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AdminMapper {

    @Insert("INSERT INTO admin(username, password, salt) VALUES(#{username}, #{password}, #{salt})")
    void register(Admin admin);

    @Select("SELECT * FROM admin WHERE username=#{username}")
    Admin getAdminByUsername(String username);
}
