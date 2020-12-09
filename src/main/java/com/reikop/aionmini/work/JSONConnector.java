package com.reikop.aionmini.work;

import org.apache.http.client.methods.CloseableHttpResponse;
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
        request.setHeader("Origin", "https://aion.plaync.com");
        request.setHeader("Referer", "https://aion.plaync.com/");
        request.setHeader("Host", "api-aion.plaync.com");
        request.setHeader("Sec-Fetch-Dest", "empty");
        request.setHeader("Sec-Fetch-Mode", "cors");
        request.setHeader("Sec-Fetch-Site", "same-site");
        request.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 11_0_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.198 Safari/537.36");

        CloseableHttpClient client = HttpClientBuilder.create().build();
        try {
            CloseableHttpResponse response = client.execute(request);
            System.out.println(response);
            InputStream content = response.getEntity().getContent();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            FileCopyUtils.copy(content, byteArrayOutputStream);
            return new String(byteArrayOutputStream.toByteArray(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
