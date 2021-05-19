package com.reikop.aionmini.work;

import com.reikop.aionmini.work.model.Servers;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.UnirestParsingException;
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

        HttpResponse<String> accept = Unirest.put(uri)
                .header("Accept", "application/json, text/javascript, */*; q=0.01")
                .header("User-Agent", userAgent)
                .header("Content-Type", "application/json")
                .body("{\"keyList\":[\"character_stats\",\"character_equipments\",\"character_abyss\",\"character_stigma\"]}")
//                .socketTimeout(800)
                .asString();
//                .ifFailure(Error.class, r -> r.getBody().printStackTrace())
        Unirest.shutDown();
        if(!accept.getParsingError().isPresent()){
            return accept.getBody();
        }else{
            return "{}";
        }
    }

    public JSONArray suggest(JSONConnections suggestChars, String keyword, Servers server) {
        String format = String.format(suggestChars.getUrl(), keyword, server == null ? "" : server.getServer());
        HttpResponse<JsonNode> response = Unirest
                .get(format)
                .asJson();

        if (!response.getParsingError().isPresent()) {
            JsonNode body = response.getBody();
            if(body.getObject().has("documents")){
                return body.getObject().getJSONArray("documents");
            }else{
                return null;
            }
        }
        return null;
    }
}
