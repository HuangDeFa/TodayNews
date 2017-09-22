package com.huangdefa.todaynews.Net;

import android.text.TextUtils;

import java.io.IOException;
import java.util.Set;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ken.huang on 9/19/2017.
 * 自定义拦截器,根据不同的请求动态切换BaseURL和添加对应参数
 */

public class BaseInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest=chain.request();
        Request.Builder newRequest=oldRequest.newBuilder();

        String header = oldRequest.header(ApiManager.HOST_HEADER);
        if(header!=null){
            HttpUrl.Builder builder = oldRequest.url().newBuilder();
            HttpUrl parse = null;
            if(header.equals(ApiManager.DOUBAN)){
                parse = HttpUrl.parse(ApiManager.HostList.get(ApiManager.DOUBAN));
            }else if(header.equals(ApiManager.TOUTIAO_CHANNEL)){
                parse = HttpUrl.parse(ApiManager.HostList.get(ApiManager.TOUTIAO_CHANNEL));
         }
         if(parse!=null){
             builder.scheme(parse.scheme())
                     .host(parse.host())
                     .port(parse.port());
             newRequest.url(builder.build());
         }
        }
        return chain.proceed(newRequest.build());
    }
}
