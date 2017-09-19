package com.huangdefa.todaynews.Net;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ken.huang on 9/19/2017.
 * 自定义拦截器
 */

public class BaseInteroper implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request=chain.request();
        Request.Builder newRequest=request.newBuilder();

        return chain.proceed(newRequest.build());
    }
}
