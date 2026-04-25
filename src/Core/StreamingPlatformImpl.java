package Core;

import dataStructures.Array;
import dataStructures.ArrayClass;
import java.util.Locale;

public class StreamingPlatformImpl implements StreamingPlatform {

    private final Array<PublishableVideo> videos;
    private final Array<Podcast> podcasts;
    private final Array<Show> shows;
    private final Array<PodcastEpisode> episodes;
    private final Array<String> episodeToPodcast;

    public StreamingPlatformImpl() {
        videos = new ArrayClass<>();
        podcasts = new ArrayClass<>();
        shows = new ArrayClass<>();
        episodes = new ArrayClass<>();
        episodeToPodcast = new ArrayClass<>();
    }

    private PublishableVideo findPublishableVideoById(String videoId) {
        PublishableVideo found = null;
        for (int i = 0; i < videos.size() && found == null; i++) {
            PublishableVideo video = videos.get(i);
            if (video.getVideoID().equals(videoId)) {
                found = video;
            }
        }
        return found;
    }

    private PodcastEpisode findEpisodeById(String videoId) {
        PodcastEpisode found = null;
        for (int i = 0; i < episodes.size() && found == null; i++) {
            PodcastEpisode episode = episodes.get(i);
            if (episode.getVideoID().equals(videoId)) {
                found = episode;
            }
        }
        return found;
    }

    private boolean isVideoUnpublishable(String videoId) {
        return findPublishableVideoById(videoId) == null;
    }

    private boolean isVideoNotPremium(String videoId) {
        PublishableVideo video = findPublishableVideoById(videoId);
        return !(video instanceof PremiumVideo);
    }

    private boolean isPodcastEpisode(String videoId) {
        return findEpisodeById(videoId) != null;
    }

    private String getPodcastTitleForEpisode(String episodeId) {
        String podcastTitle = null;
        for (int i = 0; i < episodeToPodcast.size() && podcastTitle == null; i += 2) {
            String episodeIdStored = episodeToPodcast.get(i);
            if (episodeIdStored.equals(episodeId)) {
                podcastTitle = episodeToPodcast.get(i + 1);
            }
        }
        return podcastTitle;
    }

    private void addEpisodeToPodcastMapping(String episodeId, String podcastTitle) {
        episodeToPodcast.insertLast(episodeId);
        episodeToPodcast.insertLast(podcastTitle);
    }

    private void removeEpisodeFromPodcastMapping(String episodeId) {
        boolean found = false;
        for (int i = 0; i < episodeToPodcast.size() && !found; i += 2) {
            String episodeIdStored = episodeToPodcast.get(i);
            if (episodeIdStored.equals(episodeId)) {
                episodeToPodcast.removeAt(i + 1);
                episodeToPodcast.removeAt(i);
                found = true;
            }
        }
    }

    private void removeAllEpisodesFromPodcast(String podcastTitle) {
        for (int i = episodes.size() - 1; i >= 0; i--) {
            PodcastEpisode episode = episodes.get(i);
            String episodePodcast = getPodcastTitleForEpisode(episode.getVideoID());
            if (episodePodcast != null && episodePodcast.equalsIgnoreCase(podcastTitle)) {
                removeEpisodeFromPodcastMapping(episode.getVideoID());
                episodes.removeAt(i);
            }
        }
    }

    private Podcast findPodcastByTitle(String title) {
        Podcast found = null;
        for (int i = 0; i < podcasts.size() && found == null; i++) {
            Podcast podcast = podcasts.get(i);
            if (podcast.title().equalsIgnoreCase(title)) {
                found = podcast;
            }
        }
        return found;
    }

    private boolean podcastExists(String title) {
        return findPodcastByTitle(title) != null;
    }

    private int findPodcastIndex(String title) {
        int index = -1;
        for (int i = 0; i < podcasts.size() && index == -1; i++) {
            Podcast podcast = podcasts.get(i);
            if (podcast.title().equalsIgnoreCase(title)) {
                index = i;
            }
        }
        return index;
    }

    private Array<PodcastEpisode> getPodcastEpisodesList(String podcastTitle) {
        Array<PodcastEpisode> result = new ArrayClass<>();
        for (int i = 0; i < episodes.size(); i++) {
            PodcastEpisode episode = episodes.get(i);
            String episodePodcast = getPodcastTitleForEpisode(episode.getVideoID());
            if (episodePodcast != null && episodePodcast.equalsIgnoreCase(podcastTitle)) {
                if (result.size() == 0) {
                    result.insertLast(episode);
                } else {
                    boolean inserted = false;
                    for (int j = 0; j < result.size() && !inserted; j++) {
                        PodcastEpisode existing = result.get(j);
                        if (episode.getDate().compareTo(existing.getDate()) >= 0) {
                            result.insertAt(episode, j);
                            inserted = true;
                        }
                    }
                    if (!inserted) {
                        result.insertLast(episode);
                    }
                }
            }
        }
        return result;
    }

    private Show findShowByTitle(String title) {
        Show found = null;
        for (int i = 0; i < shows.size() && found == null; i++) {
            Show show = shows.get(i);
            if (show.title().equalsIgnoreCase(title)) {
                found = show;
            }
        }
        return found;
    }

    private boolean showExists(String title) {
        return findShowByTitle(title) != null;
    }

    private int findShowIndex(String title) {
        int index = -1;
        for (int i = 0; i < shows.size() && index == -1; i++) {
            Show show = shows.get(i);
            if (show.title().equalsIgnoreCase(title)) {
                index = i;
            }
        }
        return index;
    }

    private boolean isVideoUsedInShow(String videoID) {
        boolean used = false;
        for (int i = 0; i < shows.size() && !used; i++) {
            Show show = shows.get(i);
            if (show.videoID().equals(videoID)) {
                used = true;
            }
        }
        return used;
    }

    private int findVideoIndexToRemove(String videoID) {
        int index = -1;
        for (int i = 0; i < videos.size() && index == -1; i++) {
            PublishableVideo video = videos.get(i);
            if (video.getVideoID().equals(videoID)) {
                index = i;
            }
        }
        return index;
    }

    private boolean isLanguageCodeInvalid(String languageCode) {
        String[] languages = Locale.getISOLanguages();
        boolean valid = false;
        int i = 0;
        while (i < languages.length && !valid) {
            if (languages[i].equalsIgnoreCase(languageCode)) {
                valid = true;
            }
            i++;
        }
        return !valid;
    }

    private boolean isDurationOutOfBounds(int duration) {
        return duration <= 0;
    }

    @Override
    public Status<String> addPublishableVideo(String videoId, int videoDuration,
                                              String url, String publisher, String title,
                                              String languageCode) {
        Status<String> result;
        if (isLanguageCodeInvalid(languageCode)) {
            result = StatusImpl.error(Main.INV_LANGUAGE);
        } else if (isDurationOutOfBounds(videoDuration)) {
            result = StatusImpl.error(Main.INV_VALUE);
        } else if (findPublishableVideoById(videoId) != null) {
            result = StatusImpl.error(Main.VIDEO_ID_EXISTS);
        } else {
            PublishableVideo video = new PublishableVideoImpl(videoId, videoDuration, url,
                    publisher, title, languageCode);
            videos.insertLast(video);
            result = StatusImpl.success(Main.VIDEO_CREATED, videoId);
        }
        return result;
    }

    @Override
    public Status<String> addPremiumVideo(String videoID, int videoDuration, String url,
                                          String publisher, String title,
                                          String languageCode, String subtitleUrl,
                                          String subtitleLanguageCode) {
        Status<String> result;
        if (isLanguageCodeInvalid(languageCode)) {
            result = StatusImpl.error(Main.INV_LANGUAGE);
        } else if (isLanguageCodeInvalid(subtitleLanguageCode)) {
            result = StatusImpl.error(Main.INV_SUBTITLE);
        } else if (isDurationOutOfBounds(videoDuration)) {
            result = StatusImpl.error(Main.INV_VALUE);
        } else if (findPublishableVideoById(videoID) != null) {
            result = StatusImpl.error(Main.VIDEO_ID_EXISTS);
        } else {
            PremiumVideo video = new PremiumVideoImpl(videoID, videoDuration, url, publisher,
                    title, languageCode, subtitleUrl, subtitleLanguageCode);
            videos.insertLast(video);
            result = StatusImpl.success(Main.PREMIUM_VIDEO_CREATED, videoID);
        }
        return result;
    }

    @Override
    public Status<Void> addSubtitle(String videoID, String subtitleUrl,
                                    String subtitleLanguageCode) {
        Status<Void> result;
        if (isLanguageCodeInvalid(subtitleLanguageCode)) {
            result = StatusImpl.error(Main.INV_SUBTITLE);
        } else if (isVideoUnpublishable(videoID)) {
            result = StatusImpl.error(Main.VIDEO_NOT_FOUND);
        } else if (isVideoNotPremium(videoID)) {
            result = StatusImpl.error(Main.NOT_PREMIUM);
        } else {
            PremiumVideo premiumVideo = (PremiumVideo) findPublishableVideoById(videoID);
            Subtitle subtitle = new Subtitle(subtitleUrl, subtitleLanguageCode);
            premiumVideo.addSubtitle(subtitle);
            result = StatusImpl.success(Main.SUBTITLE_ADDED);
        }
        return result;
    }

    @Override
    public Status<PremiumVideo> getSubtitleList(String videoID) {
        Status<PremiumVideo> result;
        if (isVideoNotPremium(videoID)) {
            result = StatusImpl.error(Main.NO_PREMIUM_VIDEO);
        } else {
            PremiumVideo premiumVideo = (PremiumVideo) findPublishableVideoById(videoID);
            result = StatusImpl.successWithData(premiumVideo);
        }
        return result;
    }

    @Override
    public Status<PublishableVideo> getVideo(String videoId) {
        Status<PublishableVideo> result;
        PublishableVideo video = findPublishableVideoById(videoId);
        if (video == null) {
            result = StatusImpl.error(Main.PUBLISHABLE_NOT_FOUND);
        } else {
            result = StatusImpl.successWithData(video);
        }
        return result;
    }

    @Override
    public Status<Void> removeVideo(String videoID) {
        Status<Void> result;
        if (isVideoUnpublishable(videoID)) {
            result = StatusImpl.error(Main.VIDEO_NOT_FOUND);
        } else if (isPodcastEpisode(videoID)) {
            result = StatusImpl.error(Main.CANT_REMOVE_EPISODE);
        } else if (isVideoUsedInShow(videoID)) {
            result = StatusImpl.error(Main.CANT_REMOVE_USED_VIDEO);
        } else {
            int indexToRemove = findVideoIndexToRemove(videoID);
            if (indexToRemove != -1) {
                videos.removeAt(indexToRemove);
                result = StatusImpl.success(Main.VIDEO_REMOVED);
            } else {
                result = StatusImpl.error(Main.VIDEO_NOT_FOUND);
            }
        }
        return result;
    }

    @Override
    public Status<Void> addPodcast(String title, String author, String languageCode) {
        Status<Void> result;
        if (isLanguageCodeInvalid(languageCode)) {
            result = StatusImpl.error(Main.INV_LANGUAGE);
        } else if (podcastExists(title)) {
            result = StatusImpl.error(Main.PODCAST_EXISTS);
        } else {
            Podcast podcast = new PodcastClassImpl(title, author, languageCode);
            podcasts.insertLast(podcast);
            result = StatusImpl.success(Main.PODCAST_CREATED);
        }
        return result;
    }

    @Override
    public Status<Void> addPodcastEpisode(String title, String videoID, int videoDuration,
                                          String episodeUrl, String date) {
        Status<Void> result;
        Podcast podcast = findPodcastByTitle(title);
        if (isDurationOutOfBounds(videoDuration)) {
            result = StatusImpl.error(Main.INV_VALUE);
        } else if (podcast == null) {
            result = StatusImpl.error(Main.PODCAST_NOT_FOUND);
        } else if (findPublishableVideoById(videoID) != null || isPodcastEpisode(videoID)) {
            result = StatusImpl.error(Main.EPISODE_ID_EXISTS);
        } else {
            Array<PodcastEpisode> existingEpisodes = getPodcastEpisodesList(title);
            boolean dateValid = true;
            if (existingEpisodes.size() > 0) {
                PodcastEpisode latest = existingEpisodes.get(0);
                if (date.compareTo(latest.getDate()) < 0) {
                    dateValid = false;
                }
            }
            if (!dateValid) {
                result = StatusImpl.error(Main.INV_EPISODE_DATE);
            } else {
                PodcastEpisode episode = new PodcastEpisodeImpl(videoID, videoDuration,
                        "Episode " + videoID, episodeUrl, date);
                episodes.insertLast(episode);
                addEpisodeToPodcastMapping(videoID, title);
                result = StatusImpl.success(Main.EPISODE_ADDED);
            }
        }
        return result;
    }

    @Override
    public Status<Podcast> getPodcast(String title) {
        Status<Podcast> result;
        Podcast podcast = findPodcastByTitle(title);
        if (podcast == null) {
            result = StatusImpl.error(Main.PODCAST_NOT_FOUND);
        } else {
            result = StatusImpl.successWithData(podcast);
        }
        return result;
    }

    @Override
    public Status<Array<PodcastEpisode>> getPodcastEpisodes(String title) {
        Status<Array<PodcastEpisode>> result;
        Podcast podcast = findPodcastByTitle(title);
        if (podcast == null) {
            result = StatusImpl.error(Main.PODCAST_NOT_FOUND);
        } else {
            Array<PodcastEpisode> episodesList = getPodcastEpisodesList(title);
            if (episodesList.size() == 0) {
                result = StatusImpl.error(Main.NO_EPISODES);
            } else {
                result = StatusImpl.successWithData(episodesList);
            }
        }
        return result;
    }

    @Override
    public Status<Array<Podcast>> getAuthorPodcasts(String author) {
        Status<Array<Podcast>> result;
        Array<Podcast> authorPodcasts = new ArrayClass<>();
        for (int i = 0; i < podcasts.size(); i++) {
            Podcast podcast = podcasts.get(i);
            if (podcast.author().equalsIgnoreCase(author)) {
                authorPodcasts.insertLast(podcast);
            }
        }

        if (authorPodcasts.size() == 0) {
            result = StatusImpl.error(Main.NO_USER_PODCASTS);
        } else {
            result = StatusImpl.successWithData(authorPodcasts);
        }
        return result;
    }

    @Override
    public Status<Void> removePodcast(String title) {
        Status<Void> result;
        int indexToRemove = findPodcastIndex(title);
        if (indexToRemove == -1) {
            result = StatusImpl.error(Main.PODCAST_NOT_FOUND);
        } else {
            removeAllEpisodesFromPodcast(title);
            podcasts.removeAt(indexToRemove);
            result = StatusImpl.success(Main.PODCAST_REMOVED);
        }
        return result;
    }

    @Override
    public Status<Void> addShow(String author, String videoId, String date) {
        Status<Void> result;
        PublishableVideo video = findPublishableVideoById(videoId);
        if (video == null) {
            result = StatusImpl.error(Main.SHOW_NOT_FOUND);
        } else if (showExists(video.getTitle())) {
            result = StatusImpl.error(Main.SHOW_EXISTS);
        } else {
            Show show = new ShowImpl(author, video.getTitle(), videoId, date);
            shows.insertLast(show);
            result = StatusImpl.success(Main.SHOW_CREATED);
        }
        return result;
    }

    @Override
    public Status<Show> getShow(String title) {
        Status<Show> result;
        Show show = findShowByTitle(title);
        if (show == null) {
            result = StatusImpl.error(Main.SHOW_FALSE);
        } else {
            result = StatusImpl.successWithData(show);
        }
        return result;
    }

    @Override
    public Status<Void> removeShow(String title) {
        Status<Void> result;
        int indexToRemove = findShowIndex(title);
        if (indexToRemove == -1) {
            result = StatusImpl.error(Main.SHOW_FALSE);
        } else {
            shows.removeAt(indexToRemove);
            result = StatusImpl.success(Main.SHOW_REMOVED);
        }
        return result;
    }
}