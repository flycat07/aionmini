package com.reikop.aionmini.work;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import javax.annotation.PreDestroy;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Component
public class JSONConnector {

    public JSONConnector() {

    }

    @PreDestroy
    private void close(){
    }

    public String call(JSONConnections connections, Object... params) {
        String uri = connections.getUrl();
        if(params != null && params.length > 0){
            uri = String.format(uri, params);
        }
        HttpGet request = new HttpGet(uri);
        CloseableHttpClient client = HttpClientBuilder.create().build();
        try {
            InputStream content = client.execute(request).getEntity().getContent();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            FileCopyUtils.copy(content, byteArrayOutputStream);
            return new String(byteArrayOutputStream.toByteArray(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
