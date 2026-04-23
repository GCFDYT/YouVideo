package Core;

public abstract class AbstractVideoImpl implements AbstractVideo {

    private final String videoId;
    private final String title;
    private final int videoDuration;

    protected AbstractVideoImpl(String videoId, String title, int videoDuration) {
        this.videoId = videoId;
        this.title = title;
        this.videoDuration = videoDuration;
    }

    @Override
    public String getVideoId() {
        return videoId;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public int getVideoDuration() {
        return videoDuration;
    }
}
