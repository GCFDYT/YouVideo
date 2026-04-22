package Core;

import dataStructures.Array;
import dataStructures.ArrayClass;

public class StreamingPlatformClass implements StreamingPlatform{

    private Array videos;
    private Array podcasts;
    private Array shows;
    private Array authors;

    public StreamingPlatformClass() {
        videos = new ArrayClass();
        podcasts = new ArrayClass();
        shows = new ArrayClass();
        authors = new ArrayClass();
    }

    public void addPublishableVideo(String videoId, int videoDuration, String url, String publisher, String title, String language) {

    }

    @Override
    public void addPremiumVideo(String videoId, int videoDuration, String url, String publisher, String title, String language, String subtitleUrl, String subtitleLanguage) {

    }

    @Override
    public void addSubtitle(String videoId, String subtitleUrl, String subtitleLanguage) {

    }
}
