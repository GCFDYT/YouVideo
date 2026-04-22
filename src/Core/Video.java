package Core;

public abstract class Video {

    private final String videoId;
    private final int videoDuration;

    public Video(String videoId, int videoDuration) {
        this.videoId = videoId;
        this.videoDuration = videoDuration;
    }

    public String getVideoId() {
        return videoId;
    }

    public int getVideoDuration() {
        return videoDuration;
    }
}
