package Core;

public interface StreamingPlatform {

    void addPublishableVideo(String videoID, int videoDuration, String url, String publisher,
                             String title, String languageCode);

    void addPremiumVideo(String videoID, int videoDuration, String url, String publisher,
                         String title, String languageCode, String subtitleUrl,
                         String subtitleLanguageCode);

    void addSubtitle(String videoID, String subtitleUrl, String subtitleLanguageCode);

    void getSubtitleList(String videoID);

    void getVideo(String videoID);

    void addPodcast(String title, String author, String languageCode);

    void addPodcastEpisode(String title, String videoID, int videoDuration, String url, String date);

    void getPodcast(String title);

    void getPodcastEpisodes(String title);

    void getAuthorPodcasts(String author);

    void removePodcast(String title);

    void addShow(String author, String videoID, String date);

    void getShow(String title);

    void removeShow(String title);

    void removeVideo(String videoID);
}
