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
        PodcastEpisode result;
        if (hasEpisodes()) {
            result = episodes.get(0);
        } else {
            result = null;
        }
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        boolean result;
        if (!(obj instanceof PodcastImpl that)) {
            result = false;
        } else {
            result = title.equalsIgnoreCase(that.title);
        }
        return result;
    }


    @Override
    public int hashCode() {
        return title.toLowerCase().hashCode();
    }
}