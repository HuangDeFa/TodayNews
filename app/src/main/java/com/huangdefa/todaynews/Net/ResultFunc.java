package com.huangdefa.todaynews.Net;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by ken.huang on 9/22/2017.
 * 对返回的结果预处理
 */

public class ResultFunc<T extends BaseHttpResult<R>,R> implements Function<T,R>{
    @Override
    public R apply(@NonNull T t) throws Exception {
        if(t.isSuccess()) {
            return t.ResultData;
        }else {
            throw new FaultException(t.getMessage());
        }
    }

    public static class FaultException extends Exception{
        public FaultException(String message) {
            super(message);
        }
    }
}
