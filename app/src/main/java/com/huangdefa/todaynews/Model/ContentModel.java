package com.huangdefa.todaynews.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ken.huang on 9/21/2017.
 *
 */

public class ContentModel {

    @SerializedName("log_pb")
    public Log_pb log_pb;
    @SerializedName("read_count")
    public int read_count;
    @SerializedName("video_id")
    public String video_id;
    @SerializedName("media_name")
    public String media_name;
    @SerializedName("ban_comment")
    public int ban_comment;
    @SerializedName("abstract")
    public String mabstract;
    @SerializedName("video_detail_info")
    public Video_detail_info video_detail_info;
    @SerializedName("image_list")
    public List<Image_list> image_list;
    @SerializedName("ugc_recommend")
    public Ugc_recommend ugc_recommend;
    @SerializedName("article_type")
    public int article_type;
    @SerializedName("tag")
    public String tag;
    @SerializedName("forward_info")
    public Forward_info forward_info;
    @SerializedName("has_m3u8_video")
    public int has_m3u8_video;
    @SerializedName("video_duration")
    public int video_duration;
    @SerializedName("show_portrait_article")
    public boolean show_portrait_article;
    @SerializedName("user_verified")
    public int user_verified;
    @SerializedName("aggr_type")
    public int aggr_type;
    @SerializedName("cell_type")
    public int cell_type;
    @SerializedName("article_sub_type")
    public int article_sub_type;
    @SerializedName("group_flags")
    public int group_flags;
    @SerializedName("bury_count")
    public int bury_count;
    @SerializedName("title")
    public String title;
    @SerializedName("ignore_web_transform")
    public int ignore_web_transform;
    @SerializedName("source_icon_style")
    public int source_icon_style;
    @SerializedName("tip")
    public int tip;
    @SerializedName("hot")
    public int hot;
    @SerializedName("share_url")
    public String share_url;
    @SerializedName("has_mp4_video")
    public int has_mp4_video;
    @SerializedName("source")
    public String source;
    @SerializedName("comment_count")
    public int comment_count;
    @SerializedName("article_url")
    public String article_url;
    @SerializedName("filter_words")
    public List<Filter_words> filter_words;
    @SerializedName("share_count")
    public int share_count;
    @SerializedName("rid")
    public String rid;
    @SerializedName("publish_time")
    public int publish_time;
    @SerializedName("action_list")
    public List<Action_list> action_list;
    @SerializedName("cell_layout_style")
    public int cell_layout_style;
    @SerializedName("tag_id")
    public String tag_id;
    @SerializedName("video_style")
    public int video_style;
    @SerializedName("verified_content")
    public String verified_content;
    @SerializedName("display_url")
    public String display_url;
    @SerializedName("large_image_list")
    public List<Large_image_list> large_image_list;
    @SerializedName("media_info")
    public Media_info media_info;
    @SerializedName("item_id")
    public String item_id;
    @SerializedName("is_subject")
    public boolean is_subject;
    @SerializedName("show_portrait")
    public boolean show_portrait;
    @SerializedName("repin_count")
    public int repin_count;
    @SerializedName("cell_flag")
    public int cell_flag;
    @SerializedName("user_info")
    public User_info user_info;
    @SerializedName("source_open_url")
    public String source_open_url;
    @SerializedName("level")
    public int level;
    @SerializedName("like_count")
    public int like_count;
    @SerializedName("digg_count")
    public int digg_count;
    @SerializedName("behot_time")
    public long behot_time;
    @SerializedName("cursor")
    public String cursor;
    @SerializedName("url")
    public String url;
    @SerializedName("preload_web")
    public int preload_web;
    @SerializedName("user_repin")
    public int user_repin;
    @SerializedName("has_image")
    public boolean has_image;
    @SerializedName("item_version")
    public int item_version;
    @SerializedName("has_video")
    public boolean has_video;
    @SerializedName("group_id")
    public String group_id;
    @SerializedName("middle_image")
    public Middle_image middle_image;

    public static class Log_pb {
        @SerializedName("impr_id")
        public String impr_id;
    }

    public static class Video_url {
    }

    public static class Url_list {
        @SerializedName("url")
        public String url;
    }

    public static class Detail_video_large_image {
        @SerializedName("url")
        public String url;
        @SerializedName("width")
        public int width;
        @SerializedName("url_list")
        public List<Url_list> url_list;
        @SerializedName("uri")
        public String uri;
        @SerializedName("height")
        public int height;
    }

    public static class Video_detail_info {
        @SerializedName("group_flags")
        public int group_flags;
        @SerializedName("video_type")
        public int video_type;
        @SerializedName("video_preloading_flag")
        public int video_preloading_flag;
        @SerializedName("video_url")
        public List<Video_url> video_url;
        @SerializedName("direct_play")
        public int direct_play;
        @SerializedName("detail_video_large_image")
        public Detail_video_large_image detail_video_large_image;
        @SerializedName("show_pgc_subscribe")
        public int show_pgc_subscribe;
        @SerializedName("video_third_monitor_url")
        public String video_third_monitor_url;
        @SerializedName("video_id")
        public String video_id;
        @SerializedName("video_watching_count")
        public int video_watching_count;
        @SerializedName("video_watch_count")
        public int video_watch_count;
    }

    public static class Image_list {
    }

    public static class Ugc_recommend {
        @SerializedName("reason")
        public String reason;
        @SerializedName("activity")
        public String activity;
    }

    public static class Forward_info {
        @SerializedName("forward_count")
        public int forward_count;
    }

    public static class Filter_words {
        @SerializedName("id")
        public String id;
        @SerializedName("name")
        public String name;
        @SerializedName("is_selected")
        public boolean is_selected;
    }

    public static class Extra {
    }

    public static class Action_list {
        @SerializedName("action")
        public int action;
        @SerializedName("extra")
        public Extra extra;
        @SerializedName("desc")
        public String desc;
    }

    public static class Large_image_list {
        @SerializedName("url")
        public String url;
        @SerializedName("width")
        public int width;
        @SerializedName("url_list")
        public List<Url_list> url_list;
        @SerializedName("uri")
        public String uri;
        @SerializedName("height")
        public int height;
    }

    public static class Media_info {
        @SerializedName("user_id")
        public String user_id;
        @SerializedName("verified_content")
        public String verified_content;
        @SerializedName("avatar_url")
        public String avatar_url;
        @SerializedName("media_id")
        public String media_id;
        @SerializedName("name")
        public String name;
        @SerializedName("recommend_type")
        public int recommend_type;
        @SerializedName("follow")
        public boolean follow;
        @SerializedName("recommend_reason")
        public String recommend_reason;
        @SerializedName("is_star_user")
        public boolean is_star_user;
        @SerializedName("user_verified")
        public boolean user_verified;
    }

    public static class User_info {
        @SerializedName("verified_content")
        public String verified_content;
        @SerializedName("avatar_url")
        public String avatar_url;
        @SerializedName("user_id")
        public String user_id;
        @SerializedName("name")
        public String name;
        @SerializedName("follower_count")
        public int follower_count;
        @SerializedName("follow")
        public boolean follow;
        @SerializedName("user_auth_info")
        public String user_auth_info;
        @SerializedName("user_verified")
        public boolean user_verified;
        @SerializedName("description")
        public String description;
    }


    public static class Middle_image {
        @SerializedName("url")
        public String url;
        @SerializedName("width")
        public int width;
        @SerializedName("url_list")
        public List<Url_list> url_list;
        @SerializedName("uri")
        public String uri;
        @SerializedName("height")
        public int height;
    }
}
