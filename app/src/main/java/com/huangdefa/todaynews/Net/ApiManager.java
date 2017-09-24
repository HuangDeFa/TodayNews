package com.huangdefa.todaynews.Net;

import android.content.Context;
import android.support.v4.util.ArrayMap;

import com.google.gson.Gson;
import com.huangdefa.todaynews.Model.ChannelModel;
import com.huangdefa.todaynews.Model.ChannelViewModel;
import com.huangdefa.todaynews.Model.ContentModel;
import com.huangdefa.todaynews.Model.MovieModel;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ken.huang on 9/19/2017.
 * ApiManager
 */

public class ApiManager {

    static Map<String, String> HostList;
    final static String HOST_HEADER = "hostheader";
    final static String TOUTIAO_CHANNEL = "toutiao_channel";
    final static String DOUBAN = "douban";

    //默认超时
    final static int DEFAULT_TIMEOUT=10;

    //可以添加需要baseURl
    static {
        HostList = new HashMap<>();
        HostList.put(TOUTIAO_CHANNEL, "http://lf.snssdk.com/");
        HostList.put(DOUBAN, "https://api.douban.com/");
    }

    public static interface HttpCallBack<T> {

        void onStart(Disposable d);

        void onSuccess(T data);

        void onComplete();

    }

    private ApiService mApiService;

    public ApiManager() {

        mHttpClient = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT,TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(new BaseInterceptor())
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                .build();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(mHttpClient)
                .build();
        mApiService = mRetrofit.create(ApiService.class);
    }

    private Retrofit mRetrofit;
    private OkHttpClient mHttpClient;

    private static ApiManager sManager;
    private static Context sContext;

    public static void initApi(Context context) {
        sManager = new ApiManager();
        sContext = context;
    }

    private RequestBody createBody(String description) {
        return RequestBody.create(MediaType.parse("text/plain"), description);
    }

    private MultipartBody.Part createBody(File file) {
        return MultipartBody.Part.create(RequestBody.create(MediaType.parse("stream/application"), file));
    }

    public void getChannel(String channel, HttpCallBack<ChannelViewModel> callBack) {
        Map<String, String> paramsMap = preExecute();
        mApiService.getChannel(channel, paramsMap).map(new Function<ChannelModel, ChannelViewModel>() {
            @Override
            public ChannelViewModel apply(@NonNull ChannelModel channelModel) throws Exception {
                ChannelViewModel viewModel = new ChannelViewModel();
                viewModel.ChannelModel = channelModel;
                viewModel.ContentModels = new ArrayList<>();
                Gson gson = new Gson();
                for (ChannelModel.Data data : channelModel.data) {
                    viewModel.ContentModels.add(gson.fromJson(data.content, ContentModel.class));
                }
                return viewModel;
            }
        }).subscribe(createObserver(callBack));
    }

    private <T> Observer<T> createObserver(final HttpCallBack<T> callBack) {
        return new Observer<T>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                if (callBack != null) {
                    callBack.onStart(d);
                }
            }

            @Override
            public void onNext(@NonNull T t) {
                if (callBack != null) {
                    callBack.onSuccess(t);
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {
                if (callBack != null) {
                    callBack.onComplete();
                }
            }
        };
    }

    public Observable<ChannelViewModel> getChannel(String channel) {
        Map<String, String> paramsMap = preExecute();
        return mApiService.getChannel(channel, paramsMap).map(new Function<ChannelModel, ChannelViewModel>() {
            @Override
            public ChannelViewModel apply(@NonNull ChannelModel channelModel) throws Exception {
                ChannelViewModel viewModel = new ChannelViewModel();
                viewModel.ChannelModel = channelModel;
                viewModel.ContentModels = new ArrayList<ContentModel>();
                Gson gson = new Gson();
                for (ChannelModel.Data data : channelModel.data) {
                    viewModel.ContentModels.add(gson.fromJson(data.content, ContentModel.class));
                }
                return viewModel;
            }
        });
    }

    public Observable<MovieModel> getMovie() {
        return mApiService.getTop250Movie();
    }

    //http://is.snssdk.com/api/news/feed/v51/?category=news_hot&refer=1&count=20
    // &min_behot_time=1491981025&last_refresh_sub_entrance_interval=1491981165&loc_mode=
    // &loc_time=1491981000&latitude=&longitude=&city=&tt_from=pull&lac=&cid=&cp=&iid=0123456789&
    // device_id=12345678952&ac=wifi&channel=&aid=&app_name=&version_code=&version_name=
    // &device_platform=&ab_version=&ab_client=&ab_group=&ab_feature=&abflag=3&ssmix=a&device_type=
    // &device_brand=&language=zh&os_api=&os_version=&openudid=1b8d5bf69dc4a561&manifest_version_code=
    // &resolution=&dpi=&update_version_code=&_rticket=
    private Map<String, String> preExecute() {
        Map<String, String> map = new ArrayMap<>();
        map.put("refer", "1");
        map.put("count", "20");
        map.put("min_behot_time", new Date().getTime() + "");
        map.put("last_refresh_sub_entrance_interval", new Date().getTime() + "");
        map.put("loc_mode", "1");
        map.put("loc_time", new Date().getTime() + "");
        map.put("latitude", "");
        map.put("longitude", "");
        map.put("city", "");
        map.put("tt_from", "pull");
        map.put("lac", "");
        map.put("cid", "");
        map.put("cp", "");
        map.put("plugin_enable", "3");
        map.put("iid", "0123456789");
        map.put("device_id", "12345678992");
        map.put("ac", "wifi");
        map.put("channel", "baidu");
        map.put("aid", "");
        map.put("app_name", "news_article");
        map.put("version_code", "636");
        map.put("version_name", "6.3.6");
        map.put("device_platform", "android");
        map.put("ab_version", "");
        map.put("ab_client", "");
        map.put("ab_feature", "");
        map.put("abflag", "3");
        map.put("ssmix", "a");
        map.put("device_type", "");
        map.put("device_brand", "");
        map.put("language", "zh");
        map.put("os_api", "");
        map.put("os_version", "");
        map.put("openudid", "1b8d5bf69dc4a561");
        map.put("manifest_version_code", "");
        map.put("resolution", "");
        map.put("dpi", "");
        map.put("update_version_code", "6368");
        map.put("_rticket", new Date().getTime() + "");
        map.put("plugin", "2431");
        return map;
    }

}
