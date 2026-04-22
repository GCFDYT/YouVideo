package Core;

import dataStructures.Array;
import dataStructures.ArrayClass;

public class StreamingPlatformClass implements StreamingPlatform{

    private Array videos;
    private Array podcasts;
    private Array shows;

    public StreamingPlatformClass() {
        videos = new ArrayClass();
        podcasts = new ArrayClass();
        shows = new ArrayClass();
    }

    public void addPublishableVideo(String videoId, int videoDuration, String url, String publisher, String title, String language) {

    }

    @Override
    public void addPremiumVideo(String videoId, int videoDuration, String url, String publisher, String title, String language, String subtitleUrl, String subtitleLanguage) {

    }

    @Override
    public void addSubtitle(String videoId, String subtitleUrl, String subtitleLanguage) {

    }

    @Override
    public void getSubtitleList(String videoId) {

    }

    @Override
    public void getVideo(String videoId) {

    }

    @Override
    public void addPodcast(String title, String author, String language) {

    }

    @Override
    public void addEpisode(String title, String videoId, int videoDuration, String episodeUrl, String date) {

    }

    @Override
    public void getPodcast(String title) {

    }

    @Override
    public void getEpisodes(String title) {

    }

    @Override
    public void getAuthorPodcasts(String author) {

    }

    @Override
    public void removePodcast(String title) {

    }

    @Override
    public void createShow(String author, String videoId, String date) {

    }

    @Override
    public void getShow(String title) {

    }

    @Override
    public void removeShow(String title) {

    }

    @Override
    public void removeVideo(String videoId) {

    }
}
