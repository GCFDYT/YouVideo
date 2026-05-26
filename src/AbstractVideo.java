/**
 * Abstract base class implementing the AbstractVideo interface.
 * Provides common attributes and behavior for all video types in the system.
 * This class encapsulates the core video properties: unique identifier, title, and duration.
 *
 * NOTE: If an external AbstractVideo interface is not available on the classpath,
 * a simple local interface is provided here to allow compilation. Remove this
 * local interface when the project's real AbstractVideo is present.
 *
 * Author: Gonçalo Domingos and João Domingues
 */

/* Local fallback interface to satisfy compilation when the external
 * AbstractVideo type is not present. It mirrors the expected contract
 * used by AbstractVideoImpl.
 */
interface AbstractVideo {
    String getVideoID();
    String getTitle();
    int getVideoDuration();
}

public abstract class AbstractVideoImpl implements AbstractVideo {

    private final String videoID;
    private final String title;
    private final int videoDuration;

    /**
     * Constructs a new AbstractVideoImpl with the specified video properties.
     *
     * @param videoID the unique identifier of the video
     * @param title the title of the video
     * @param videoDuration the duration of the video in minutes
     */
    protected AbstractVideoImpl(String videoID, String title, int videoDuration) {
        this.videoID = videoID;
        this.title = title;
        this.videoDuration = videoDuration;
    }

    @Override
    public String getVideoID() {
        return videoID;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public int getVideoDuration() {
        return videoDuration;
    }
}