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
        // Iterate through all podcasts and their respective episodes to find a matching video ID
        for (Podcast podcast : podcasts.values()) {
            Iterator<PodcastEpisode> episodeIterator = podcast.getEpisodes();
            while (episodeIterator.hasNext()) {
                if (episodeIterator.next().getVideoID().equalsIgnoreCase(videoID)) {
                    return true;
                }
            }
        }
        return false;
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
        for (String lang : Locale.getISOLanguages()) {
            if (lang.equalsIgnoreCase(code)) return true;
        }
        return false;
    }

    @Override
    public boolean isVideoUsedInShow(String videoID) {
        for (Show show : shows.values()) {
            if (show.getVideoID().equalsIgnoreCase(videoID)) return true;
        }
        return false;
    }

    @Override
    public boolean isValidEpisodeDate(String podcastTitle, String date) {
        Podcast podcast = findPodcast(podcastTitle);
        // Ensure the new episode's date is chronologically on or after the most recently added episode
        if (podcast != null && podcast.hasEpisodes()) {
            return date.compareTo(podcast.getLatestEpisode().getDate()) >= 0;
        }
        return true;
    }

    @Override
    public boolean hasAuthorPodcasts(String author) {
        Author a = authorsMap.get(author.toLowerCase());
        return a != null && a.getPodcasts().hasNext();
    }
    
    @Override
    public boolean hasProductiveAuthors() {
        for (Author a : authorsMap.values()) {
            if (a.getProductivity() > 0) return true;
        }
        return false;
    }
    
    @Override
    public boolean isTitleTaggedWith(String title, String tag) {
        Podcast p = findPodcast(title);
        Show s = findShow(title);
        
        // A title might be a Podcast or a Show, so we check both collections and verify if the tag exists
        boolean pTagged = (p != null && ((TaggableContent) p).hasTag(tag));
        boolean sTagged = (s != null && ((TaggableContent) s).hasTag(tag));
        return pTagged || sTagged;
    }

    @Override
    public void addPublishableVideo(String videoID, int videoDuration, String url,
                                    String publisher, String title, String languageCode) {
        publishableVideos.put(videoID.toLowerCase(),
                new PublishableVideoImpl(videoID, videoDuration, url, publisher, title, languageCode));
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
        podcast.addEpisode(new PodcastEpisodeImpl(videoID, videoDuration, episodeTitle, episodeUrl, date));
    }

    @Override
    public void removePodcast(String title) {
        Podcast p = findPodcast(title);
        if (p != null) {
            Author author = authorsMap.get(((TaggableContent) p).getAuthor().toLowerCase());
            // Safely remove the content from the author's specific contribution list before deleting it globally
            if (author != null) author.removePodcast(p); 
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
            Author author = authorsMap.get(((TaggableContent) s).getAuthor().toLowerCase());
            // Safely remove the content from the author's specific contribution list before deleting it globally
            if (author != null) author.removeShow(s); 
            shows.remove(title.toLowerCase());
        }
    }

    @Override
    public void addTagToTitle(String title, String tag) {
        // Apply the tag operation to whichever content type (Podcast or Show) matches the title
        Podcast p = findPodcast(title);
        if (p != null) ((TaggableContent) p).addTag(tag);
        
        Show s = findShow(title);
        if (s != null) ((TaggableContent) s).addTag(tag);
    }

    @Override
    public void removeTagFromTitle(String title, String tag) {
        Podcast p = findPodcast(title);
        if (p != null) ((TaggableContent) p).removeTag(tag);
        
        Show s = findShow(title);
        if (s != null) ((TaggableContent) s).removeTag(tag);
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
        
        // Sort primarily by productivity count (descending), using alphabetical author names as a tie-breaker
        productiveAuthors.sort(Comparator
                .comparingInt(Author::getProductivity).reversed()
                .thenComparing(Author::getName, String.CASE_INSENSITIVE_ORDER)
        );
        
        return productiveAuthors.iterator();
    }

    @Override
    public Iterator<TaggableContent> getTaggedContent(String tag, String filter, String order) {
        List<TaggableContent> result = new ArrayList<>();
        
        // Populate the result list based on the requested filter type (ALL, SHOW, or PODCAST)
        if (filter.equals("ALL") || filter.equals("SHOW")) {
            for (Show s : shows.values()) {
                if (((TaggableContent) s).hasTag(tag)) {
                    result.add((TaggableContent) s);
                }
            }
        }
        
        if (filter.equals("ALL") || filter.equals("PODCAST")) {
            for (Podcast p : podcasts.values()) {
                if (((TaggableContent) p).hasTag(tag)) {
                    result.add((TaggableContent) p);
                }
            }
        }
        
        // Custom sort mapping the ASC/DES parameter to title alphabetization and handling type tie-breakers
        result.sort((c1, c2) -> {
            int titleCompare = c1.getTitle().compareToIgnoreCase(c2.getTitle());
            if (order.equals("DES")) titleCompare *= -1; // Reverse for Descending order
            
            if (titleCompare != 0) return titleCompare;
            
            // If titles are identical, prioritize Shows over Podcasts as the final tie-breaker
            if (c1 instanceof Show && c2 instanceof Podcast) return -1;
            if (c1 instanceof Podcast && c2 instanceof Show) return 1;
            return 0;
        });
        
        return result.iterator();
    }
}