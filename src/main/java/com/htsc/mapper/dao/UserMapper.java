package com.htsc.mapper.dao;


import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {
    public String getUserNameByUserId(int userId);
}
