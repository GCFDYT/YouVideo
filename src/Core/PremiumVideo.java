package Core;

import dataStructures.Iterator;

/**
 * Represents a premium publishable video that includes subtitle support.
 * @author Gonçalo Domingos and João Domingues
 */
public interface PremiumVideo extends PublishableVideo {

    /**
     * Adds a new subtitle to the premium video.
     * @param newSubtitle - the subtitle object to be added.
     */
    void addSubtitle(Subtitle newSubtitle);

    /**
     * Returns an iterator over the subtitles of the video.
     * @return returns an <code>Iterator</code> of subtitles.
     */
    Iterator<Subtitle> getSubtitles();
}