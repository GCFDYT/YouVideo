package Core;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Implementation of a podcast, containing a collection of episodes.
 * * @author Gonçalo Domingos and João Domingues
 */
public class PodcastImpl extends AbstractTaggableContent implements Podcast {

    private final String languageCode;
    private final List<PodcastEpisode> episodes;

    public PodcastImpl(String title, String author, String languageCode) {
        // Pass the shared attributes up to the abstract class
        super(title, author); 
        this.languageCode = languageCode;
        this.episodes = new LinkedList<>();
    }

    @Override
    public String getLanguageCode() {
        return languageCode;
    }

    @Override
    public void addEpisode(PodcastEpisode episode) {
        episodes.addFirst(episode);
    }

    @Override
    public Iterator<PodcastEpisode> getEpisodes() {
        return episodes.iterator();
    }

    @Override
    public boolean hasEpisodes() {
        return !episodes.isEmpty();
    }

    @Override
    public PodcastEpisode getLatestEpisode() {
        if (hasEpisodes()) {
            return episodes.iterator().next();
        }
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof PodcastImpl that)) {
            return false;
        }
        return this.getTitle().equalsIgnoreCase(that.getTitle());
    }

    @Override
    public int hashCode() {
        return getTitle().toLowerCase().hashCode();
    }
}