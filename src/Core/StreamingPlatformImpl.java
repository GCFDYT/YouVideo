package Core;

import dataStructures.Array;
import dataStructures.ArrayClass;
import dataStructures.Iterator;
import java.util.Locale;

/**
 * Implementation of the StreamingPlatform interface.
 * Manages collections of publishable videos, podcasts, and shows.
 *
 * @author Gonçalo Domingos and João Domingues
 */
public class StreamingPlatformImpl implements StreamingPlatform {

    private final Array<PublishableVideo> publishableVideos;
    private final Array<Podcast> podcasts;
    private final Array<Show> shows;

    /**
     * Constructs a new StreamingPlatformImpl with pre-allocated storage.
     * Initial capacity for each internal array is set to 50 elements.
     */
    public StreamingPlatformImpl() {
        publishableVideos = new ArrayClass<>(50);
        podcasts = new ArrayClass<>(50);
        shows = new ArrayClass<>(50);
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
        PublishableVideo result = null;
        Iterator<PublishableVideo> iterator = publishableVideos.iterator();
        while (iterator.hasNext() && result == null) {
            PublishableVideo video = iterator.next();
            if (video.getVideoID().equalsIgnoreCase(videoID)) {
                result = video;
            }
        }
        return result;
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
        // 1. Check if they already exist as a Video Publisher
        Iterator<PublishableVideo> itVid = publishableVideos.iterator();
        while (itVid.hasNext()) {
            PublishableVideo v = itVid.next();
            if (v.getPublisher().equalsIgnoreCase(name)) {
                return v.getPublisher();
            }
        }

        // 2. Check if they already exist as a Podcast Author
        Iterator<Podcast> itPod = podcasts.iterator();
        while (itPod.hasNext()) {
            Podcast p = itPod.next();
            if (p.getAuthor().equalsIgnoreCase(name)) {
                return p.getAuthor();
            }
        }

        // 3. Check if they already exist as a Show Author
        Iterator<Show> itShow = shows.iterator();
        while (itShow.hasNext()) {
            Show s = itShow.next();
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
        Podcast result = null;
        Iterator<Podcast> iterator = podcasts.iterator();
        while (iterator.hasNext() && result == null) {
            Podcast podcast = iterator.next();
            if (podcast.getTitle().equalsIgnoreCase(title)) {
                result = podcast;
            }
        }
        return result;
    }

    /**
     * Searches for a show in the platform by its title.
     * Checks iteratively through the collection of shows ignoring case.
     *
     * @param title the title of the show to search for
     * @return the {@code Show} if found, or {@code null} if no show matches the title
     */
    private Show findShow(String title) {
        Show result = null;
        Iterator<Show> iterator = shows.iterator();
        while (iterator.hasNext() && result == null) {
            Show show = iterator.next();
            if (show.title().equalsIgnoreCase(title)) {
                result = show;
            }
        }
        return result;
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
        PublishableVideo searchKey = new PublishableVideoImpl(videoID, 0, "", "", "", "");
        return publishableVideos.searchForward(searchKey);
    }

    @Override
    public boolean hasPodcastEpisode(String videoID) {
        boolean found = false;
        Iterator<Podcast> podcastIterator = podcasts.iterator();
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
        boolean found = false;
        Iterator<Podcast> iterator = podcasts.iterator();
        while (iterator.hasNext() && !found) {
            Podcast podcast = iterator.next();
            if (podcast.getTitle().equalsIgnoreCase(title)) {
                found = true;
            }
        }
        return found;
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
        boolean found = false;
        Iterator<Show> iterator = shows.iterator();
        while (iterator.hasNext() && !found) {
            Show show = iterator.next();
            if (show.videoID().equalsIgnoreCase(videoID)) {
                found = true;
            }
        }
        return found;
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
        boolean found = false;
        Iterator<Podcast> iterator = podcasts.iterator();
        while (iterator.hasNext() && !found) {
            Podcast podcast = iterator.next();
            if (podcast.getAuthor().equalsIgnoreCase(author)) {
                found = true;
            }
        }
        return found;
    }

    // --- Commands ---

    @Override
    public void addPublishableVideo(String videoID, int videoDuration, String url,
                                    String publisher, String title, String languageCode) {
        String finalPublisher = getStandardizedName(publisher);
        publishableVideos.insertLast(new PublishableVideoImpl(videoID,
                videoDuration, url, finalPublisher, title, languageCode));
    }

    @Override
    public void addPremiumVideo(String videoID, int videoDuration, String url,
                                String publisher, String title, String languageCode,
                                String subtitleUrl, String subtitleLanguageCode) {
        String finalPublisher = getStandardizedName(publisher);
        publishableVideos.insertLast(new PremiumVideoImpl(videoID, videoDuration, url,
                finalPublisher, title, languageCode, subtitleUrl, subtitleLanguageCode));
    }

    @Override
    public void addSubtitle(String videoID, String subtitleUrl, String subtitleLanguageCode) {
        PremiumVideo premiumVideo = (PremiumVideo) findPublishableVideo(videoID);
        premiumVideo.addSubtitle(new SubtitleImpl(subtitleUrl, subtitleLanguageCode));
    }

    @Override
    public void removeVideo(String videoID) {
        PublishableVideo searchKey = new PublishableVideoImpl(videoID, 0, "", "", "", "");
        int index = publishableVideos.searchIndexOf(searchKey);
        if (index != -1) {
            publishableVideos.removeAt(index);
        }
    }

    @Override
    public void addPodcast(String title, String author, String languageCode) {
        String finalAuthor = getStandardizedName(author);
        podcasts.insertLast(new PodcastImpl(title, finalAuthor, languageCode));
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
        int index = -1;
        Iterator<Podcast> iterator = podcasts.iterator();
        int i = 0;
        while (iterator.hasNext() && index == -1) {
            Podcast podcast = iterator.next();
            if (podcast.getTitle().equalsIgnoreCase(title)) {
                index = i;
            }
            i++;
        }
        if (index != -1) {
            podcasts.removeAt(index);
        }
    }

    @Override
    public void addShow(String author, String videoID, String date) {
        PublishableVideo v = findPublishableVideo(videoID);
        String finalAuthor = getStandardizedName(author);
        shows.insertLast(new ShowImpl(finalAuthor, v.getTitle(), videoID, date));
    }

    @Override
    public void removeShow(String title) {
        Show toRemove = findShow(title);
        if (toRemove != null) {
            int index = shows.searchIndexOf(toRemove);
            if (index != -1) {
                shows.removeAt(index);
            }
        }
    }

    // --- Queries ---

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
        Iterator<PodcastEpisode> result;
        if (podcast == null) {
            result = null;
        } else {
            result = podcast.getEpisodes();
        }
        return result;
    }

    @Override
    public Iterator<Podcast> getAuthorPodcasts(String author) {
        Array<Podcast> authorPodcasts = new ArrayClass<>();
        for (Iterator<Podcast> iterator = podcasts.iterator(); iterator.hasNext(); ) {
            Podcast podcast = iterator.next();
            if (podcast.getAuthor().equalsIgnoreCase(author)) {
                authorPodcasts.insertLast(podcast);
            }
        }
        return authorPodcasts.iterator();
    }

    @Override
    public Show getShow(String title) {
        return findShow(title);
    }
}