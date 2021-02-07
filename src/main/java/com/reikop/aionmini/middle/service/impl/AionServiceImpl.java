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
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
        if(keyword.equals("")){
            return Collections.emptyList();
        }
        JSONArray suggest = connector.suggest(JSONConnections.SUGGEST_CHARS, keyword, server);
        List<User> userArrayList = new ArrayList<>();
        for (Object o : suggest) {
            JSONObject object = (JSONObject) o;
            if(object.getInt("serverId") > 10){
                User user = new User();
                user.setUserid(object.getInt("charId"));
                user.setLevel(object.getInt("level"));
                user.setServer(Servers.getServerValue(object.getInt("serverId")));
                user.setClassName(object.getString("className"));
                user.setRaceName(object.getString("raceName"));
                String username = object.getString("charName");
                user.setCharname(username.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", ""));
                userArrayList.add(user);
                userService.putUser(user);
            }
        }
        userArrayList.sort(Comparator.comparingInt(User::getLevel).reversed());
        return userArrayList;
    }

    @Override
    public String getStatus(Status status) {
        String call = connector.status(JSONConnections.STATUS, status.getServer().getServer(), status.getUserid());
        status.setStatus(call);
        statusService.putData(status);
        return call;
    }
}
