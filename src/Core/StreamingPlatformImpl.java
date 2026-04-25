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
    public StatusImpl<String> addPublishableVideo(String videoId, int videoDuration, String url,
                                                  String publisher, String title, String languageCode) {
        if (isLanguageCodeInvalid(languageCode)) {
            return StatusImpl.error(Main.INV_LANGUAGE);
        } else if (isDurationOutOfBounds(videoDuration)) {
            return StatusImpl.error(Main.INV_VALUE);
        } else if (findPublishableVideoById(videoId) != null) {
            return StatusImpl.error(Main.VIDEO_ID_EXISTS);
        } else {
            PublishableVideo video = new PublishableVideoImpl(videoId, videoDuration, url,
                    publisher, title, languageCode);
            videos.insertLast(video);
            // Returning the videoId so Main can format the success message
            return StatusImpl.success(Main.VIDEO_CREATED, videoId);
        }
    }

    @Override
    public StatusImpl<String> addPremiumVideo(String videoID, int videoDuration, String url, String publisher,
                                              String title, String languageCode, String subtitleUrl,
                                              String subtitleLanguageCode) {
        if (isLanguageCodeInvalid(languageCode)) {
            return StatusImpl.error(Main.INV_LANGUAGE);
        } else if (isLanguageCodeInvalid(subtitleLanguageCode)) {
            return StatusImpl.error(Main.INV_SUBTITLE);
        } else if (isDurationOutOfBounds(videoDuration)) {
            return StatusImpl.error(Main.INV_VALUE);
        } else if (findPublishableVideoById(videoID) != null) {
            return StatusImpl.error(Main.VIDEO_ID_EXISTS);
        } else {
            PremiumVideo video = new PremiumVideoImpl(videoID, videoDuration, url, publisher,
                    title, languageCode, subtitleUrl, subtitleLanguageCode);
            videos.insertLast(video);
            return StatusImpl.success(Main.PREMIUM_VIDEO_CREATED, videoID);
        }
    }

    @Override
    public StatusImpl<Void> addSubtitle(String videoID, String subtitleUrl, String subtitleLanguageCode) {
        if (isLanguageCodeInvalid(subtitleLanguageCode)) {
            return StatusImpl.error(Main.INV_SUBTITLE);
        } else if (isVideoUnpublishable(videoID)) {
            return StatusImpl.error(Main.VIDEO_NOT_FOUND);
        } else if (isVideoNotPremium(videoID)) {
            return StatusImpl.error(Main.NOT_PREMIUM);
        } else {
            PremiumVideo premiumVideo = (PremiumVideo) findPublishableVideoById(videoID);
            Subtitle subtitle = new Subtitle(subtitleUrl, subtitleLanguageCode);
            premiumVideo.addSubtitle(subtitle);
            return StatusImpl.success(Main.SUBTITLE_ADDED);
        }
    }

    @Override
    public StatusImpl<PremiumVideo> getSubtitleList(String videoID) {
        if (isVideoNotPremium(videoID)) {
            return StatusImpl.error(Main.NO_PREMIUM_VIDEO);
        } else {
            PremiumVideo premiumVideo = (PremiumVideo) findPublishableVideoById(videoID);
            // Main gets the whole PremiumVideo, so it can call getTitle() and getSubtitles() directly
            return StatusImpl.successWithData(premiumVideo);
        }
    }

    @Override
    public StatusImpl<PublishableVideo> getVideo(String videoId) {
        PublishableVideo video = findPublishableVideoById(videoId);
        if (video == null) {
            return StatusImpl.error(Main.PUBLISHABLE_NOT_FOUND);
        } else {
            return StatusImpl.successWithData(video);
        }
    }

    @Override
    public StatusImpl<Void> removeVideo(String videoID) {
        if (isVideoUnpublishable(videoID)) {
            return StatusImpl.error(Main.VIDEO_NOT_FOUND);
        } else if (isPodcastEpisode(videoID)) {
            return StatusImpl.error(Main.CANT_REMOVE_EPISODE);
        } else if (isVideoUsedInShow(videoID)) {
            return StatusImpl.error(Main.CANT_REMOVE_USED_VIDEO);
        } else {
            int indexToRemove = findVideoIndexToRemove(videoID);
            if (indexToRemove != -1) {
                videos.removeAt(indexToRemove);
                return StatusImpl.success(Main.VIDEO_REMOVED);
            }
            return StatusImpl.error(Main.VIDEO_NOT_FOUND);
        }
    }

    @Override
    public StatusImpl<Void> addPodcast(String title, String author, String languageCode) {
        if (isLanguageCodeInvalid(languageCode)) {
            return StatusImpl.error(Main.INV_LANGUAGE);
        } else if (podcastExists(title)) {
            return StatusImpl.error(Main.PODCAST_EXISTS);
        } else {
            Podcast podcast = new PodcastClassImpl(title, author, languageCode);
            podcasts.insertLast(podcast);
            return StatusImpl.success(Main.PODCAST_CREATED);
        }
    }

    @Override
    public StatusImpl<Void> addPodcastEpisode(String title, String videoId, int videoDuration,
                                              String episodeUrl, String date) {
        Podcast podcast = findPodcastByTitle(title);
        if (isDurationOutOfBounds(videoDuration)) {
            return StatusImpl.error(Main.INV_VALUE);
        } else if (podcast == null) {
            return StatusImpl.error(Main.PODCAST_NOT_FOUND);
        } else if (findPublishableVideoById(videoId) != null || isPodcastEpisode(videoId)) {
            return StatusImpl.error(Main.EPISODE_ID_EXISTS);
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
                return StatusImpl.error(Main.INV_EPISODE_DATE);
            } else {
                PodcastEpisode episode = new PodcastEpisodeImpl(videoId, videoDuration,
                        "Episode " + videoId, episodeUrl, date);
                episodes.insertLast(episode);
                addEpisodeToPodcastMapping(videoId, title);
                return StatusImpl.success(Main.EPISODE_ADDED);
            }
        }
    }

    @Override
    public StatusImpl<Podcast> getPodcast(String title) {
        Podcast podcast = findPodcastByTitle(title);
        if (podcast == null) {
            return StatusImpl.error(Main.PODCAST_NOT_FOUND);
        } else {
            return StatusImpl.successWithData(podcast);
        }
    }

    @Override
    public StatusImpl<Array<PodcastEpisode>> getPodcastEpisodes(String title) {
        Podcast podcast = findPodcastByTitle(title);
        if (podcast == null) {
            return StatusImpl.error(Main.PODCAST_NOT_FOUND);
        } else {
            Array<PodcastEpisode> episodesList = getPodcastEpisodesList(title);
            if (episodesList.size() == 0) {
                return StatusImpl.error(Main.NO_EPISODES);
            } else {
                return StatusImpl.successWithData(episodesList);
            }
        }
    }

    @Override
    public StatusImpl<Array<Podcast>> getAuthorPodcasts(String author) {
        Array<Podcast> authorPodcasts = new ArrayClass<>();
        for (int i = 0; i < podcasts.size(); i++) {
            Podcast podcast = podcasts.get(i);
            if (podcast.author().equalsIgnoreCase(author)) {
                authorPodcasts.insertLast(podcast);
            }
        }

        if (authorPodcasts.size() == 0) {
            return StatusImpl.error(Main.NO_USER_PODCASTS);
        }
        return StatusImpl.successWithData(authorPodcasts);
    }

    @Override
    public StatusImpl<Void> removePodcast(String title) {
        int indexToRemove = findPodcastIndex(title);
        if (indexToRemove == -1) {
            return StatusImpl.error(Main.PODCAST_NOT_FOUND);
        } else {
            removeAllEpisodesFromPodcast(title);
            podcasts.removeAt(indexToRemove);
            return StatusImpl.success(Main.PODCAST_REMOVED);
        }
    }

    @Override
    public StatusImpl<Void> addShow(String author, String videoId, String date) {
        PublishableVideo video = findPublishableVideoById(videoId);
        if (video == null) {
            return StatusImpl.error(Main.SHOW_NOT_FOUND);
        } else if (showExists(video.getTitle())) {
            return StatusImpl.error(Main.SHOW_EXISTS);
        } else {
            Show show = new ShowImpl(author, video.getTitle(), videoId, date);
            shows.insertLast(show);
            return StatusImpl.success(Main.SHOW_CREATED);
        }
    }

    @Override
    public StatusImpl<Show> getShow(String title) {
        Show show = findShowByTitle(title);
        if (show == null) {
            return StatusImpl.error(Main.SHOW_FALSE);
        } else {
            return StatusImpl.successWithData(show);
        }
    }

    @Override
    public StatusImpl<Void> removeShow(String title) {
        int indexToRemove = findShowIndex(title);
        if (indexToRemove == -1) {
            return StatusImpl.error(Main.SHOW_FALSE);
        } else {
            shows.removeAt(indexToRemove);
            return StatusImpl.success(Main.SHOW_REMOVED);
        }
    }
}