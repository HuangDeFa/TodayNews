package com.huangdefa.todaynews.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ken.huang on 9/21/2017.
 */

public class ChannelModel {

    @SerializedName("login_status")
    public int login_status;
    @SerializedName("total_number")
    public int total_number;
    @SerializedName("has_more")
    public boolean has_more;
    @SerializedName("post_content_hint")
    public String post_content_hint;
    @SerializedName("show_et_status")
    public int show_et_status;
    @SerializedName("feed_flag")
    public int feed_flag;
    @SerializedName("action_to_last_stick")
    public int action_to_last_stick;
    @SerializedName("message")
    public String message;
    @SerializedName("has_more_to_refresh")
    public boolean has_more_to_refresh;
    @SerializedName("data")
    public List<Data> data;
    @SerializedName("tips")
    public Tips tips;

    public static class Data {
        @SerializedName("content")
        public String content;
        @SerializedName("code")
        public String code;
    }

    public static class Tips {
        @SerializedName("display_info")
        public String display_info;
        @SerializedName("open_url")
        public String open_url;
        @SerializedName("web_url")
        public String web_url;
        @SerializedName("app_name")
        public String app_name;
        @SerializedName("package_name")
        public String package_name;
        @SerializedName("display_template")
        public String display_template;
        @SerializedName("type")
        public String type;
        @SerializedName("display_duration")
        public int display_duration;
        @SerializedName("download_url")
        public String download_url;
    }
}
