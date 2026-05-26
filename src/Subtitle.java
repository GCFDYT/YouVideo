/**
 * Represents a subtitle file for premium videos.
 * @author Gonçalo Domingos and João Domingues
 */
public interface Subtitle {

    /**
     * Returns the URL of the subtitle file.
     * @return the subtitle URL
     */
    String getSubtitleUrl();

    /**
     * Returns the language code of the subtitle.
     * @return the language code
     */
    String getSubtitleLanguage();
}
