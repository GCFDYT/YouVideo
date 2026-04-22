package Core;

public record Subtitle(String subtitleUrl, String subtitleLanguage) {

    public String getSubtitleUrl() {
        return subtitleUrl;
    }

    public String getSubtitleLanguage() {
        return subtitleLanguage;
    }

}
