package com.reikop.aionmini.work;

import org.springframework.http.HttpMethod;

public enum JSONConnections {

    SUGGEST_CHARS("https://searchsuggest.plaync.com/suggest/aion/v1.0/aion?query=%s&page_size=20"),
    CHARACTERS("https://api-aion.plaync.com/game/v2/live/characters/server/%s/id/%s"),
    GLORYPOINT("https://api-aion.plaync.com/game/v2/live/ranking/glorypoint/season/servers/%s/characters/%s"),
    PVP("https://api-aion.plaync.com/webcontents/v2/ranking/pvp/field/season/servers/%s/characters/%s"),
    INFINITY("https://api-aion.plaync.com/game/v2/live/ranking/infinity/season/servers/%s/characters/%s");


    private final String url;

    JSONConnections(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
