import java.util.Iterator;

/**
 * Represents an author in the streaming platform.
 * An author can create shows and podcasts.
 *
 * @author Gonçalo Domingos and João Domingues
 */
public interface Author {

    /**
     * Returns the name of the author.
     * @return the author's name
     */
    String getName();

    /**
     * Returns the total productivity count (number of shows + podcasts).
     * @return total contributions
     */
    int getProductivity();

    /**
     * Adds a show to this author's list.
     * @param show the show to add
     */
    void addShow(Show show);

    /**
     * Adds a podcast to this author's list.
     * @param podcast the podcast to add
     */
    void addPodcast(Podcast podcast);

    /**
     * Returns an iterator over the author's podcasts.
     * @return iterator of podcasts
     */
    Iterator<Podcast> getPodcasts();

    /**
     * Returns an iterator over the author's shows.
     * @return iterator of shows
     */
    Iterator<Show> getShows();

    /**
     * Removes a show from this author's list.
     * @param show the show to remove
     */
    void removeShow(Show show);

    /**
     * Removes a podcast from this author's list.
     * @param podcast the podcast to remove
     */
    void removePodcast(Podcast podcast);
}