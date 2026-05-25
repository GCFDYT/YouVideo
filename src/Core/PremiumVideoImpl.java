package Core;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of a premium video, extending PublishableVideoImpl.
 * Premium videos support additional subtitle tracks that can be added after creation.
 * Each premium video is initialized with one mandatory subtitle file and can have
 * multiple additional subtitles added later.
 *
 * @author Gonçalo Domingos and João Domingues
 */
public class PremiumVideoImpl extends PublishableVideoImpl implements PremiumVideo {

    private final List<SubtitleImpl> subtitles;

    /**
     * Constructs a new premium video with the specified properties and an initial subtitle.
     *
     * @param videoID the unique identifier of the video
     * @param videoDuration the duration of the video in minutes
     * @param url the file URL of the video
     * @param publisher the name of the publisher
     * @param title the title of the video
     * @param language the primary language code of the video
     * @param subtitleUrl the URL of the initial subtitle file
     * @param subtitleLanguageCode the language code of the initial subtitle
     */
    public PremiumVideoImpl(String videoID, int videoDuration, String url, String publisher,
                            String title, String language, String subtitleUrl,
                            String subtitleLanguageCode) {

        super(videoID, videoDuration, url, publisher, title, language);
        subtitles = new ArrayList<>();
        SubtitleImpl subtitleImpl = new SubtitleImpl(subtitleUrl, subtitleLanguageCode);
        subtitles.add(subtitleImpl);
    }

    @Override
    public void addSubtitle(SubtitleImpl newSubtitleImpl) {
        subtitles.add(newSubtitleImpl);
    }

    @Override
    public java.util.Iterator<SubtitleImpl> getSubtitles() {
        return subtitles.iterator();
    }
}