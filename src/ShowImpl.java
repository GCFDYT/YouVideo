/**
 * Implementation of a Show, representing a broadcast event.
 * A show associates an author with a specific video being transmitted on a given date.
 *
 * @author Gonçalo Domingos and João Domingues
 */
public class ShowImpl extends AbstractTaggableContent implements Show {

    private final String videoID;
    private final String date;

    public ShowImpl(String author, String title, String videoID, String date) {
        super(title, author);
        this.videoID = videoID;
        this.date = date;
    }

    @Override
    public String getVideoID() {
        return videoID;
    }

    @Override
    public String getDate() {
        return date;
    }

    @Override
    public boolean equals(Object obj) {
        boolean isEqual = false;
        if (this == obj) {
            isEqual = true;
        } else if (obj instanceof ShowImpl that) {
            isEqual = this.getTitle().equalsIgnoreCase(that.getTitle());
        }
        return isEqual;
    }

    @Override
    public int hashCode() {
        return getTitle().toLowerCase().hashCode();
    }
}