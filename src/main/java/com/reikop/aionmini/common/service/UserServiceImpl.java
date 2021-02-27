package com.reikop.aionmini.common.service;

import com.reikop.aionmini.common.repo.UserRepo;
import com.reikop.aionmini.work.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepo repo;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User putUser(User user) {
//        User byServerAndUserid = repo.findByServerAndUserid(user.getServer(), user.getUserid());
//        if(byServerAndUserid != null){
//            byServerAndUserid.setCharname(user.getCharname());
//        }
//        return entityManager.merge(user);
    return user;
//        if(byServerAndUserid != null){
//            byServerAndUserid.setCharname(user.getCharname());
//            return repo.save(byServerAndUserid);
//        }else{
//            return repo.save(user);
//        }
//        entityManager.merge(user);

    }
//
//    @Override
//    public Iterable<User> putUser(Iterable<User> user) {
//        return repo.saveAll(user);
//    }
}
