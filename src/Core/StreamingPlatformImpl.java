package Core;

import dataStructures.Array;
import dataStructures.ArrayClass;
import dataStructures.Iterator;
import java.util.Locale;

public class StreamingPlatformImpl implements StreamingPlatform {

    private final Array<PublishableVideo> publishableVideos;
    private final Array<Podcast> podcasts;
    private final Array<Show> shows;

    public StreamingPlatformImpl() {
        publishableVideos = new ArrayClass<>();
        podcasts = new ArrayClass<>();
        shows = new ArrayClass<>();
    }

    // --- Internal Helpers ---

    private PublishableVideo findPublishableVideo(String videoID) {
        Iterator<PublishableVideo> it = publishableVideos.iterator();
        while (it.hasNext()) {
            PublishableVideo v = it.next();
            if (v.getVideoID().equalsIgnoreCase(videoID)) return v;
        }
        return null;
    }

    private PodcastEpisode findPodcastEpisode(String videoID) {
        Iterator<Podcast> pIt = podcasts.iterator();
        while (pIt.hasNext()) {
            Podcast p = pIt.next();
            Iterator<PodcastEpisode> epIt = p.getEpisodes();
            while (epIt.hasNext()) {
                PodcastEpisode ep = epIt.next();
                if (ep.getVideoID().equalsIgnoreCase(videoID)) return ep;
            }
        }
        return null;
    }

    private Podcast findPodcast(String title) {
        Iterator<Podcast> it = podcasts.iterator();
        while (it.hasNext()) {
            Podcast p = it.next();
            if (p.getTitle().equalsIgnoreCase(title)) return p;
        }
        return null;
    }

    private Show findShow(String title) {
        Iterator<Show> it = shows.iterator();
        while (it.hasNext()) {
            Show s = it.next();
            if (s.title().equalsIgnoreCase(title)) return s;
        }
        return null;
    }

    // --- Pre-condition Checks ---

    @Override
    public boolean hasVideo(String videoID) {
        return hasPublishableVideo(videoID) || hasPodcastEpisode(videoID);
    }

    @Override
    public boolean hasPublishableVideo(String videoID) {
        return findPublishableVideo(videoID) != null;
    }

    @Override
    public boolean hasPodcastEpisode(String videoID) {
        return findPodcastEpisode(videoID) != null;
    }

    @Override
    public boolean isPremiumVideo(String videoID) {
        PublishableVideo v = findPublishableVideo(videoID);
        return v instanceof PremiumVideo;
    }

    @Override
    public boolean hasPodcast(String title) {
        return findPodcast(title) != null;
    }

    @Override
    public boolean hasShow(String title) {
        return findShow(title) != null;
    }

    @Override
    public boolean isValidLanguageCode(String code) {
        String[] languages = Locale.getISOLanguages();
        for (int i = 0; i < languages.length; i++) {
            if (languages[i].equalsIgnoreCase(code)) return true;
        }
        return false;
    }

    @Override
    public boolean isVideoUsedInShow(String videoID) {
        Iterator<Show> it = shows.iterator();
        while (it.hasNext()) {
            Show s = it.next();
            if (s.videoID().equalsIgnoreCase(videoID)) return true;
        }
        return false;
    }

    @Override
    public boolean isValidEpisodeDate(String podcastTitle, String date) {
        Podcast p = findPodcast(podcastTitle);
        if (p != null && p.hasEpisodes()) {
            return date.compareTo(p.getLatestEpisode().getDate()) >= 0;
        }
        return true;
    }

    @Override
    public boolean hasAuthorPodcasts(String author) {
        Iterator<Podcast> it = podcasts.iterator();
        while (it.hasNext()) {
            Podcast p = it.next();
            if (p.getAuthor().equalsIgnoreCase(author)) return true;
        }
        return false;
    }

    // --- Commands ---

    @Override
    public void addPublishableVideo(String videoID, int videoDuration, String url, String publisher, String title, String languageCode) {
        publishableVideos.insertLast(new PublishableVideoImpl(videoID, videoDuration, url, publisher, title, languageCode));
    }

    @Override
    public void addPremiumVideo(String videoID, int videoDuration, String url, String publisher, String title, String languageCode, String subtitleUrl, String subtitleLanguageCode) {
        publishableVideos.insertLast(new PremiumVideoImpl(videoID, videoDuration, url, publisher, title, languageCode, subtitleUrl, subtitleLanguageCode));
    }

    @Override
    public void addSubtitle(String videoID, String subtitleUrl, String subtitleLanguageCode) {
        PremiumVideo pv = (PremiumVideo) findPublishableVideo(videoID);
        pv.addSubtitle(new Subtitle(subtitleUrl, subtitleLanguageCode));
    }

    @Override
    public void removeVideo(String videoID) {
        for (int i = 0; i < publishableVideos.size(); i++) {
            if (publishableVideos.get(i).getVideoID().equalsIgnoreCase(videoID)) {
                publishableVideos.removeAt(i);
                break;
            }
        }
    }

    @Override
    public void addPodcast(String title, String author, String languageCode) {
        podcasts.insertLast(new PodcastImpl(title, author, languageCode));
    }

    @Override
    public void addPodcastEpisode(String title, String videoID, int videoDuration, String episodeUrl, String date) {
        Podcast podcast = findPodcast(title);
        podcast.addEpisode(new PodcastEpisodeImpl(videoID, videoDuration, "Episode " + videoID, episodeUrl, date));
    }

    @Override
    public void removePodcast(String title) {
        for (int i = 0; i < podcasts.size(); i++) {
            if (podcasts.get(i).getTitle().equalsIgnoreCase(title)) {
                podcasts.removeAt(i);
                break;
            }
        }
    }

    @Override
    public void addShow(String author, String videoID, String date) {
        PublishableVideo v = findPublishableVideo(videoID);
        shows.insertLast(new ShowImpl(author, v.getTitle(), videoID, date));
    }

    @Override
    public void removeShow(String title) {
        for (int i = 0; i < shows.size(); i++) {
            if (shows.get(i).title().equalsIgnoreCase(title)) {
                shows.removeAt(i);
                break;
            }
        }
    }

    // --- Queries ---

    @Override
    public PublishableVideo getVideo(String videoID) {
        return findPublishableVideo(videoID);
    }

    @Override
    public Iterator<Subtitle> getSubtitles(String videoID) {
        PremiumVideo premiumVideo = (PremiumVideo) findPublishableVideo(videoID);
        return premiumVideo.getSubtitles();
    }

    @Override
    public Podcast getPodcast(String title) {
        return findPodcast(title);
    }

    @Override
    public Iterator<PodcastEpisode> getPodcastEpisodes(String title) {
        return findPodcast(title).getEpisodes();
    }

    @Override
    public Iterator<Podcast> getAuthorPodcasts(String author) {
        Array<Podcast> authorPods = new ArrayClass<>();
        Iterator<Podcast> it = podcasts.iterator();
        while (it.hasNext()) {
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