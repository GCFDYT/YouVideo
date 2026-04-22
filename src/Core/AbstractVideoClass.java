package Core;

public abstract class AbstractVideoClass implements AbstractVideo {

    private final String videoId;
    private final String title;
    private final int videoDuration;

    protected AbstractVideoClass(String videoId, String title, int videoDuration) {
        this.videoId = videoId;
        this.title = title;
        this.videoDuration = videoDuration;
    }

    @Override
    public String getVideoId() {
        return videoId;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public int getVideoDuration() {
        return videoDuration;
    }
}
