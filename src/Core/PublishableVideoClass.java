package Core;

public class PublishableVideoClass extends AbstractVideoClass implements PublishableVideo {

    private final String url;
    private final String publisher;
    private final String title;
    private final String language;

    public PublishableVideoClass(String videoId, int videoDuration, String url, String publisher, String title, String language) {
        super(videoId, videoDuration);

        this.url = url;
        this.publisher = publisher;
        this.title = title;
        this.language = language;
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
