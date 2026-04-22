package Core;

public class PublishableVideoClass extends AbstractVideoClass implements PublishableVideo {

    private final String url;
    private final String publisher;
    private final String language;

    public PublishableVideoClass(String videoId, int videoDuration, String url, String publisher, String title, String language) {
        super(videoId, title, videoDuration);

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
