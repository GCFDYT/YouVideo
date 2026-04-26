package Core;

/**
 * Represents a scheduled transmission of a publishable video.
 * @author Gonçalo Domingos and João Domingues
 */
public interface Show {

    /**
     * Returns the author of the show.
     * @return returns the <code>author</code> string.
     */
    String author();

    /**
     * Returns the title of the show (which matches the underlying video's title).
     * @return returns the <code>title</code> string.
     */
    String title();

    /**
     * Returns the unique identifier of the video being broadcasted.
     * @return returns the <code>videoID</code> string.
     */
    String videoID();

    /**
     * Returns the transmission date of the show.
     * @return returns the <code>date</code> string.
     */
    String date();
}