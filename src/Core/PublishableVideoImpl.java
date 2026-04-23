package Core;

public class PublishableVideoImpl extends AbstractVideoImpl implements PublishableVideo {

    private final String url;
    private final String publisher;
    private final String language;

    public PublishableVideoImpl(String videoID, int videoDuration, String url, String publisher, String title, String language) {
        super(videoID, title, videoDuration);
        this.url = url;
        this.publisher = publisher;
        this.language = language;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getPublisher() {
        return publisher;
    }

    @Override
    public String getLanguage() {
        return language;
    }
}
