package com.reikop.aionmini.common.service;

import com.reikop.aionmini.common.repo.StatusRepo;
import com.reikop.aionmini.work.model.GuildServer;
import com.reikop.aionmini.work.model.Servers;
import com.reikop.aionmini.work.model.Status;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
@Transactional
public class StatusServiceImpl implements StatusService {

    @Resource
    private StatusRepo repo;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Status putData(Status status) {
        return repo.save(status);
    }

    @Override
    public GuildServer mergeServer(String guildId, Servers server) {
        GuildServer guildServer = new GuildServer();
        guildServer.setGuildId(guildId);
        guildServer.setServers(server);
        return entityManager.merge(guildServer);
    }

    @Override
    public GuildServer getServer(String guildId) {
        return entityManager.find(GuildServer.class, guildId);
    }
}
