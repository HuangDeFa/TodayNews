package com.huangdefa.todaynews.Net;

import android.content.Context;
import android.support.v4.util.ArrayMap;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ken.huang on 9/19/2017.
 * ApiManager
 */

public class ApiManager {

    private ApiService mApiService;
    public ApiManager(){

        mHttpClient=new OkHttpClient.Builder()
                .addInterceptor(new BaseInteroper())
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                .build();
        mRetrofit=new Retrofit.Builder()
                 .baseUrl(ApiService.BASE_URL)
                 .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                 .addConverterFactory(GsonConverterFactory.create())
                 .client(mHttpClient)
                 .build();
        mApiService=mRetrofit.create(ApiService.class);
    }

    private Retrofit mRetrofit;
    private OkHttpClient mHttpClient;

    private static ApiManager sManager;
    private static Context sContext;

    public static void initApi(Context context){
        sManager=new ApiManager();
        sContext=context;
    }

     public Observable<ResponseBody> getChannel(String channel){
       Map<String,String> paramsMap= preExecute();
       return  mApiService.getChannel(channel,paramsMap);
    }

    //http://is.snssdk.com/api/news/feed/v51/?category=news_hot&refer=1&count=20
    // &min_behot_time=1491981025&last_refresh_sub_entrance_interval=1491981165&loc_mode=
    // &loc_time=1491981000&latitude=&longitude=&city=&tt_from=pull&lac=&cid=&cp=&iid=0123456789&
    // device_id=12345678952&ac=wifi&channel=&aid=&app_name=&version_code=&version_name=
    // &device_platform=&ab_version=&ab_client=&ab_group=&ab_feature=&abflag=3&ssmix=a&device_type=
    // &device_brand=&language=zh&os_api=&os_version=&openudid=1b8d5bf69dc4a561&manifest_version_code=
    // &resolution=&dpi=&update_version_code=&_rticket=
    private  Map<String,String> preExecute() {
        Map<String,String> map=new ArrayMap<>();
        map.put("refer","1");
        map.put("count","20");
        map.put("min_behot_time","0");
        map.put("last_refresh_sub_entrance_interval","0");
        map.put("loc_mode","1");
        map.put("loc_time","1");
        map.put("latitude","");
        map.put("longitude","");
        map.put("city","");
        map.put("tt_from","pull");
        map.put("lac","");
        map.put("cid","");
        map.put("cp","");
        map.put("iid","0123456789");
        map.put("device_id","12345678992");
        map.put("ac","wifi");
        map.put("channel","");
        map.put("aid","");
        map.put("app_name","");
        map.put("version_code","");
        map.put("version_name","");
        map.put("device_platform","");
        map.put("ab_version","");
        map.put("ab_client","");
        map.put("ab_group","");
        map.put("ab_feature","");
        map.put("abflag","3");
        map.put("ssmix","a");
        map.put("device_type","");
        map.put("device_brand","");
        map.put("language","zh");
        map.put("os_api","");
        map.put("os_version","");
        map.put("openudid","1b8d5bf69dc4a561");
        map.put("manifest_version_code","");
        map.put("resolution","");
        map.put("dpi","");
        map.put("update_version_code","");
        map.put("_rticket","");
        return map;
    }

}
