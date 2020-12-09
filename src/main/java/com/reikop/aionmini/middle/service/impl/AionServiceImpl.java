package com.reikop.aionmini.middle.service.impl;

import com.reikop.aionmini.middle.service.AionService;
import com.reikop.aionmini.work.JSONConnections;
import com.reikop.aionmini.work.JSONConnector;
import com.reikop.aionmini.work.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AionServiceImpl implements AionService {

    @Autowired
    private JSONConnector connector;

    @Override
    public String suggestCharNames(String keyword) {
        return connector.call(JSONConnections.SUGGEST_CHARS, keyword);
    }
    @Override
    public String getPVPStatus(User user) {
        return connector.call(JSONConnections.PVP, user.getServer(), user.getUserid());
    }
    @Override
    public String getCHARACTERStatus(User user) {
        return connector.call(JSONConnections.CHARACTERS, user.getServer(), user.getUserid());
    }
    @Override
    public String getGLORYPOINTStatus(User user) {
        return connector.call(JSONConnections.GLORYPOINT, user.getServer(), user.getUserid());
    }
    @Override
    public String getINFINITYStatus(User user) {
        return connector.call(JSONConnections.INFINITY, user.getServer(), user.getUserid());
    }
}
