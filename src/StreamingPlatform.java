import java.util.Iterator;

/**
 * Main interface for the You Video streaming platform, managing videos, podcasts, and shows.
 * @author Gonçalo Domingos and João Domingues
 */
public interface StreamingPlatform {

    // --- Pre-condition Checks ---
    boolean hasVideo(String videoID);
    boolean hasPublishableVideo(String videoID);
    boolean hasPodcastEpisode(String videoID);
    boolean isPremiumVideo(String videoID);
    boolean hasPodcast(String title);
    boolean hasShow(String title);
    boolean isValidLanguageCode(String code);
    boolean isVideoUsedInShow(String videoID);
    boolean isValidEpisodeDate(String podcastTitle, String date);
    boolean hasAuthorPodcasts(String author);
    
    // Phase 2 Pre-conditions
    boolean hasProductiveAuthors();
    boolean isTitleTaggedWith(String title, String tag);

    // --- Commands ---
    void addPublishableVideo(String videoID, int videoDuration, String url,
                             String publisher, String title, String languageCode);

    void addPremiumVideo(String videoID, int videoDuration, String url, String publisher,
                         String title, String languageCode, String subtitleUrl,
                         String subtitleLanguageCode);

    void addSubtitle(String videoID, String subtitleUrl, String subtitleLanguageCode);
    void removeVideo(String videoID);

    void addPodcast(String title, String author, String languageCode);
    void addPodcastEpisode(String title, String videoID, int videoDuration,
                           String episodeUrl, String date, String episodeTitle);
    void removePodcast(String title);

    void addShow(String author, String videoID, String date);
    void removeShow(String title);
    
    // Phase 2 Commands
    void addTagToTitle(String title, String tag);
    void removeTagFromTitle(String title, String tag);

    // --- Queries ---
    PublishableVideo getVideo(String videoID);
    Iterator<SubtitleImpl> getSubtitles(String videoID);
    Podcast getPodcast(String title);
    Iterator<PodcastEpisode> getPodcastEpisodes(String title);
    Iterator<Podcast> getAuthorPodcasts(String author);
    Show getShow(String title);
    
    // Phase 2 Queries
    Iterator<Author> getAuthorsByProductivity();
    Iterator<TaggableContent> getTaggedContent(String tag, String filter, String order);
}