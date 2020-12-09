package com.reikop.aionmini.middle.service;

import com.reikop.aionmini.work.model.User;

public interface AionService {

    String suggestCharNames(String keyword);

    String getPVPStatus(User user);

    String getCHARACTERStatus(User user);

    String getGLORYPOINTStatus(User user);

    String getINFINITYStatus(User user);
}
