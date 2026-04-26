package Core;

import dataStructures.Iterator;

/**
 * Represents a premium publishable video that includes subtitle support.
 * @author Gonçalo Domingos and João Domingues
 */
public interface PremiumVideo extends PublishableVideo {

    /**
     * Adds a new subtitle to the premium video.
     * @param newSubtitleImpl - the subtitle object to be added.
     * @pre newSubtitleImpl != null
     * @pre isValidLanguageCode(newSubtitleImpl.getSubtitleLanguage())
     */
    void addSubtitle(SubtitleImpl newSubtitleImpl);

    /**
     * Returns an iterator over the subtitles of the video.
     * @return returns an <code>Iterator</code> of subtitles.
     */
    Iterator<SubtitleImpl> getSubtitles();
}