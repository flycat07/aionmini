package com.reikop.aionmini.common.service;

import com.reikop.aionmini.work.model.GuildServer;
import com.reikop.aionmini.work.model.Servers;
import com.reikop.aionmini.work.model.Status;

public interface StatusService {

    Status putData(Status status);
    GuildServer mergeServer(String guildId, Servers server);

    GuildServer getServer(String guildId);
}
