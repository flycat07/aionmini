package com.reikop.aionmini.common.service;

import com.reikop.aionmini.common.repo.UserRepo;
import com.reikop.aionmini.work.model.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepo repo;

    @Override
    public User putUser(User user) {
        User byServerAndUserid = repo.findByServerAndUserid(user.getServer(), user.getUserid());
        if(byServerAndUserid != null){
            byServerAndUserid.setCharname(user.getCharname());
            return repo.save(byServerAndUserid);
        }else{
            return repo.save(user);
        }
    }
//
//    @Override
//    public Iterable<User> putUser(Iterable<User> user) {
//        return repo.saveAll(user);
//    }
}
