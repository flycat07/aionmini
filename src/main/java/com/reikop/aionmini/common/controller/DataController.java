package com.reikop.aionmini.common.controller;

import com.reikop.aionmini.middle.service.AionService;
import com.reikop.aionmini.work.model.Servers;
import com.reikop.aionmini.work.model.Status;
import com.reikop.aionmini.work.model.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping("/api")
@RestController
public class DataController {

    @Resource(name = "aionServiceImpl")
    private AionService aionService;

    @RequestMapping("/suggest")
    private List<User> suggest(String keyword, @RequestParam(required = false) Servers server) {
        return aionService.suggestCharNames(keyword, server);
    }

    @RequestMapping("/character/{server}/{id}")
    private String characterInfo(@PathVariable("server") Servers server,
                                 @PathVariable("id") int id){
        Status status = new Status();
        status.setServer(server);
        status.setUserid(id);

        return aionService.getStatus(status);
    }

}
