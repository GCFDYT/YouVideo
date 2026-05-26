/**
 * Represents an unpublishable video that acts as an episode within a podcast.
 * @author Gonçalo Domingos and João Domingues
 */
public interface PodcastEpisode extends AbstractVideo {

    /**
     * Returns the file URL of the episode.
     * @return returns the <code>url</code> string.
     */
    String getUrl();

    /**
     * Returns the release date of the episode.
     * @return returns the <code>date</code> string in YYYY-MM-DD format.
     */
    String getDate();
}