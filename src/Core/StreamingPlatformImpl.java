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

    private boolean isVideoUsedInShow(String videoId) {
        boolean used = false;
        for (int i = 0; i < shows.size() && !used; i++) {
            Show show = shows.get(i);
            if (show.videoID().equals(videoId)) {
                used = true;
            }
        }
        return used;
    }

    private int findVideoIndexToRemove(String videoId) {
        int index = -1;
        for (int i = 0; i < videos.size() && index == -1; i++) {
            PublishableVideo video = videos.get(i);
            if (video.getVideoID().equals(videoId)) {
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
            if (languages[i].equals(languageCode)) {
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
    public void addPublishableVideo(String videoId, int videoDuration, String url,
                                    String publisher, String title, String languageCode) {
        if (isLanguageCodeInvalid(languageCode)) {
            System.out.println(Main.INV_LANGUAGE);
        } else if (isDurationOutOfBounds(videoDuration)) {
            System.out.println(Main.INV_VALUE);
        } else if (findPublishableVideoById(videoId) != null) {
            System.out.println(Main.VIDEO_ID_EXISTS);
        } else {
            PublishableVideo video = new PublishableVideoImpl(videoId, videoDuration, url,
                    publisher, title, languageCode);
            videos.insertLast(video);
            System.out.printf(Main.VIDEO_CREATED + "%n", videoId);
        }
    }

    @Override
    public void addPremiumVideo(String videoId, int videoDuration, String url, String publisher,
                                String title, String languageCode, String subtitleUrl,
                                String subtitleLanguageCode) {
        if (isLanguageCodeInvalid(languageCode)) {
            System.out.println(Main.INV_LANGUAGE);
        } else if (isLanguageCodeInvalid(subtitleLanguageCode)) {
            System.out.println(Main.INV_SUBTITLE);
        } else if (isDurationOutOfBounds(videoDuration)) {
            System.out.println(Main.INV_VALUE);
        } else if (findPublishableVideoById(videoId) != null) {
            System.out.println(Main.VIDEO_ID_EXISTS);
        } else {
            PremiumVideo video = new PremiumVideoImpl(videoId, videoDuration, url, publisher,
                    title, languageCode, subtitleUrl, subtitleLanguageCode);
            videos.insertLast(video);
            System.out.printf(Main.PREMIUM_VIDEO_CREATED + "%n", videoId);
        }
    }

    @Override
    public void addSubtitle(String videoID, String subtitleUrl, String subtitleLanguageCode) {
        if (isLanguageCodeInvalid(subtitleLanguageCode)) {
            System.out.println(Main.INV_SUBTITLE);
        } else if (isVideoUnpublishable(videoID)) {
            System.out.println(Main.VIDEO_NOT_FOUND);
        } else if (isVideoNotPremium(videoID)) {
            System.out.println(Main.NOT_PREMIUM);
        } else {
            PremiumVideo premiumVideo = (PremiumVideo) findPublishableVideoById(videoID);
            Subtitle subtitle = new Subtitle(subtitleUrl, subtitleLanguageCode);
            premiumVideo.addSubtitle(subtitle);
            System.out.println(Main.SUBTITLE_ADDED);
        }
    }

    @Override
    public void getSubtitleList(String videoId) {
        if (isVideoNotPremium(videoId)) {
            System.out.println(Main.NO_PREMIUM_VIDEO);
        } else {
            PremiumVideo premiumVideo = (PremiumVideo) findPublishableVideoById(videoId);
            Array subtitles = premiumVideo.getSubtitles();
            System.out.printf(Main.SUBTITLES_HEADER + "%n", premiumVideo.getTitle());
            for (int i = 0; i < subtitles.size(); i++) {
                Subtitle subtitle = (Subtitle) subtitles.get(i);
                Locale locale = Locale.of(subtitle.getSubtitleLanguage());
                System.out.printf(Main.SUBTITLE_ENTRY + "%n",
                        subtitle.getSubtitleUrl(),
                        locale.getDisplayLanguage().toUpperCase());
            }
        }
    }

    @Override
    public void getVideo(String videoId) {
        PublishableVideo video = findPublishableVideoById(videoId);
        if (video == null) {
            System.out.printf(Main.PUBLISHABLE_NOT_FOUND + "%n", videoId);
        } else {
            Locale locale = Locale.of(video.getLanguage());
            if (video instanceof PremiumVideo) {
                System.out.printf("PREMIUM Video %s %d Title: %s%n",
                        video.getVideoID(), video.getVideoDuration(), video.getTitle());
                System.out.printf("File: %s Publisher: %s Language: %s%n",
                        video.getUrl(), video.getPublisher(),
                        locale.getDisplayLanguage().toUpperCase());
            } else {
                System.out.printf("Video %s %d Title: %s%n",
                        video.getVideoID(), video.getVideoDuration(), video.getTitle());
                System.out.printf("File: %s Publisher: %s Language: %s%n",
                        video.getUrl(), video.getPublisher(),
                        locale.getDisplayLanguage().toUpperCase());
            }
        }
    }

    @Override
    public void removeVideo(String videoID) {
        if (isVideoUnpublishable(videoID)) {
            System.out.println(Main.VIDEO_NOT_FOUND);
        } else if (isPodcastEpisode(videoID)) {
            System.out.println(Main.CANT_REMOVE_EPISODE);
        } else if (isVideoUsedInShow(videoID)) {
            System.out.println(Main.CANT_REMOVE_USED_VIDEO);
        } else {
            int indexToRemove = findVideoIndexToRemove(videoID);
            if (indexToRemove != -1) {
                videos.removeAt(indexToRemove);
                System.out.println(Main.VIDEO_REMOVED);
            }
        }
    }

    @Override
    public void addPodcast(String title, String author, String languageCode) {
        if (isLanguageCodeInvalid(languageCode)) {
            System.out.println(Main.INV_LANGUAGE);
        } else if (podcastExists(title)) {
            System.out.println(Main.PODCAST_EXISTS);
        } else {
            Podcast podcast = new PodcastClassImpl(title, author, languageCode);
            podcasts.insertLast(podcast);
            System.out.println(Main.PODCAST_CREATED);
        }
    }

    @Override
    public void addPodcastEpisode(String title, String videoID, int videoDuration,
                                  String episodeUrl, String date) {
        Podcast podcast = findPodcastByTitle(title);
        if (isDurationOutOfBounds(videoDuration)) {
            System.out.println(Main.INV_VALUE);
        } else if (podcast == null) {
            System.out.println(Main.PODCAST_NOT_FOUND);
        } else if (findPublishableVideoById(videoID) != null || isPodcastEpisode(videoID)) {
            System.out.println(Main.EPISODE_ID_EXISTS);
        } else {
            Array<PodcastEpisode> existingEpisodes = getPodcastEpisodesList(title);
            boolean dateValid = true;
            if (existingEpisodes.size() > 0) {
                PodcastEpisode latest = existingEpisodes.get(0);
                if (date.compareTo(latest.getDate()) < 0) {
                    dateValid = false;
                }
            } if (!dateValid) {
                System.out.println(Main.INV_EPISODE_DATE);
            } else {
                PodcastEpisode episode = new PodcastEpisodeImpl(videoID, videoDuration,
                        "Episode " + videoID, episodeUrl, date);
                episodes.insertLast(episode);
                addEpisodeToPodcastMapping(videoID, title);
                System.out.println(Main.EPISODE_ADDED);
            }
        }
    }

    @Override
    public void getPodcast(String title) {
        Podcast podcast = findPodcastByTitle(title);
        if (podcast == null) {
            System.out.println(Main.PODCAST_NOT_FOUND);
        } else {
            Locale locale = Locale.of(podcast.languageCode());
            System.out.printf(Main.PODCAST_INFO + "%n",
                    podcast.title(), podcast.author(),
                    locale.getDisplayLanguage().toUpperCase());
            Array<PodcastEpisode> episodesList = getPodcastEpisodesList(title);
            if (episodesList.size() > 0) {
                PodcastEpisode latest = episodesList.get(0);
                System.out.printf(Main.PODCAST_LATEST_EPISODE + "%n", latest.getDate());
            }
        }
    }

    @Override
    public void getPodcastEpisodes(String title) {
        Podcast podcast = findPodcastByTitle(title);
        if (podcast == null) {
            System.out.println(Main.PODCAST_NOT_FOUND);
        } else {
            Array<PodcastEpisode> episodesList = getPodcastEpisodesList(title);
            if (episodesList.size() == 0) {
                System.out.println(Main.NO_EPISODES);
            } else {
                System.out.printf(Main.EPISODES_HEADER + "%n", title);
                for (int i = 0; i < episodesList.size(); i++) {
                    PodcastEpisode episode = episodesList.get(i);
                    System.out.printf(Main.EPISODE_ENTRY + "%n",
                            episode.getVideoID(), episode.getVideoDuration(),
                            episode.getDate());
                    System.out.printf(Main.EPISODE_URL + "%n", episode.getUrl());
                }
            }
        }
    }

    @Override
    public void getAuthorPodcasts(String author) {
        boolean found = false;
        System.out.printf(Main.AUTHOR_PODCASTS_HEADER + "%n", author);
        for (int i = 0; i < podcasts.size(); i++) {
            Podcast podcast = podcasts.get(i);
            if (podcast.author().equalsIgnoreCase(author)) {
                Locale locale = Locale.of(podcast.languageCode());
                System.out.printf(Main.PODCAST_ENTRY + "%n",
                        podcast.title(), podcast.author(),
                        locale.getDisplayLanguage().toUpperCase());
                found = true;
            }
        }
        if (!found) {
            System.out.println(Main.NO_USER_PODCASTS);
        }
    }

    @Override
    public void removePodcast(String title) {
        int indexToRemove = findPodcastIndex(title);
        if (indexToRemove == -1) {
            System.out.println(Main.PODCAST_NOT_FOUND);
        } else {
            removeAllEpisodesFromPodcast(title);
            podcasts.removeAt(indexToRemove);
            System.out.println(Main.PODCAST_REMOVED);
        }
    }

    @Override
    public void addShow(String author, String videoId, String date) {
        PublishableVideo video = findPublishableVideoById(videoId);
        if (video == null) {
            System.out.println(Main.SHOW_NOT_FOUND);
        } else if (showExists(video.getTitle())) {
            System.out.println(Main.SHOW_EXISTS);
        } else {
            Show show = new ShowImpl(author, video.getTitle(), videoId, date);
            shows.insertLast(show);
            System.out.println(Main.SHOW_CREATED);
        }
    }

    @Override
    public void getShow(String title) {
        Show show = findShowByTitle(title);
        if (show == null) {
            System.out.println(Main.SHOW_FALSE);
        } else {
            System.out.printf(Main.SHOW_INFO + "%n", show.date(), show.author());
            System.out.printf(Main.SHOW_VIDEO + "%n", show.videoID());
        }
    }

    @Override
    public void removeShow(String title) {
        int indexToRemove = findShowIndex(title);
        if (indexToRemove == -1) {
            System.out.println(Main.SHOW_FALSE);
        } else {
            shows.removeAt(indexToRemove);
            System.out.println(Main.SHOW_REMOVED);
        }
    }
}