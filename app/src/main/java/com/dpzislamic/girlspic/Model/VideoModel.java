package com.dpzislamic.girlspic.Model;

public class VideoModel {
    String thumbnail_url;
    String id;
    String video_url;

    public VideoModel(String thumbnail_url, String id, String video_url) {
        this.thumbnail_url = thumbnail_url;
        this.id = id;
        this.video_url = video_url;
    }

    public String getThumbnail_url() {
        return thumbnail_url;
    }

    public void setThumbnail_url(String thumbnail_url) {
        this.thumbnail_url = thumbnail_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }
}
