package com.reikop.aionmini.common.controller;

import com.reikop.aionmini.common.service.ItemService;
import com.reikop.aionmini.middle.service.AionService;
import com.reikop.aionmini.work.model.Item;
import com.reikop.aionmini.work.model.Servers;
import com.reikop.aionmini.work.model.Status;
import com.reikop.aionmini.work.model.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("/api")
@RestController
public class DataController {

    @Resource(name = "aionServiceImpl")
    private AionService aionService;

    @Resource(name = "itemServiceImpl")
    private ItemService itemService;

    @RequestMapping("/suggest")
    private List<User> suggest(String keyword, @RequestParam(required = false) Servers server) {
        return aionService.suggestCharNames(keyword, server);
    }
    @RequestMapping("/items")
    private List<Item> suggestitems(String keyword) {
        return itemService.findItem(keyword);
    }
//    @RequestMapping("/make")
//    private void suggestitems() {
//        itemService.make();
//    }

    @RequestMapping("/character/{server}/{id}")
    private String characterInfo(HttpServletRequest request,
                                 @PathVariable("server") Servers server,
                                 @PathVariable("id") int id){
        Status status = new Status();
        status.setServer(server);
        status.setUserid(id);
        String userAgent = request.getHeader("User-Agent");
        return aionService.getStatus(userAgent, status);
    }

}
