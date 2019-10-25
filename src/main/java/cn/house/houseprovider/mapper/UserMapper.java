package cn.house.houseprovider.mapper;

import cn.house.houseprovider.pojo.User;

import java.util.List;

public interface UserMapper {

    void saveAll(List list) throws  Exception;

    List<User> findAll() throws Exception;

}
