package com.reikop.aionmini.work;

import org.springframework.http.HttpMethod;

public enum JSONConnections {

    SUGGEST_CHARS("https://api-aion.plaync.com/search/v1/characters?classId=&pageNo=1&pageSize=50&query=%s&raceId=&serverId=%s"),
//    CHARACTERS("https://api-aion.plaync.com/game/v2/live/characters/server/%s/id/%s"),
//    GLORYPOINT("https://api-aion.plaync.com/game/v2/live/ranking/glorypoint/season/servers/%s/characters/%s"),
    STATUS("https://api-aion.plaync.com/game/v2/classic/merge/server/%s/id/%s");
//    INFINITY("https://api-aion.plaync.com/game/v2/live/ranking/infinity/season/servers/%s/characters/%s");


    private final String url;

    JSONConnections(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
