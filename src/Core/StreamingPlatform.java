package Core;

public interface StreamingPlatform {

    void addPublishableVideo(String id, int duration, String url, String publisher, String title, String language);

    void addPremiumVideo(String id, int duration, String url, String publisher, String title, String language, String subtitleUrl, String subtitleLanguage);

    void addSubtitle(String id, String subtitleUrl, String subtitleLanguage);


}
