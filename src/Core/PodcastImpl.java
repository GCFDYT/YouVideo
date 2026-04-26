package Core;

import dataStructures.Array;
import dataStructures.ArrayClass;
import dataStructures.Iterator;

/**
 * Implementation of a podcast, containing a collection of episodes.
 * Episodes are stored in reverse chronological order (newest first) to optimize
 * access to the latest episode without requiring sorting operations.
 *
 * @author Gonçalo Domingos and João Domingues
 */
public class PodcastImpl implements Podcast {

    private final String title;
    private final String author;
    private final String languageCode;
    private final Array<PodcastEpisode> episodes;

    /**
     * Constructs a new podcast with the specified properties and no episodes.
     *
     * @param title the unique title of the podcast
     * @param author the author of the podcast
     * @param languageCode the language code of the podcast
     */
    public PodcastImpl(String title, String author, String languageCode) {
        this.title = title;
        this.author = author;
        this.languageCode = languageCode;
        this.episodes = new ArrayClass<>();
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public String getLanguageCode() {
        return languageCode;
    }

    @Override
    public void addEpisode(PodcastEpisode episode) {
        episodes.insertAt(episode, 0);
    }

    @Override
    public Iterator<PodcastEpisode> getEpisodes() {
        return episodes.iterator();
    }

    @Override
    public boolean hasEpisodes() {
        return episodes.size() > 0;
    }

    @Override
    public PodcastEpisode getLatestEpisode() {
        if (hasEpisodes()) {
            return episodes.get(0);
        } else {
            return null;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null || getClass() != obj.getClass()) {
            return false;
        } else {
            PodcastImpl that = (PodcastImpl) obj;
            return title.equalsIgnoreCase(that.title);
        }
    }

    @Override
    public int hashCode() {
        return title.toLowerCase().hashCode();
    }
}