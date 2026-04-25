package Core;

import dataStructures.Array;

public interface StreamingPlatform {

    Status<String> addPublishableVideo(String videoID, int videoDuration, String url,
                                       String publisher, String title, String languageCode);

    Status<String> addPremiumVideo(String videoID, int videoDuration, String url, String publisher,
                                   String title, String languageCode, String subtitleUrl,
                                   String subtitleLanguageCode);

    Status<Void> addSubtitle(String videoID, String subtitleUrl, String subtitleLanguageCode);

    Status<PremiumVideo> getSubtitleList(String videoID);

    Status<PublishableVideo> getVideo(String videoID);

    Status<Void> removeVideo(String videoID);

    Status<Void> addPodcast(String title, String author, String languageCode);

    Status<Void> addPodcastEpisode(String title, String videoID, int videoDuration,
                                   String episodeUrl, String date);

    Status<Podcast> getPodcast(String title);

    Status<Array<PodcastEpisode>> getPodcastEpisodes(String title);

    Status<Array<Podcast>> getAuthorPodcasts(String author);

    Status<Void> removePodcast(String title);

    Status<Void> addShow(String author, String videoID, String date);

    Status<Show> getShow(String title);

    Status<Void> removeShow(String title);
}