package cn.house.houseprovider.service;

import cn.house.houseprovider.pojo.User;

import java.util.List;

public interface UserService {

    void saveAll(List list) throws  Exception;

    List<User> findAll() throws Exception;
}
