package Core;

public class PodcastEpisodeImpl extends AbstractVideoImpl implements PodcastEpisode {

    private final String url;
    private final String date;

    public PodcastEpisodeImpl(String videoId, int videoDuration, String title, String url, String date) {
        super(videoId, title, videoDuration);
        this.url = url;
        this.date = date;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getDate() {
        return date;
    }
}