package cn.house.houseprovider.service;

import cn.house.houseprovider.mapper.UserMapper;
import cn.house.houseprovider.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public void saveAll(List list) throws Exception {

        userMapper.saveAll(list);
    }

    @Override
    public List<User> findAll() throws Exception {
        return userMapper.findAll();
    }
}
