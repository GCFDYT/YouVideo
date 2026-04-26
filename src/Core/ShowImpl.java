package Core;

/**
 * Record implementation of a Show, representing a broadcast event.
 * A show associates an author with a specific video being transmitted on a given date.
 *
 * @param author the name of the show author
 * @param title the title of the show
 * @param videoID the unique identifier of the video being broadcasted
 * @param date the transmission date of the show
 *
 * @author Gonçalo Domingos and João Domingues
 */
public record ShowImpl(String author, String title, String videoID, String date) implements Show {}