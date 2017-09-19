import com.huangdefa.todaynews.Net.ApiManager;

import static org.junit.Assert.*;
import org.junit.Test;

import java.io.IOException;

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
                    System.out.println(responseBody.string());
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
