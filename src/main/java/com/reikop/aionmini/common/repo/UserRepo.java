package com.reikop.aionmini.common.repo;

import com.reikop.aionmini.work.model.Servers;
import com.reikop.aionmini.work.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User, Integer> {

    User findByServerAndUserid(Servers server, int userid);

}
