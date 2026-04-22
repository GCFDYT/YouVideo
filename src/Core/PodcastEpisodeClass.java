package Core;

public class PodcastEpisodeClass extends AbstractVideoClass implements PodcastEpisode {

    private final String title;
    private final String url;
    private final String date;


    private PodcastEpisodeClass(String videoId, int videoDuration, String title, String url, String date) {
        super(videoId, videoDuration);

        this.title = title;
        this.url = url;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getDate() {
        return date;
    }
}
