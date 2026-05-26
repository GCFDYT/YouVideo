/**
 * Record implementation of a Subtitle, representing a subtitle file for a premium video.
 * Contains the file URL and the language code of the subtitle track.
 *
 * @param subtitleUrl the URL location of the subtitle file
 * @param subtitleLanguage the ISO 639-1 language code of the subtitle
 *
 * @author Gonçalo Domingos and João Domingues
 */
public record SubtitleImpl(String subtitleUrl, String subtitleLanguage) implements Subtitle {

    @Override
    public String getSubtitleUrl() {
        return subtitleUrl;
    }

    @Override
    public String getSubtitleLanguage() {
        return subtitleLanguage;
    }
}