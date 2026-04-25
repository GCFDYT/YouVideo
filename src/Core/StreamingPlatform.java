package Core;

import dataStructures.Array;

public interface StreamingPlatform {

    StatusImpl<String> addPublishableVideo(String videoID, int videoDuration, String url,
                                           String publisher, String title, String languageCode);

    StatusImpl<String> addPremiumVideo(String videoID, int videoDuration, String url, String publisher,
                                       String title, String languageCode, String subtitleUrl,
                                       String subtitleLanguageCode);

    StatusImpl<Void> addSubtitle(String videoID, String subtitleUrl, String subtitleLanguageCode);

    StatusImpl<PremiumVideo> getSubtitleList(String videoID);

    StatusImpl<PublishableVideo> getVideo(String videoID);

    StatusImpl<Void> removeVideo(String videoID);

    StatusImpl<Void> addPodcast(String title, String author, String languageCode);

    StatusImpl<Void> addPodcastEpisode(String title, String videoID, int videoDuration,
                                       String episodeUrl, String date);

    StatusImpl<Podcast> getPodcast(String title);

    StatusImpl<Array<PodcastEpisode>> getPodcastEpisodes(String title);

    StatusImpl<Array<Podcast>> getAuthorPodcasts(String author);

    StatusImpl<Void> removePodcast(String title);

    StatusImpl<Void> addShow(String author, String videoID, String date);

    StatusImpl<Show> getShow(String title);

    StatusImpl<Void> removeShow(String title);
}