import android.os.SystemClock;

import com.google.gson.Gson;
import com.huangdefa.todaynews.Model.ChannelModel;
import com.huangdefa.todaynews.Model.ChannelViewModel;
import com.huangdefa.todaynews.Model.ContentModel;
import com.huangdefa.todaynews.Model.MovieModel;
import com.huangdefa.todaynews.Net.ApiManager;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

import static org.junit.Assert.assertEquals;

/**
 * Created by ken.huang on 9/19/2017.
 * 单元测试类
 */

public class AppUnitTest {
    @Test
    public void exampleTest(){
        int a=2,b=5;
        long time= new Date().getTime();
       assertEquals(7,a+b);
    }

    @Test
    public void getChannel(){
        new ApiManager().getChannel("news_hot").subscribe(new Observer<ChannelViewModel>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull ChannelViewModel responseBody) {
                try {
                    ChannelViewModel viewModel=responseBody;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Test
    public void apiManagerTest(){
        ApiManager apiManager=new ApiManager();;
        apiManager.getChannel("new_host").subscribe(new Observer<ChannelViewModel>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull ChannelViewModel channelViewModel) {
                System.out.print(channelViewModel);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.out.print(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });

        apiManager.getMovie().subscribe(new Consumer<MovieModel>() {
            @Override
            public void accept(MovieModel movieModel) throws Exception {
                System.out.print(movieModel);
            }
        });
    }

    /**
     * 使用retrofit
     */
    @Test public void retrofitTest(){
        OkHttpClient okHttpClient=new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request oldRequest = chain.request();
                        Request.Builder request = oldRequest.newBuilder();
                        HttpUrl.Builder urlBuilder=oldRequest.url().newBuilder();
                        urlBuilder.addQueryParameter("start","0");
                        urlBuilder.addQueryParameter("count","10");
                        request.url(urlBuilder.build());
                        return chain.proceed(request.build());
                    }
                })
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://api.douban.com/v2/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        DouBanApiservice douBanApiservice = retrofit.create(DouBanApiservice.class);
       /* douBanApiservice.getMovies("top250")
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<MovieModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull MovieModel movieModel) {
                        System.out.println(movieModel.toString());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                    System.out.println(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("complete");
                    }
                });*/
        douBanApiservice.getMovies()
                .map(new HttpReusltFunc<List<MovieModel.SubjectsBean>>())
                .subscribe(new Observer<List<MovieModel.SubjectsBean>>() {
                    private Disposable mDisposable;
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        this.mDisposable=d;
                    }

                    @Override
                    public void onNext(@NonNull List<MovieModel.SubjectsBean> subjectsBeen) {
                     System.out.println(subjectsBeen.size());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    interface DouBanApiservice{
        @GET("movie/{arg}")
        Observable<MovieModel> getMovies(@Path("arg") String arg);

        @GET("movie/top250")
        Observable<HttpResult<List<MovieModel.SubjectsBean>>> getMovies();
    }

    private abstract class BaseHttpResult<T>{

    }
    private class HttpResult<T>{
        public int count;
        public int start;
        public int total;
        public T subjects;
    }

    private class HttpReusltFunc<T> implements Function<HttpResult<T>,T>{
        @Override
        public T apply(@NonNull HttpResult<T> tHttpResult) throws Exception {
            if(tHttpResult.subjects==null)
                throw new RuntimeException("get nothing error!");
            return tHttpResult.subjects;
        }
    }

}
