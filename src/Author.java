import java.util.ArrayList;
import java.util.Iterator;

/**
 * Represents an author in the system, responsible for creating shows and podcasts.
 * Each author has a name and lists of shows and podcasts they have created.
 * @author Gonçalo Domingos and João Domingues
 */
public class Author {
    private final String name;
    private final ArrayList<Show> shows;
    private final ArrayList<Podcast> podcasts;

    public Author(String name) {
        this.name = name;
        this.shows = new ArrayList<>();
        this.podcasts = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getProductivity() {
        return shows.size() + podcasts.size();
    }

    public void addShow(Show show) {
        shows.add(show);
    }

    public void addPodcast(Podcast podcast) {
        podcasts.add(podcast);
    }

    public Iterator<Podcast> getPodcasts() {
        return podcasts.iterator();
    }

    public void removeShow(Show show) {
        shows.remove(show);
    }

    public void removePodcast(Podcast podcast) {
        podcasts.remove(podcast);
    }
}