/**
 * Implementation of a standard publishable video without subtitle support.
 * @author Gonçalo Domingos and João Domingues
 */
public class PublishableVideoImpl extends AbstractVideo implements PublishableVideo {

    private final String url;
    private final String publisher;
    private final String language;

    /**
     * Constructs a new publishable video.
     * @param videoID the unique identifier of the video
     * @param videoDuration the duration of the video in minutes
     * @param url the file URL of the video
     * @param publisher the name of the publisher
     * @param title the title of the video
     * @param language the language code of the video
     */
    public PublishableVideoImpl(String videoID, int videoDuration, String url,
                                String publisher, String title, String language) {
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
    public String getLanguageCode() {
        return language;
    }

    @Override
    public boolean equals(Object obj) {
        boolean isEqual = false;

        if (this == obj) {
            isEqual = true;
        } else if (obj instanceof PublishableVideoImpl that) {
            isEqual = getVideoID().equalsIgnoreCase(that.getVideoID());
        }
        return isEqual;
    }

    @Override
    public int hashCode() {
        return getVideoID().toLowerCase().hashCode();
    }
}