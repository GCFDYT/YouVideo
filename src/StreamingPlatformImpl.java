import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Implementation of the StreamingPlatform interface.
 * Manages collections of publishable videos, podcasts, shows, and authors.
 *
 * @author Gonçalo Domingos and João Domingues
 */
public class StreamingPlatformImpl implements StreamingPlatform {

    private final Map<String, PublishableVideo> publishableVideos;
    private final Map<String, Podcast> podcasts;
    private final Map<String, Show> shows;
    private final Map<String, Author> authorsMap;

    /**
     * Initializes the streaming platform with empty collections for videos, podcasts, shows, and authors.
     */
    public StreamingPlatformImpl() {
        publishableVideos = new HashMap<>();
        podcasts = new HashMap<>();
        shows = new HashMap<>();
        authorsMap = new HashMap<>();
    }

    /**
     * Retrieves a publishable video from the internal map using a case-insensitive ID search.
     * @param videoID - the ID of the video to find.
     * @return returns the PublishableVideo object, or null if not found.
     */
    private PublishableVideo findPublishableVideo(String videoID) {
        return publishableVideos.get(videoID.toLowerCase());
    }

    /**
     * Retrieves a podcast from the internal map using a case-insensitive title search.
     * @param title - the title of the podcast to find.
     * @return returns the Podcast object, or null if not found.
     */
    private Podcast findPodcast(String title) {
        return podcasts.get(title.toLowerCase());
    }

    /**
     * Retrieves a show from the internal map using a case-insensitive title search.
     * @param title - the title of the show to find.
     * @return returns the Show object, or null if not found.
     */
    private Show findShow(String title) {
        return shows.get(title.toLowerCase());
    }

    /**
     * Retrieves an existing author or creates and registers a new one if they do not currently exist.
     * @param name - the name of the author.
     * @return returns the Author object associated with the given name.
     */
    private Author getOrCreateAuthor(String name) {
        String key = name.toLowerCase();
        if (!authorsMap.containsKey(key)) {
            authorsMap.put(key, new Author(name));
        }
        return authorsMap.get(key);
    }

    @Override
    public boolean hasVideo(String videoID) {
        return hasPublishableVideo(videoID) || hasPodcastEpisode(videoID);
    }

    @Override
    public boolean hasPublishableVideo(String videoID) {
        return publishableVideos.containsKey(videoID.toLowerCase());
    }

    @Override
    public boolean hasPodcastEpisode(String videoID) {
        boolean found = false;
        Iterator<Podcast> podcastIterator = podcasts.values().iterator();

        while (!found && podcastIterator.hasNext()) {
            Podcast podcast = podcastIterator.next();
            Iterator<PodcastEpisode> episodeIterator = podcast.getEpisodes();

            while (!found && episodeIterator.hasNext()) {
                if (episodeIterator.next().getVideoID().equalsIgnoreCase(videoID)) {
                    found = true;
                }
            }
        }
        return found;
    }

    @Override
    public boolean isPremiumVideo(String videoID) {
        return findPublishableVideo(videoID) instanceof PremiumVideo;
    }

    @Override
    public boolean hasPodcast(String title) {
        return podcasts.containsKey(title.toLowerCase());
    }

    @Override
    public boolean hasShow(String title) {
        return shows.containsKey(title.toLowerCase());
    }

    @Override
    public boolean isValidLanguageCode(String code) {
        boolean isValid = false;

        if (code != null && code.length() == 2) {
            String[] languages = Locale.getISOLanguages();
            int i = 0;

            while (i < languages.length && !isValid) {
                if (languages[i].equalsIgnoreCase(code)) {
                    isValid = true;
                }
                i++;
            }
        }
        return isValid;
    }

    @Override
    public boolean isVideoUsedInShow(String videoID) {
        boolean found = false;
        Iterator<Show> showIterator = shows.values().iterator();

        while (!found && showIterator.hasNext()) {
            if (showIterator.next().getVideoID().equalsIgnoreCase(videoID)) {
                found = true;
            }
        }
        return found;
    }

    @Override
    public boolean isValidEpisodeDate(String podcastTitle, String date) {
        Podcast podcast = findPodcast(podcastTitle);
        boolean isValid = true;

        if (podcast != null && podcast.hasEpisodes()) {
            isValid = date.compareTo(podcast.getLatestEpisode().getDate()) >= 0;
        }
        return isValid;
    }

    @Override
    public boolean hasAuthorPodcasts(String author) {
        Author a = authorsMap.get(author.toLowerCase());
        return a != null && a.getPodcasts().hasNext();
    }

    @Override
    public boolean hasProductiveAuthors() {
        boolean hasProductive = false;
        Iterator<Author> authorIterator = authorsMap.values().iterator();

        while (!hasProductive && authorIterator.hasNext()) {
            hasProductive = authorIterator.next().getProductivity() > 0;
        }
        return hasProductive;
    }

    @Override
    public boolean isTitleTaggedWith(String title, String tag) {
        String normalizedTag = tag.toLowerCase();
        Podcast p = findPodcast(title);
        Show s = findShow(title);

        boolean pTagged = (p != null && p.hasTag(normalizedTag));
        boolean sTagged = (s != null && s.hasTag(normalizedTag));

        return pTagged || sTagged;
    }

    @Override
    public void addPublishableVideo(String videoID, int videoDuration, String url,
                                    String publisher, String title, String languageCode) {
        publishableVideos.put(videoID.toLowerCase(),
                new PublishableVideoImpl(videoID, videoDuration, url,
                        publisher, title, languageCode));
    }

    @Override
    public void addPremiumVideo(String videoID, int videoDuration, String url,
                                String publisher, String title, String languageCode,
                                String subtitleUrl, String subtitleLanguageCode) {
        publishableVideos.put(videoID.toLowerCase(),
                new PremiumVideoImpl(videoID, videoDuration, url, publisher, title, languageCode,
                        subtitleUrl, subtitleLanguageCode));
    }

    @Override
    public void addSubtitle(String videoID, String subtitleUrl, String subtitleLanguageCode) {
        PublishableVideo video = findPublishableVideo(videoID);
        if (video instanceof PremiumVideo premiumVideo) {
            premiumVideo.addSubtitle(new SubtitleImpl(subtitleUrl, subtitleLanguageCode));
        }
    }

    @Override
    public void removeVideo(String videoID) {
        publishableVideos.remove(videoID.toLowerCase());
    }

    @Override
    public void addPodcast(String title, String authorName, String languageCode) {
        Author author = getOrCreateAuthor(authorName);
        Podcast podcast = new PodcastImpl(title, author.getName(), languageCode);
        podcasts.put(title.toLowerCase(), podcast);
        author.addPodcast(podcast);
    }

    @Override
    public void addPodcastEpisode(String title, String videoID, int videoDuration,
                                  String episodeUrl, String date, String episodeTitle) {
        Podcast podcast = findPodcast(title);
        if (podcast != null) {
            podcast.addEpisode(new PodcastEpisodeImpl(videoID, videoDuration,
                    episodeTitle, episodeUrl, date));
        }
    }

    @Override
    public void removePodcast(String title) {
        Podcast p = findPodcast(title);
        if (p != null) {
            Author author = authorsMap.get(p.getAuthor().toLowerCase());
            if (author != null) {
                author.removePodcast(p);
            }
            podcasts.remove(title.toLowerCase());
        }
    }

    @Override
    public void addShow(String authorName, String videoID, String date) {
        PublishableVideo v = findPublishableVideo(videoID);
        if (v != null) {
            Author author = getOrCreateAuthor(authorName);
            Show show = new ShowImpl(author.getName(), v.getTitle(), videoID, date);
            shows.put(v.getTitle().toLowerCase(), show);
            author.addShow(show);
        }
    }

    @Override
    public void removeShow(String title) {
        Show s = findShow(title);
        if (s != null) {
            Author author = authorsMap.get(s.getAuthor().toLowerCase());
            if (author != null) {
                author.removeShow(s);
            }
            shows.remove(title.toLowerCase());
        }
    }

    @Override
    public void addTagToTitle(String title, String tag) {
        String normalizedTag = tag.toLowerCase();
        Podcast p = findPodcast(title);
        if (p != null) {
            p.addTag(normalizedTag);
        }

        Show s = findShow(title);
        if (s != null) {
            s.addTag(normalizedTag);
        }
    }

    @Override
    public void removeTagFromTitle(String title, String tag) {
        String normalizedTag = tag.toLowerCase();
        Podcast p = findPodcast(title);
        if (p != null) {
            p.removeTag(normalizedTag);
        }

        Show s = findShow(title);
        if (s != null) {
            s.removeTag(normalizedTag);
        }
    }

    @Override
    public PublishableVideo getVideo(String videoID) {
        return findPublishableVideo(videoID);
    }

    @Override
    public Iterator<SubtitleImpl> getSubtitles(String videoID) {
        PublishableVideo video = findPublishableVideo(videoID);
        Iterator<SubtitleImpl> result = Collections.emptyIterator();

        if (video instanceof PremiumVideo premiumVideo) {
            result = premiumVideo.getSubtitles();
        }

        return result;
    }

    @Override
    public Podcast getPodcast(String title) {
        return findPodcast(title);
    }

    @Override
    public Iterator<PodcastEpisode> getPodcastEpisodes(String title) {
        Podcast podcast = findPodcast(title);
        Iterator<PodcastEpisode> result = null;
        if (podcast != null) {
            result = podcast.getEpisodes();
        }
        return result;
    }

    @Override
    public Iterator<Podcast> getAuthorPodcasts(String author) {
        Author a = authorsMap.get(author.toLowerCase());
        Iterator<Podcast> result = Collections.emptyIterator();
        if (a != null) {
            result = a.getPodcasts();
        }
        return result;
    }

    @Override
    public Show getShow(String title) {
        return findShow(title);
    }

    @Override
    public Iterator<Author> getAuthorsByProductivity() {
        List<Author> productiveAuthors = new ArrayList<>();

        for (Author a : authorsMap.values()) {
            if (a.getProductivity() > 0) {
                productiveAuthors.add(a);
            }
        }

        productiveAuthors.sort((a1, a2) -> {
            int productivityCompare = Integer.compare(a2.getProductivity(), a1.getProductivity());
            int nameCompare = a1.getName().compareToIgnoreCase(a2.getName());

            int result;
            if (productivityCompare != 0) {
                result = productivityCompare;
            } else {
                result = nameCompare;
            }
            return result;
        });
        return productiveAuthors.iterator();
    }

    @Override
    public Iterator<TaggableContent> getTaggedContent(String tag, String filter, String order) {
        String normalizedTag = tag.toLowerCase();
        List<TaggableContent> result = new ArrayList<>();

        if (filter.equals("ALL") || filter.equals("SHOW")) {
            for (Show s : shows.values()) {
                if (s.hasTag(normalizedTag)) {
                    result.add(s);
                }
            }
        }

        if (filter.equals("ALL") || filter.equals("PODCAST")) {
            for (Podcast p : podcasts.values()) {
                if (p.hasTag(normalizedTag)) {
                    result.add(p);
                }
            }
        }

        result.sort((c1, c2) -> {
            int titleCompare = c1.getTitle().compareToIgnoreCase(c2.getTitle());
            if (order.equals("DES")) {
                titleCompare = -titleCompare;
            }

            int compareResult = titleCompare;

            if (compareResult == 0) {
                boolean c1IsShow = c1 instanceof Show;
                boolean c2IsShow = c2 instanceof Show;

                if (c1IsShow && !c2IsShow) {
                    compareResult = -1;
                } else if (!c1IsShow && c2IsShow) {
                    compareResult = 1;
                }
            }
            return compareResult;
        });
        return result.iterator();
    }
}