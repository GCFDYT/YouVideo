import java.util.Iterator;

/**
 * Main interface for the You Video streaming platform, managing videos, podcasts, and shows.
 * * @author Gonçalo Domingos and João Domingues
 */
public interface StreamingPlatform {

    /**
     * Checks if a video exists with the given ID.
     * @param videoID - the ID of the video to check.
     * @return returns true if the video exists, false otherwise.
     * @pre videoID != null
     */
    boolean hasVideo(String videoID);

    /**
     * Checks if a publishable video exists with the given ID.
     * @param videoID - the ID of the video to check.
     * @return returns true if the publishable video exists, false otherwise.
     * @pre videoID != null
     */
    boolean hasPublishableVideo(String videoID);

    /**
     * Checks if a video ID belongs to a podcast episode.
     * @param videoID - the ID of the video to check.
     * @return returns true if the video is a podcast episode, false otherwise.
     * @pre videoID != null
     */
    boolean hasPodcastEpisode(String videoID);

    /**
     * Checks if a video is a premium video.
     * @param videoID - the ID of the video to check.
     * @return returns true if the video is premium, false otherwise.
     * @pre videoID != null
     */
    boolean isPremiumVideo(String videoID);

    /**
     * Checks if a podcast exists with the given title.
     * @param title - the title of the podcast.
     * @return returns true if the podcast exists, false otherwise.
     * @pre title != null
     */
    boolean hasPodcast(String title);

    /**
     * Checks if a show exists with the given title.
     * @param title - the title of the show.
     * @return returns true if the show exists, false otherwise.
     * @pre title != null
     */
    boolean hasShow(String title);

    /**
     * Checks if the provided language code is valid within the platform.
     * @param code - the language code to validate.
     * @return returns true if valid, false otherwise.
     * @pre code != null
     */
    boolean isValidLanguageCode(String code);

    /**
     * Checks if a video is currently being used in any show.
     * @param videoID - the ID of the video.
     * @return returns true if used in a show, false otherwise.
     * @pre videoID != null
     */
    boolean isVideoUsedInShow(String videoID);

    /**
     * Checks if the episode date is valid (greater than or equal to the latest episode date).
     * @param podcastTitle - the title of the podcast.
     * @param date - the date to check.
     * @return returns true if valid, false otherwise.
     * @pre podcastTitle != null && date != null
     */
    boolean isValidEpisodeDate(String podcastTitle, String date);

    /**
     * Checks if an author has any podcasts.
     * @param author - the name of the author.
     * @return returns true if the author has podcasts, false otherwise.
     * @pre author != null
     */
    boolean hasAuthorPodcasts(String author);

    /**
     * Checks if there are any authors with content contributions.
     * @return returns true if there is at least one productive author, false otherwise.
     */
    boolean hasProductiveAuthors();

    /**
     * Checks if a specific title (show or podcast) is tagged with a given tag.
     * @param title - the title of the content.
     * @param tag - the tag to check.
     * @return returns true if the title has the tag, false otherwise.
     * @pre title != null && tag != null
     */
    boolean isTitleTaggedWith(String title, String tag);

    /**
     * Adds a new standard publishable video to the platform.
     * @param videoID - the unique identifier for the video.
     * @param videoDuration - the duration of the video.
     * @param url - the video URL.
     * @param publisher - the publisher of the video.
     * @param title - the title of the video.
     * @param languageCode - the language code of the video.
     * @pre videoID != null && !hasVideo(videoID)
     * @pre isValidLanguageCode(languageCode)
     */
    void addPublishableVideo(String videoID, int videoDuration, String url,
                             String publisher, String title, String languageCode);

    /**
     * Adds a new premium video to the platform.
     * @param videoID - the unique identifier for the video.
     * @param videoDuration - the duration of the video.
     * @param url - the video URL.
     * @param publisher - the publisher of the video.
     * @param title - the title of the video.
     * @param languageCode - the primary language code.
     * @param subtitleUrl - the URL for the subtitle file.
     * @param subtitleLanguageCode - the language code for the subtitle.
     * @pre videoID != null && !hasVideo(videoID)
     * @pre isValidLanguageCode(languageCode) && isValidLanguageCode(subtitleLanguageCode)
     */
    void addPremiumVideo(String videoID, int videoDuration, String url, String publisher,
                         String title, String languageCode, String subtitleUrl,
                         String subtitleLanguageCode);

    /**
     * Adds a subtitle to an existing premium video.
     * @param videoID - the ID of the premium video.
     * @param subtitleUrl - the URL of the subtitle.
     * @param subtitleLanguageCode - the language code of the subtitle.
     * @pre videoID != null && isPremiumVideo(videoID)
     * @pre isValidLanguageCode(subtitleLanguageCode)
     */
    void addSubtitle(String videoID, String subtitleUrl, String subtitleLanguageCode);

    /**
     * Removes a video from the platform.
     * @param videoID - the ID of the video to remove.
     * @pre videoID != null && hasVideo(videoID)
     * @pre !hasPodcastEpisode(videoID) && !isVideoUsedInShow(videoID)
     */
    void removeVideo(String videoID);

    /**
     * Creates a new podcast.
     * @param title - the title of the podcast.
     * @param author - the author of the podcast.
     * @param languageCode - the language code of the podcast.
     * @pre title != null && !hasPodcast(title)
     * @pre isValidLanguageCode(languageCode)
     */
    void addPodcast(String title, String author, String languageCode);

    /**
     * Adds an episode to an existing podcast.
     * @param title - the title of the podcast.
     * @param videoID - the unique video ID for the episode.
     * @param videoDuration - the duration of the episode.
     * @param episodeUrl - the URL of the episode.
     * @param date - the release date of the episode.
     * @param episodeTitle - the specific title of the episode.
     * @pre title != null && hasPodcast(title)
     * @pre videoID != null && !hasVideo(videoID)
     * @pre isValidEpisodeDate(title, date)
     */
    void addPodcastEpisode(String title, String videoID, int videoDuration,
                           String episodeUrl, String date, String episodeTitle);

    /**
     * Removes a podcast and all its episodes from the platform.
     * @param title - the title of the podcast to remove.
     * @pre title != null && hasPodcast(title)
     */
    void removePodcast(String title);

    /**
     * Creates a new show referencing an existing video.
     * @param author - the author of the show.
     * @param videoID - the ID of the video to be used.
     * @param date - the release date of the show.
     * @pre author != null && videoID != null && hasPublishableVideo(videoID)
     * @pre !hasShow(getVideo(videoID).getTitle())
     */
    void addShow(String author, String videoID, String date);

    /**
     * Removes a show from the platform.
     * @param title - the title of the show to remove.
     * @pre title != null && hasShow(title)
     */
    void removeShow(String title);

    /**
     * Assigns a tag to a specified title (podcast or show).
     * @param title - the title of the content.
     * @param tag - the tag to add.
     * @pre title != null && (hasPodcast(title) || hasShow(title))
     * @pre !isTitleTaggedWith(title, tag)
     */
    void addTagToTitle(String title, String tag);

    /**
     * Removes an assigned tag from a specified title (podcast or show).
     * @param title - the title of the content.
     * @param tag - the tag to remove.
     * @pre title != null && (hasPodcast(title) || hasShow(title))
     * @pre isTitleTaggedWith(title, tag)
     */
    void removeTagFromTitle(String title, String tag);

    /**
     * Returns a publishable video by its ID.
     * @param videoID - the ID of the video.
     * @return returns the <code>PublishableVideo</code> object.
     * @pre videoID != null && hasPublishableVideo(videoID)
     */
    PublishableVideo getVideo(String videoID);

    /**
     * Returns an iterator over the subtitles of a premium video.
     * @param videoID - the ID of the video.
     * @return returns an <code>Iterator</code> of subtitles.
     * @pre videoID != null && isPremiumVideo(videoID)
     */
    Iterator<SubtitleImpl> getSubtitles(String videoID);

    /**
     * Returns a podcast by its title.
     * @param title - the title of the podcast.
     * @return returns the <code>Podcast</code> object.
     * @pre title != null && hasPodcast(title)
     */
    Podcast getPodcast(String title);

    /**
     * Returns an iterator over the episodes of a specific podcast.
     * @param title - the title of the podcast.
     * @return returns an <code>Iterator</code> of <code>PodcastEpisode</code> objects.
     * @pre title != null && hasPodcast(title)
     */
    Iterator<PodcastEpisode> getPodcastEpisodes(String title);

    /**
     * Returns an iterator over all podcasts created by a specific author.
     * @param author - the name of the author.
     * @return returns an <code>Iterator</code> of <code>Podcast</code> objects.
     * @pre author != null && hasAuthorPodcasts(author)
     */
    Iterator<Podcast> getAuthorPodcasts(String author);

    /**
     * Returns a show by its title.
     * @param title - the title of the show.
     * @return returns the <code>Show</code> object.
     * @pre title != null && hasShow(title)
     */
    Show getShow(String title);

    /**
     * Returns an iterator over authors ordered by their productivity (number of contributions).
     * @return returns an <code>Iterator</code> of <code>Author</code> objects.
     * @pre hasProductiveAuthors()
     */
    Iterator<Author> getAuthorsByProductivity();

    /**
     * Returns an iterator over content matching a specific tag, filtered and ordered.
     * @param tag - the tag to search for.
     * @param filter - the filter type (e.g., ALL, SHOW, PODCAST).
     * @param order - the sort order (e.g., ASC, DES).
     * @return returns an <code>Iterator</code> of <code>TaggableContent</code>.
     * @pre tag != null && filter != null && order != null
     */
    Iterator<TaggableContent> getTaggedContent(String tag, String filter, String order);
}