package Core;

public interface StreamingPlatform {

    void addPublishableVideo(String videoId, int videoDuration, String url, String publisher, String title, String language);

    void addPremiumVideo(String videoId, int videoDuration, String url, String publisher, String title, String language, String subtitleUrl, String subtitleLanguage);

    void addSubtitle(String videoId, String subtitleUrl, String subtitleLanguage);


}
