package Core;

public class PublishableVideo extends Video{

    private final String url;
    private final String publisher;
    private final String title;
    private final String language;

    public PublishableVideo(String videoId, int videoDuration, String url, String publisher, String title, String language) {
        super(videoId, videoDuration);

        this.url = url;
        this.publisher = publisher;
        this.title = title;
        this.language = language;
    }

    @Override
    public String getVideoId() {
        return super.getVideoId();
    }

    @Override
    public int getVideoDuration() {
        return super.getVideoDuration();
    }

    public String getUrl() {
        return url;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getTitle() {
        return title;
    }

    public String getLanguage() {
        return language;
    }

}
