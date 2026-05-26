package Core;

/**
 * Represents the base abstraction of a video in the system.
 * @author Gonçalo Domingos and João Domingues
 */
public interface Video {

    /**
     * Returns the unique identifier of the video.
     * @return returns the <code>videoID</code> string.
     */
    String getVideoID();

    /**
     * Returns the title of the video.
     * @return returns the <code>title</code> string.
     */
    String getTitle();

    /**
     * Returns the duration of the video in minutes.
     * @return returns the <code>videoDuration</code> in minutes.
     * @pre videoDuration > 0
     */
    int getVideoDuration();
}