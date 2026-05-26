package Core;

/**
 * Represents a podcast containing a collection of video episodes.
 * @author Gonçalo Domingos and João Domingues
 */
public interface Podcast {

    /**
     * Returns the title of the podcast.
     * @return returns the <code>title</code> string.
     */
    String getTitle();

    /**
     * Returns the author of the podcast.
     * @return returns the <code>author</code> string.
     */
    String getAuthor();

    /**
     * Returns the ISO 639-1 language code of the podcast.
     * @return returns the <code>languageCode</code> string.
     */
    String getLanguageCode();

    /**
     * Adds a new episode to the podcast collection.
     * @param episode the episode object to be added.
     * @pre episode != null
     */
    void addEpisode(PodcastEpisode episode);

    /**
     * Returns an iterator over the episodes in reverse chronological order.
     *
     * @return returns an <code>Iterator</code> of podcast episodes.
     */
    java.util.Iterator<PodcastEpisode> getEpisodes();

    /**
     * Checks if the podcast currently has any episodes.
     * @return returns <code>true</code> if it has at least one episode.
     */
    boolean hasEpisodes();

    /**
     * Returns the most recent episode added to the podcast.
     * @return returns the latest <code>PodcastEpisode</code> object.
     * @pre hasEpisodes() == true
     */
    PodcastEpisode getLatestEpisode();
}