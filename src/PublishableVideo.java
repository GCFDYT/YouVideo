/**
 * Represents a video that can be independently published in the system.
 * @author Gonçalo Domingos and João Domingues
 */
public interface PublishableVideo extends AbstractVideo {

    /**
     * Returns the file URL of the video.
     * @return returns the <code>url</code> string.
     */
    String getUrl();

    /**
     * Returns the name of the entity that published the video.
     * @return returns the <code>publisher</code> string.
     */
    String getPublisher();

    /**
     * Returns the language code of the video content.
     * @return returns the <code>languageCode</code> string.
     */
    String getLanguageCode();
}