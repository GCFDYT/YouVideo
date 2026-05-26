/**
 * Implementation of a Show, representing a broadcast event.
 * A show associates an author with a specific video being transmitted on a given date.
 *
 * @author Gonçalo Domingos and João Domingues
 */
public class ShowImpl extends AbstractTaggableContentImpl implements Show {
    
    private final String videoID;
    private final String date;

    public ShowImpl(String author, String title, String videoID, String date) {
        // Pass the shared attributes up to the abstract class
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
    
}