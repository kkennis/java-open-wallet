package org.kkennis.walletapis;

import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;

import javax.annotation.Nullable;
import java.io.IOException;

abstract public class APIConnection {
    static HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    static JsonFactory JSON_FACTORY = new JacksonFactory();

    @Nullable
    public HttpRequestFactory requestFactory;

    private HttpRequestFactory buildFactory() {
        if (this.requestFactory == null) {
            this.requestFactory = HTTP_TRANSPORT.createRequestFactory((HttpRequest request) -> {
                request.setParser(new JsonObjectParser(JSON_FACTORY));
            });
        }

        return this.requestFactory;
    }

    // Should be implemented by each API to set site-specific headers if necessary
    void setHeaders(HttpRequest request) {};

    // Makes a request and gets something back
    public HttpResponse doGet(String encodedUrl) throws IOException {
        GenericUrl url = new GenericUrl(encodedUrl);
        HttpRequest request = this.buildFactory().buildGetRequest(url);
        this.setHeaders(request);

        return request.execute();
    }


}
