package com.reikop.aionmini.middle.service;

import com.reikop.aionmini.work.model.Servers;
import com.reikop.aionmini.work.model.Status;
import com.reikop.aionmini.work.model.User;

import java.util.List;

public interface AionService {

    List<User> suggestCharNames(String keyword, Servers server);

    String getStatus(String userAgent, Status status);

}
