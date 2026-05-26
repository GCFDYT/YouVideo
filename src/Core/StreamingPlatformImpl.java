package Core;

import java.util.*;
import java.util.Locale;

/**
 * Implementation of the StreamingPlatform interface.
 * Manages collections of publishable videos, podcasts, and shows.
 *
 * @author Gonçalo Domingos and João Domingues
 */
public class StreamingPlatformImpl implements StreamingPlatform {

    private final Map<String, PublishableVideo> publishableVideos;
    private final Map<String, Podcast> podcasts;
    private final Map<String, Show> shows;

    /**
     * Constructs a new StreamingPlatformImpl with pre-allocated storage.
     * Initial capacity for each internal array is set to 50 elements.
     */
    public StreamingPlatformImpl() {
        publishableVideos = new HashMap<>();
        podcasts = new HashMap<>();
        shows = new HashMap<>();
    }

    // --- Internal Helpers ---

    /**
     * Searches for a publishable video in the platform by its unique ID.
     * Checks iteratively through the collection of publishable videos ignoring case.
     *
     * @param videoID the unique identifier of the video to search for
     * @return the {@code PublishableVideo} if found, or {@code null} if no video matches the ID
     */
    private PublishableVideo findPublishableVideo(String videoID) {
        return publishableVideos.get(videoID.toLowerCase());
    }
    /**
     * Standardizes the casing of an author or publisher's name.
     * Checks existing videos, podcasts, and shows to see if the author/publisher
     * already exists in the system. If they do, it returns the exact letter casing
     * used in their first recorded entry to maintain a consistent signature.
     *
     * @param name the name of the author or publisher to check
     * @return the standardized name string if a prior record exists, or the original provided name if it is a new author
     */
    private String getStandardizedName(String name) {
        for (PublishableVideo v : publishableVideos.values()) {
            if (v.getPublisher().equalsIgnoreCase(name)) {
                return v.getPublisher();
            }
        }

        for (Podcast p : podcasts.values()) {
            if (p.getAuthor().equalsIgnoreCase(name)) {
                return p.getAuthor();
            }
        }

        // 3. Check if they already exist as a Show Author
        for (Show s : shows.values()) {
            if (s.author().equalsIgnoreCase(name)) {
                return s.author();
            }
        }

        // If no match is found anywhere, return the exact casing they just typed
        return name;
    }

    /**
     * Searches for a podcast in the platform by its title.
     * Checks iteratively through the collection of podcasts ignoring case.
     *
     * @param title the title of the podcast to search for
     * @return the {@code Podcast} if found, or {@code null} if no podcast matches the title
     */
    private Podcast findPodcast(String title) {
        return podcasts.get(title.toLowerCase());
    }

    /**
     * Searches for a show in the platform by its title.
     * Checks iteratively through the collection of shows ignoring case.
     *
     * @param title the title of the show to search for
     * @return the {@code Show} if found, or {@code null} if no show matches the title
     */
    private Show findShow(String title) {
        return shows.get(title.toLowerCase());
    }

    // --- Pre-condition Checks ---

    @Override
    public boolean hasVideo(String videoID) {
        boolean hasPublishable = hasPublishableVideo(videoID);
        boolean hasPodcast = hasPodcastEpisode(videoID);
        return hasPublishable || hasPodcast;
    }

    @Override
    public boolean hasPublishableVideo(String videoID) {
        return publishableVideos.containsKey(videoID.toLowerCase());
    }

    @Override
    public boolean hasPodcastEpisode(String videoID) {
        boolean found = false;
        Iterator<Podcast> podcastIterator = podcasts.values().iterator();
        while (podcastIterator.hasNext() && !found) {
            Podcast podcast = podcastIterator.next();
            Iterator<PodcastEpisode> episodeIterator = podcast.getEpisodes();
            while (episodeIterator.hasNext() && !found) {
                PodcastEpisode episode = episodeIterator.next();
                if (episode.getVideoID().equalsIgnoreCase(videoID)) {
                    found = true;
                }
            }
        }
        return found;
    }

    @Override
    public boolean isPremiumVideo(String videoID) {
        PublishableVideo video = findPublishableVideo(videoID);
        return video instanceof PremiumVideo;
    }

    @Override
    public boolean hasPodcast(String title) {
        return podcasts.containsKey(title.toLowerCase());
    }

    @Override
    public boolean hasShow(String title) {
        return findShow(title) != null;
    }

    @Override
    public boolean isValidLanguageCode(String code) {
        boolean valid = false;
        String[] languages = Locale.getISOLanguages();
        int i = 0;
        while (i < languages.length && !valid) {
            if (languages[i].equalsIgnoreCase(code)) {
                valid = true;
            }
            i++;
        }
        return valid;
    }

    @Override
    public boolean isVideoUsedInShow(String videoID) {
        boolean videoUsed = false;
        for (Show show : shows.values()) {
            if (show.videoID().equalsIgnoreCase(videoID)) {
                videoUsed = true;
            }
        }
        return videoUsed;
    }

    @Override
    public boolean isValidEpisodeDate(String podcastTitle, String date) {
        boolean valid = true;
        Podcast podcast = findPodcast(podcastTitle);
        if (podcast != null && podcast.hasEpisodes()) {
            valid = date.compareTo(podcast.getLatestEpisode().getDate()) >= 0;
        }
        return valid;
    }

    @Override
    public boolean hasAuthorPodcasts(String author) {
        boolean authorPodcasts = false;
        for (Podcast podcast : podcasts.values()) {
            if (podcast.getAuthor().equalsIgnoreCase(author)) {
                authorPodcasts = true;
            }
        }
        return authorPodcasts;
    }

    // --- Commands ---

    @Override
    public void addPublishableVideo(String videoID, int videoDuration, String url,
                                    String publisher, String title, String languageCode) {
        String finalPublisher = getStandardizedName(publisher);
        publishableVideos.put(videoID.toLowerCase(),
                new PublishableVideoImpl(videoID, videoDuration, url,
                        finalPublisher, title, languageCode));
    }

    @Override
    public void addPremiumVideo(String videoID, int videoDuration, String url,
                                String publisher, String title, String languageCode,
                                String subtitleUrl, String subtitleLanguageCode) {
        String finalPublisher = getStandardizedName(publisher);
        publishableVideos.put(videoID.toLowerCase(),
                new PremiumVideoImpl(videoID, videoDuration, url,
                        finalPublisher, title, languageCode,
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
    public void addPodcast(String title, String author, String languageCode) {
        String finalAuthor = getStandardizedName(author);
        podcasts.put(title.toLowerCase(), new PodcastImpl(title, finalAuthor, languageCode));
    }

    @Override
    public void addPodcastEpisode(String title, String videoID, int videoDuration,
                                  String episodeUrl, String date, String episodeTitle) {
        Podcast podcast = findPodcast(title);
        podcast.addEpisode(new PodcastEpisodeImpl(videoID, videoDuration, episodeTitle,
                episodeUrl, date));
    }

    @Override
    public void removePodcast(String title) {
        podcasts.remove(title.toLowerCase());
    }

    @Override
    public void addShow(String author, String videoID, String date) {
        PublishableVideo v = findPublishableVideo(videoID);
        String finalAuthor = getStandardizedName(author);
        shows.put(v.getTitle().toLowerCase(), new ShowImpl(finalAuthor,
                v.getTitle(), videoID, date));
    }

    @Override
    public void removeShow(String title) {
        shows.remove(title.toLowerCase());
    }

    // --- Queries ---

    @Override
    public PublishableVideo getVideo(String videoID) {
        return findPublishableVideo(videoID);
    }

    @Override
    public java.util.Iterator<SubtitleImpl> getSubtitles(String videoID) {
        PremiumVideo premiumVideo = (PremiumVideo) findPublishableVideo(videoID);
        return premiumVideo.getSubtitles();
    }

    @Override
    public Podcast getPodcast(String title) {
        return findPodcast(title);
    }

    @Override
    public java.util.Iterator<PodcastEpisode> getPodcastEpisodes(String title) {
        Podcast podcast = findPodcast(title);
        Iterator<PodcastEpisode> result;
        if (podcast == null) {
            result = null;
        } else {
            result = podcast.getEpisodes();
        }
        return result;
    }

    @Override
    public java.util.Iterator<Podcast> getAuthorPodcasts(String author) {
        List<Podcast> authorPodcasts = new ArrayList<>();

        for (Podcast podcast : podcasts.values()) {
            if (podcast.getAuthor().equalsIgnoreCase(author)) {
                authorPodcasts.add(podcast);
            }
        }
        return authorPodcasts.iterator();
    }

    @Override
    public Show getShow(String title) {
        return findShow(title);
    }
}