package Core;

import dataStructures.Iterator;

/**
 * Main interface for the You Video streaming platform, managing videos, podcasts, and shows.
 * @author Gonçalo Domingos and João Domingues
 */
public interface StreamingPlatform {

    // --- Pre-condition Checks ---

    /**
     * Checks if a video (Publishable or Episode) with <code>videoID</code> exists in the system.
     * @param videoID - the identifier of the video.
     * @return returns <code>true</code> if the video exists in the system.
     */
    boolean hasVideo(String videoID);

    /**
     * Checks if a Publishable Video with <code>videoID</code> exists.
     * @param videoID - the identifier of the video.
     * @return returns <code>true</code> if the publishable video exists.
     */
    boolean hasPublishableVideo(String videoID);

    /**
     * Checks if a video with <code>videoID</code> is a Podcast Episode.
     * @param videoID - the identifier of the video.
     * @return returns <code>true</code> if the video is an episode.
     */
    boolean hasPodcastEpisode(String videoID);

    /**
     * Checks if a Publishable Video with <code>videoID</code> is a Premium Video.
     * @param videoID - the identifier of the video.
     * @return returns <code>true</code> if the video is premium.
     */
    boolean isPremiumVideo(String videoID);

    /**
     * Checks if a podcast with the given <code>title</code> exists.
     * @param title - the title of the podcast.
     * @return returns <code>true</code> if the podcast exists.
     */
    boolean hasPodcast(String title);

    /**
     * Checks if a show with the given <code>title</code> exists.
     * @param title - the title of the show.
     * @return returns <code>true</code> if the show exists.
     */
    boolean hasShow(String title);

    /**
     * Checks if the <code>code</code> is a valid ISO 639-1 language code.
     * @param code - the language code.
     * @return returns <code>true</code> if the code is valid.
     */
    boolean isValidLanguageCode(String code);

    /**
     * Checks if a video with <code>videoID</code> is currently being used in a show.
     * @param videoID - the identifier of the video.
     * @return returns <code>true</code> if the video is used in a show.
     */
    boolean isVideoUsedInShow(String videoID);

    /**
     * Checks if a new episode <code>date</code> is greater than or equal to the latest episode date of the podcast.
     * @pre hasPodcast(podcastTitle)
     * @param podcastTitle - the title of the podcast.
     * @param date - the release date of the new episode.
     * @return returns <code>true</code> if the date is valid.
     */
    boolean isValidEpisodeDate(String podcastTitle, String date);

    /**
     * Checks if the <code>author</code> has any podcasts in the system.
     * @param author - the name of the author.
     * @return returns <code>true</code> if the author has at least one podcast.
     */
    boolean hasAuthorPodcasts(String author);

    // --- Commands ---

    /**
     * Adds a new publishable video to the system.
     * @pre !hasVideo(videoID) && isValidLanguageCode(languageCode) && videoDuration > 0
     * @param videoID - the unique identifier of the video.
     * @param videoDuration - the duration of the video in minutes.
     * @param url - the file URL of the video.
     * @param publisher - the name of the publisher.
     * @param title - the title of the video.
     * @param languageCode - the language code of the video.
     */
    void addPublishableVideo(String videoID, int videoDuration, String url,
                             String publisher, String title, String languageCode);

    /**
     * Adds a new premium video to the system.
     * @pre !hasVideo(videoID) && isValidLanguageCode(languageCode) && isValidLanguageCode(subtitleLanguageCode) && videoDuration > 0
     * @param videoID - the unique identifier of the video.
     * @param videoDuration - the duration of the video in minutes.
     * @param url - the file URL of the video.
     * @param publisher - the name of the publisher.
     * @param title - the title of the video.
     * @param languageCode - the primary language code of the video.
     * @param subtitleUrl - the URL of the first subtitle file.
     * @param subtitleLanguageCode - the language code of the subtitle.
     */
    void addPremiumVideo(String videoID, int videoDuration, String url, String publisher,
                         String title, String languageCode, String subtitleUrl,
                         String subtitleLanguageCode);

    /**
     * Adds a subtitle to an existing premium video.
     * @pre isPremiumVideo(videoID) && isValidLanguageCode(subtitleLanguageCode)
     * @param videoID - the unique identifier of the premium video.
     * @param subtitleUrl - the URL of the subtitle file.
     * @param subtitleLanguageCode - the language code of the subtitle.
     */
    void addSubtitle(String videoID, String subtitleUrl, String subtitleLanguageCode);

    /**
     * Removes a publishable video from the system by its <code>videoID</code>.
     * @pre hasPublishableVideo(videoID) && !hasPodcastEpisode(videoID) && !isVideoUsedInShow(videoID)
     * @param videoID - the unique identifier of the video to remove.
     */
    void removeVideo(String videoID);

    /**
     * Creates a new podcast with no episodes.
     * @pre !hasPodcast(title) && isValidLanguageCode(languageCode)
     * @param title - the unique title of the podcast.
     * @param author - the author of the podcast.
     * @param languageCode - the language code of the podcast.
     */
    void addPodcast(String title, String author, String languageCode);

    /**
     * Adds a new episode to an existing podcast.
     * @pre hasPodcast(title) && !hasVideo(videoID) && videoDuration > 0 && isValidEpisodeDate(title, date)
     * @param title - the title of the podcast.
     * @param videoID - the unique identifier of the episode.
     * @param videoDuration - the duration of the episode in minutes.
     * @param episodeUrl - the file URL of the episode.
     * @param date - the release date of the episode.
     * @param episodeTitle - the title of the episode.
     */
    void addPodcastEpisode(String title, String videoID, int videoDuration,
                           String episodeUrl, String date, String episodeTitle);

    /**
     * Removes a podcast and all its associated episodes from the system.
     * @pre hasPodcast(title)
     * @param title - the title of the podcast to remove.
     */
    void removePodcast(String title);

    /**
     * Creates a show using an existing publishable video.
     * @pre hasPublishableVideo(videoID) && !hasShow(videoTitle)
     * @param author - the author of the show.
     * @param videoID - the unique identifier of the video being broadcasted.
     * @param date - the transmission date of the show.
     */
    void addShow(String author, String videoID, String date);

    /**
     * Removes a show from the system without affecting the underlying video.
     * @pre hasShow(title)
     * @param title - the title of the show to remove.
     */
    void removeShow(String title);

    // --- Queries ---

    /**
     * Returns a publishable video.
     * @pre hasPublishableVideo(videoID)
     * @param videoID - the unique identifier of the video.
     * @return returns the <code>PublishableVideo</code> object.
     */
    PublishableVideo getVideo(String videoID);

    /**
     * Returns an iterator with the subtitles of a premium video.
     * @pre isPremiumVideo(videoID)
     * @param videoID - the unique identifier of the premium video.
     * @return returns an <code>Iterator</code> of subtitles.
     */
    Iterator<SubtitleImpl> getSubtitles(String videoID);

    /**
     * Returns a podcast.
     * @pre hasPodcast(title)
     * @param title - the title of the podcast.
     * @return returns the <code>Podcast</code> object.
     */
    Podcast getPodcast(String title);

    /**
     * Returns an iterator with all episodes of a podcast in reverse chronological order.
     * @pre hasPodcast(title)
     * @param title - the title of the podcast.
     * @return returns an <code>Iterator</code> of podcast episodes.
     */
    Iterator<PodcastEpisode> getPodcastEpisodes(String title);

    /**
     * Returns an iterator with all podcasts created by a specific author.
     * @pre hasAuthorPodcasts(author)
     * @param author - the name of the author.
     * @return returns an <code>Iterator</code> of podcasts.
     */
    Iterator<Podcast> getAuthorPodcasts(String author);

    /**
     * Returns a show.
     * @pre hasShow(title)
     * @param title - the title of the show.
     * @return returns the <code>Show</code> object.
     */
    Show getShow(String title);
}