package com.huangdefa.todaynews.Net;

/**
 * Created by ken.huang on 9/22/2017.
 *
 */

public abstract class BaseHttpResult<T> {

   abstract boolean isSuccess();

   abstract String getMessage();

    T ResultData;
}
