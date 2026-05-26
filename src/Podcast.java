import java.util.Iterator;

/**
 * Interface representing a podcast containing a collection of video episodes.
 * @author Gonçalo Domingos and João Domingues
 */
public interface Podcast extends AbstractTaggableContent {
    /**
     * Gets the language code of the podcast.
     * @return the two-character language code
     */
    String getLanguageCode();

    /**
     * Adds a new episode to the podcast collection.
     * @param episode the episode to add
     */
    void addEpisode(PodcastEpisode episode);

    /**
     * Retrieves all episodes in reverse chronological order.
     * @return an iterator of the podcast episodes
     */
    Iterator<PodcastEpisode> getEpisodes();

    /**
     * Checks if the podcast has any published episodes.
     * @return true if there is at least one episode, false otherwise
     */
    boolean hasEpisodes();

    /**
     * Retrieves the most recently added episode.
     * @return the latest PodcastEpisode, or null if empty
     */
    PodcastEpisode getLatestEpisode();
}