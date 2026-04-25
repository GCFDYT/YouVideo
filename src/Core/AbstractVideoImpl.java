package Core;

public abstract class AbstractVideoImpl implements AbstractVideo {

    private final String videoID;
    private final String title;
    private final int videoDuration;

    protected AbstractVideoImpl(String videoID, String title, int videoDuration) {
        this.videoID = videoID;
        this.title = title;
        this.videoDuration = videoDuration;
    }

    @Override
    public String getVideoID() {
        return videoID;
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