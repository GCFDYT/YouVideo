package Core;

import dataStructures.Array;
import dataStructures.ArrayClass;


public class PremiumVideoImpl extends PublishableVideoImpl implements PremiumVideo{

    private final Array<Subtitle> subtitles;

    public PremiumVideoImpl(String videoId, int videoDuration, String url, String publisher,
                            String title, String language, String subtitleUrl, String subtitleLanguageCode) {

        super(videoId, videoDuration, url, publisher, title, language);

        subtitles = new ArrayClass<>();

        Subtitle subtitle = new Subtitle(subtitleUrl, subtitleLanguageCode);

        subtitles.insertLast(subtitle);
    }

    @Override
    public void addSubtitle(Subtitle newSubtitle) {
        subtitles.insertLast(newSubtitle);
    }

    @Override
    public Array<Subtitle> getSubtitles() {
        return subtitles;
    }
}