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
        PublishableVideo v = findPublishableVideo(videoID);
        return v instanceof PremiumVideo;
    }

    @Override
    public boolean hasPodcast(String title) {
        Podcast searchKey = new PodcastImpl(title, "", "");
        return podcasts.searchForward(searchKey);
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
        Iterator<Show> it = shows.iterator();
        while (it.hasNext() && !found) {
            Show s = it.next();
            if (s.videoID().equalsIgnoreCase(videoID)) {
                found = true;
            }
        }
        return found;
    }

    @Override
    public boolean isValidEpisodeDate(String podcastTitle, String date) {
        boolean valid = true;
        Podcast p = findPodcast(podcastTitle);
        if (p != null && p.hasEpisodes()) {
            valid = date.compareTo(p.getLatestEpisode().getDate()) >= 0;
        }
        return valid;
    }

    @Override
    public boolean hasAuthorPodcasts(String author) {
        boolean found = false;
        Iterator<Podcast> it = podcasts.iterator();
        while (it.hasNext() && !found) {
            Podcast p = it.next();
            if (p.getAuthor().equalsIgnoreCase(author)) {
                found = true;
            }
        }
        return found;
    }

    // --- Commands ---

    @Override
    public void addPublishableVideo(String videoID, int videoDuration, String url,
                                    String publisher, String title, String languageCode) {
        publishableVideos.insertLast(new PublishableVideoImpl(videoID,
                videoDuration, url, publisher, title, languageCode));
    }

    @Override
    public void addPremiumVideo(String videoID, int videoDuration, String url,
                                String publisher, String title, String languageCode,
                                String subtitleUrl, String subtitleLanguageCode) {
        publishableVideos.insertLast(new PremiumVideoImpl(videoID, videoDuration, url,
                publisher, title, languageCode, subtitleUrl, subtitleLanguageCode));
    }

    @Override
    public void addSubtitle(String videoID, String subtitleUrl, String subtitleLanguageCode) {
        PremiumVideo pv = (PremiumVideo) findPublishableVideo(videoID);
        pv.addSubtitle(new SubtitleImpl(subtitleUrl, subtitleLanguageCode));
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
        podcasts.insertLast(new PodcastImpl(title, author, languageCode));
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
        Podcast searchKey = new PodcastImpl(title, "", "");
        int index = podcasts.searchIndexOf(searchKey);
        if (index != -1) {
            podcasts.removeAt(index);
        }
    }

    @Override
    public void addShow(String author, String videoID, String date) {
        PublishableVideo v = findPublishableVideo(videoID);
        shows.insertLast(new ShowImpl(author, v.getTitle(), videoID, date));
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
        Array<Podcast> authorPods = new ArrayClass<>();
        for (Iterator<Podcast> it = podcasts.iterator(); it.hasNext(); ) {
            Podcast p = it.next();
            if (p.getAuthor().equalsIgnoreCase(author)) {
                authorPods.insertLast(p);
            }
        }
        return authorPods.iterator();
    }

    @Override
    public Show getShow(String title) {
        return findShow(title);
    }
}