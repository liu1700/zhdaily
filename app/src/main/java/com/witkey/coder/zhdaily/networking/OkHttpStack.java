package com.witkey.coder.zhdaily.networking;

import com.android.volley.toolbox.HurlStack;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.OkUrlFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * An {@link com.android.volley.toolbox.HttpStack HttpStack} implementation which
 * uses OkHttp as its transport.
 */
class OkHttpStack extends HurlStack {
    private final OkUrlFactory factory;

    public OkHttpStack() {
        this(new OkHttpClient());
    }

    private OkHttpStack(OkHttpClient client) {
        if (client == null) {
            throw new NullPointerException("Client must not be null.");
        }
        factory = new OkUrlFactory(client);
    }

    @Override protected HttpURLConnection createConnection(URL url) throws IOException {
        HttpURLConnection connection = factory.open(url);
        connection.setRequestProperty("Accept-Encoding", "");
        return connection;
    }
}
