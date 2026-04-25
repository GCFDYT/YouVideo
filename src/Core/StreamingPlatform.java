package Core;

import dataStructures.Array;

public interface StreamingPlatform {

    Status<String> addPublishableVideo(String videoId, int videoDuration, String url,
                                       String publisher, String title, String languageCode);

    Status<String> addPremiumVideo(String videoId, int videoDuration, String url, String publisher,
                                   String title, String languageCode, String subtitleUrl,
                                   String subtitleLanguageCode);

    Status<Void> addSubtitle(String videoId, String subtitleUrl, String subtitleLanguageCode);

    Status<PremiumVideo> getSubtitleList(String videoId);

    Status<PublishableVideo> getVideo(String videoId);

    Status<Void> removeVideo(String videoId);

    Status<Void> addPodcast(String title, String author, String languageCode);

    Status<Void> addPodcastEpisode(String title, String videoId, int videoDuration,
                                   String episodeUrl, String date);

    Status<Podcast> getPodcast(String title);

    Status<Array<PodcastEpisode>> getPodcastEpisodes(String title);

    Status<Array<Podcast>> getAuthorPodcasts(String author);

    Status<Void> removePodcast(String title);

    Status<Void> addShow(String author, String videoId, String date);

    Status<Show> getShow(String title);

    Status<Void> removeShow(String title);
}