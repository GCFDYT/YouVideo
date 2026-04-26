package Core;

import dataStructures.Array;
import dataStructures.ArrayClass;
import dataStructures.Iterator;


public class PremiumVideoImpl extends PublishableVideoImpl implements PremiumVideo{

    private final Array<Subtitle> subtitles;

    public PremiumVideoImpl(String videoID, int videoDuration, String url, String publisher,
                            String title, String language, String subtitleUrl,
                            String subtitleLanguageCode) {

        super(videoID, videoDuration, url, publisher, title, language);
        subtitles = new ArrayClass<>();
        Subtitle subtitle = new Subtitle(subtitleUrl, subtitleLanguageCode);
        subtitles.insertLast(subtitle);
    }

    @Override
    public void addSubtitle(Subtitle newSubtitle) {
        subtitles.insertLast(newSubtitle);
    }

    @Override
    public Iterator<Subtitle> getSubtitles() {
        return subtitles.iterator();
    }
}