package com.reikop.aionmini.work;

import com.reikop.aionmini.work.model.Servers;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

@Component
public class JSONConnector {

    @PreDestroy
    private void close(){
    }

    public String status(JSONConnections connections, String userAgent, Object... params) {
        String uri = connections.getUrl();
        if(params != null && params.length > 0){
            uri = String.format(uri, params);
        }

        String accept = Unirest.put(uri)
                .header("Accept", "application/json, text/javascript, */*; q=0.01")
                .header("User-Agent", userAgent)
                .header("Content-Type", "application/json")
                .body("{\"keyList\":[\"character_stats\",\"character_equipments\",\"character_abyss\",\"character_stigma\"]}")
//                .socketTimeout(800)
                .asString()
//                .ifFailure(Error.class, r -> r.getBody().printStackTrace())
                .getBody();

        Unirest.shutDown();


        return accept;
    }

    public JSONArray suggest(JSONConnections suggestChars, String keyword, Servers server) {
        String format = String.format(suggestChars.getUrl(), keyword, server == null ? "" : server.getServer());
        JsonNode body = Unirest.get(format).asJson().getBody();
        if(body.getObject().has("documents")){
            return body.getObject().getJSONArray("documents");
        }else{
            return null;
        }
    }
}
