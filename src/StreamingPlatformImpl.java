import java.util.*;

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
        String[] languages = Locale.getISOLanguages();

        int i = 0;
        while (i < languages.length && !isValid) {
            if (languages[i].equalsIgnoreCase(code)) {
                isValid = true;
            }
            i++;
        }
        return isValid;
    }

    @Override
    public boolean isVideoUsedInShow(String videoID) {
        boolean found = false;

        for (Show show : shows.values()) {
            if (!found && show.getVideoID().equalsIgnoreCase(videoID)) {
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

        for (Author a : authorsMap.values()) {
            if (!hasProductive && a.getProductivity() > 0) {
                hasProductive = true;
            }
        }
        return hasProductive;
    }
    
    @Override
    public boolean isTitleTaggedWith(String title, String tag) {
        Podcast p = findPodcast(title);
        Show s = findShow(title);

        boolean pTagged = (p != null && p.hasTag(tag));
        boolean sTagged = (s != null && s.hasTag(tag));
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
        PremiumVideo premiumVideo = (PremiumVideo) findPublishableVideo(videoID);
        premiumVideo.addSubtitle(new SubtitleImpl(subtitleUrl, subtitleLanguageCode));
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
        podcast.addEpisode(new PodcastEpisodeImpl(videoID, videoDuration,
                episodeTitle, episodeUrl, date));
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
        Author author = getOrCreateAuthor(authorName);
        Show show = new ShowImpl(author.getName(), v.getTitle(), videoID, date);
        shows.put(v.getTitle().toLowerCase(), show);
        author.addShow(show); 
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
        Podcast p = findPodcast(title);
        if (p != null) {
            p.addTag(tag);
        }
        
        Show s = findShow(title);
        if (s != null) {
            s.addTag(tag);
        }
    }

    @Override
    public void removeTagFromTitle(String title, String tag) {
        Podcast p = findPodcast(title);
        if (p != null) {
            p.removeTag(tag);
        }
        
        Show s = findShow(title);
        if (s != null) {
            s.removeTag(tag);
        }
    }

    @Override
    public PublishableVideo getVideo(String videoID) {
        return findPublishableVideo(videoID);
    }

    @Override
    public Iterator<SubtitleImpl> getSubtitles(String videoID) {
        PremiumVideo premiumVideo = (PremiumVideo) findPublishableVideo(videoID);
        return premiumVideo.getSubtitles();
    }

    @Override
    public Podcast getPodcast(String title) {
        return findPodcast(title);
    }

    @Override
    public Iterator<PodcastEpisode> getPodcastEpisodes(String title) {
        Podcast podcast = findPodcast(title);
        return podcast == null ? null : podcast.getEpisodes();
    }

    @Override
    public Iterator<Podcast> getAuthorPodcasts(String author) {
        Author a = authorsMap.get(author.toLowerCase());
        return a == null ? Collections.emptyIterator() : a.getPodcasts();
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

        productiveAuthors.sort(Comparator
                .comparingInt(Author::getProductivity).reversed()
                .thenComparing(Author::getName, String.CASE_INSENSITIVE_ORDER)
        );
        return productiveAuthors.iterator();
    }

    public Iterator<TaggableContent> getTaggedContent(String tag, String filter, String order) {
        List<TaggableContent> result = buildTaggedContentList(tag, filter);
        sortTaggedContent(result, order);
        return result.iterator();
    }

    private List<TaggableContent> buildTaggedContentList(String tag, String filter) {
        List<TaggableContent> result = new ArrayList<>();

        if (filter.equals("ALL") || filter.equals("SHOW")) {
            for (Show s : shows.values()) {
                if (s.hasTag(tag)) {
                    result.add(s);
                }
            }
        }

        if (filter.equals("ALL") || filter.equals("PODCAST")) {
            for (Podcast p : podcasts.values()) {
                if (p.hasTag(tag)) {
                    result.add(p);
                }
            }
        }
        return result;
    }

    private void sortTaggedContent(List<TaggableContent> list, String order) {
        list.sort((c1, c2) -> {
            int titleCompare = c1.getTitle().compareToIgnoreCase(c2.getTitle());
            if (order.equals("DES")) {
                titleCompare *= -1;
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
    }
}