package com.reikop.aionmini.middle.service.impl;

import com.google.gson.JsonObject;
import com.reikop.aionmini.common.service.StatusService;
import com.reikop.aionmini.common.service.UserService;
import com.reikop.aionmini.middle.service.AionService;
import com.reikop.aionmini.work.JSONConnections;
import com.reikop.aionmini.work.JSONConnector;
import com.reikop.aionmini.work.model.Servers;
import com.reikop.aionmini.work.model.Status;
import com.reikop.aionmini.work.model.User;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class AionServiceImpl implements AionService {

    private final JSONConnector connector;

    public AionServiceImpl(JSONConnector connector) {
        this.connector = connector;
    }

    @Resource(name="statusServiceImpl")
    private StatusService statusService;
    @Resource(name="userServiceImpl")
    private UserService userService;


    @Override
    public List<User> suggestCharNames(String keyword, Servers server){
        JSONArray suggest = connector.suggest(JSONConnections.SUGGEST_CHARS, keyword, server);
        List<User> userArrayList = new ArrayList<>();
        for (Object o : suggest) {
            User user = new User();
            JSONObject object = (JSONObject) o;
            user.setUserid(object.getInt("charId"));
            user.setServer(Servers.getServerValue(object.getInt("serverId")));
            String username = object.getString("charName");
            user.setCharname(username.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", ""));
            userArrayList.add(user);
            userService.putUser(user);
        }
        return userArrayList;
    }

    @Override
    public String getStatus(Status status) {
        String call = connector.status(JSONConnections.STATUS, status.getServer(), status.getUserid());
        status.setStatus(call);
        statusService.putData(status);
        return call;
    }
}
