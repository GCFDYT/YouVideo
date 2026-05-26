/**
 * Interface representing a broadcast event of a publishable video.
 * @author Gonçalo Domingos and João Domingues
 */
public interface Show extends TaggableContent {
    /**
     * Gets the unique identifier of the video being broadcasted.
     * @return the video ID
     */
    String getVideoID();

    /**
     * Gets the transmission date of the show.
     * @return the date in YYYY-MM-DD format
     */
    String getDate();
}