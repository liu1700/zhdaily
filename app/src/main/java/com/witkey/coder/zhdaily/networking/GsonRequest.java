package com.witkey.coder.zhdaily.networking;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.Response.ErrorListener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * 使用 gson 将java请求转换为json并请求
 *
 */
class GsonRequest<T> extends Request<T> {
    private final Gson gson = new Gson();
    private final Class<T> responseClass;
    private final Listener<T> listener;
    private final Map params;

    public GsonRequest(int method,
                       String url,
                       Map params,
                       Class<T> responseClass,
                       Listener<T> listener,
                       ErrorListener errorListener
    ) {
        super(method, url, errorListener);
        this.listener = listener;
        this.responseClass = responseClass;
        this.params = params;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String,String> headers= new HashMap<>();
        headers.put("Accept","application/json");
        headers.put("Content-Type","application/x-www-form-urlencoded");
        return headers;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }

    @Override
    protected void deliverResponse(T response) {
        if (listener != null) listener.onResponse(response);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(
                    response.data, HttpHeaderParser.parseCharset(response.headers)
            );
            T result = gson.fromJson(json, responseClass);
            return Response.success(
                    result, HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }
}
