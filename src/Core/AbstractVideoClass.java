package Core;

public abstract class AbstractVideoClass implements AbstractVideo {

    private final String videoId;
    private final int videoDuration;

    protected AbstractVideoClass(String videoId, int videoDuration) {
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
