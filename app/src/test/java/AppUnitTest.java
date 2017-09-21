import android.os.SystemClock;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.huangdefa.todaynews.Model.ChannelModel;
import com.huangdefa.todaynews.Model.ChannelViewModel;
import com.huangdefa.todaynews.Model.ContentModel;
import com.huangdefa.todaynews.Net.ApiManager;

import static org.junit.Assert.*;

import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

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
        new ApiManager().getChannel("news_hot").subscribe(new Observer<ResponseBody>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull ResponseBody responseBody) {
                try {
                    Gson gson= new Gson();
                    ChannelViewModel channelViewModel =new ChannelViewModel();
                    channelViewModel.ChannelModel=gson.fromJson(responseBody.string(), ChannelModel.class);
                    channelViewModel.ContentModels=new ArrayList<ContentModel>();
                    for (ChannelModel.Data data : channelViewModel.ChannelModel.data) {
                        ContentModel contentModel= gson.fromJson(data.content,ContentModel.class);
                        channelViewModel.ContentModels.add(contentModel);
                    }
                    List<ContentModel> contentModels = channelViewModel.ContentModels;
                } catch (IOException e) {
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
}
