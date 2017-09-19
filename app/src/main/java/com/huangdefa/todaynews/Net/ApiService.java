package com.huangdefa.todaynews.Net;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by ken.huang on 9/19/2017.
 * ApiService interface for Retrofit
 */

public interface ApiService {
    //http://is.snssdk.com/api/news/feed/v51/?category=news_hot&refer=1&count=20
    // &min_behot_time=1491981025&last_refresh_sub_entrance_interval=1491981165&loc_mode=
    // &loc_time=1491981000&latitude=&longitude=&city=&tt_from=pull&lac=&cid=&cp=&iid=0123456789&
    // device_id=12345678952&ac=wifi&channel=&aid=&app_name=&version_code=&version_name=
    // &device_platform=&ab_version=&ab_client=&ab_group=&ab_feature=&abflag=3&ssmix=a&device_type=
    // &device_brand=&language=zh&os_api=&os_version=&openudid=1b8d5bf69dc4a561&manifest_version_code=
    // &resolution=&dpi=&update_version_code=&_rticket=
    static String BASE_URL="http://is.snssdk.com/api/news/feed/v51/";

    @GET("{category}")
    Observable<ResponseBody> getChannel(@Path("category") String category, @QueryMap Map<String,String> queryMap);

}
