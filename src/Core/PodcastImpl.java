package Core;

import dataStructures.Array;
import dataStructures.ArrayClass;
import dataStructures.Iterator;

public class PodcastImpl implements Podcast {

    private final String title;
    private final String author;
    private final String languageCode;
    private final Array<PodcastEpisode> episodes;

    public PodcastImpl(String title, String author, String languageCode) {
        this.title = title;
        this.author = author;
        this.languageCode = languageCode;
        this.episodes = new ArrayClass<>();
    }

    @Override
    public String getTitle() { return title; }

    @Override
    public String getAuthor() { return author; }

    @Override
    public String getLanguageCode() { return languageCode; }

    @Override
    public void addEpisode(PodcastEpisode episode) {
        /* * Platform rules guarantee new episodes are >= the latest episode date.
         * By inserting at index 0, we naturally maintain the required reverse chronological order
         * without needing a sorting algorithm.
         */
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
        }
        return null;
    }
}