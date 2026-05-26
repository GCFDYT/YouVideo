/**
 * Implementation of a podcast episode, extending the abstract video class.
 * Represents a single episode within a podcast, containing its file URL and release date.
 * Each episode is treated as a video in the system and inherits common video properties.
 *
 * @author Gonçalo Domingos and João Domingues
 */
public class PodcastEpisodeImpl extends AbstractVideo implements PodcastEpisode {

    private final String url;
    private final String date;

    /**
     * Constructs a new podcast episode with the specified properties.
     *
     * @param videoID the unique identifier of the episode
     * @param videoDuration the duration of the episode in minutes
     * @param title the title of the episode
     * @param url the file URL of the episode
     * @param date the release date of the episode
     */
    public PodcastEpisodeImpl(String videoID, int videoDuration,
                              String title, String url, String date) {
        super(videoID, title, videoDuration);
        this.url = url;
        this.date = date;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getDate() {
        return date;
    }
}