package Core;

public class PremiumVideoClass extends PublishableVideoClass implements PremiumVideo{

    private final Subtitle subtitle;

    public PremiumVideoClass(String videoId, int videoDuration, String url, String publisher,
                             String title, String language, String subtitleUrl, Subtitle subtitle) {

        super(videoId,videoDuration, url, publisher, title, language);

        this.subtitle = subtitle;
    }

    @Override
    public Subtitle getSubtitle() {
        return subtitle;
    }
}
