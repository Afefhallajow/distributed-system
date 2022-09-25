package com.first.project;

public class myBody {
    String videoId;
    String textOriginal;

    public myBody(String videoId, String textOriginal) {
        this.videoId = videoId;
        this.textOriginal = textOriginal;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getTextOriginal() {
        return textOriginal;
    }

    public void setTextOriginal(String textOriginal) {
        this.textOriginal = textOriginal;
    }

}
