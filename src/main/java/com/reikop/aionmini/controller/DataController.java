package com.reikop.aionmini.controller;

import com.reikop.aionmini.middle.service.AionService;
import com.reikop.aionmini.work.model.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
@RequestMapping("/api")
@RestController
public class DataController {

    @Resource(name = "aionServiceImpl")
    private AionService aionService;

    @RequestMapping("/suggest")
    private String suggest(String keyword){
        return aionService.suggestCharNames(keyword);
    }

    @RequestMapping("/character/{server}/{id}")
    private String characterInfo(@PathVariable("server") int server,
                                 @PathVariable("id") int id){
        User user = new User();
        user.setServer(server);
        user.setUserid(id);
        String characterStatus = aionService.getCHARACTERStatus(user);
        String glorypointStatus = aionService.getGLORYPOINTStatus(user);
        String infinityStatus = aionService.getINFINITYStatus(user);
        String pvpStatus = aionService.getPVPStatus(user);

        return String.format("{\"char\": %s, \"glory\": %s, \"infinity\": %s, \"pvp\": %s}"
        , characterStatus
        , glorypointStatus
        , infinityStatus
        , pvpStatus);
    }

}
