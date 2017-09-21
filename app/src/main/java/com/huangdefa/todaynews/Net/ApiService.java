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
   //http://lf.snssdk.com/api/news/feed/v66/?refer=1&count=20&min_behot_time=1505973829&
   // last_refresh_sub_entrance_interval=1505977293&loc_mode=5&loc_time=1505918061
   // &latitude=33.00125&longitude=109.56358166666665&city=%E5%AE%89%E5%BA%B7%E5%B8%82&tt_from=tab_tip&lac=37207&cid=52701&cp=5d9dc637643cdq1&plugin_enable=3&st_time=7677&iid=15264841846&device_id=39335733732&ac=wifi&channel=baidu&aid=13&app_name=news_article&version_code=636&version_name=6.3.6&device_platform=android&ab_version=169864%2C177251%2C179336%2C173967%2C172662%2C172661%2C171194%2C176914%2C173738%2C170354%2C178992%2C175618%2C177071%2C159170%2C169445%2C169431%2C174273%2C168998%2C174395%2C178731%2C178664%2C177166%2C152027%2C176593%2C176289%2C176071%2C178533%2C177785%2C170713%2C176740%2C156262%2C145585%2C174430%2C177257%2C172419%2C162572%2C176601%2C176602%2C158958%2C177727%2C178112%2C176617%2C164943%2C170988%2C178987%2C175927%2C176597%2C176653%2C177702%2C176614&ab_client=a1%2Cc4%2Ce1%2Cf2%2Cg2%2Cf7&ab_feature=94563%2C102749&abflag=3&ssmix=a&device_type=ZUK+Z1&device_brand=ZUK&language=zh&os_api=19&os_version=4.4.2&uuid=867695010501541&openudid=509a4c1da0fc2216&manifest_version_code=636&resolution=900*1440&dpi=320&update_version_code=6368&_rticket=1505977293270&plugin=2431
    static String BASE_URL="http://lf.snssdk.com/api/news/feed/v66/";

    @GET("{category}")
    Observable<ResponseBody> getChannel(@Path("category") String category, @QueryMap Map<String,String> queryMap);

}
