package Core;

public interface StreamingPlatform {

    void addPublishableVideo(String videoId, int videoDuration, String url, String publisher, String title, String language);

    void addPremiumVideo(String videoId, int videoDuration, String url, String publisher, String title, String language, String subtitleUrl, String subtitleLanguage);

    void addSubtitle(String videoId, String subtitleUrl, String subtitleLanguage);

    void getSubtitleList(String videoId);

    void getVideo(String videoId);

    void addPodcast(String title, String author, String language);

    void addPodcastEpisode(String title, String videoId, int videoDuration, String url, String date);

    void getPodcast(String title);

    void getPodcastEpisodes(String title);

    void getAuthorPodcasts(String author);

    void removePodcast(String title);

    void addShow(String author, String videoId, String date);

    void getShow(String title);

    void removeShow(String title);

    void removeVideo(String videoId);
}
