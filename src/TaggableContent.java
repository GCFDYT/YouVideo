import java.util.Iterator;

/**
 * Interface representing content that can be tagged (Shows and Podcasts).
 * @author Gonçalo Domingos and João Domingues
 */
public interface TaggableContent {
    String getTitle();
    String getAuthor();
    
    boolean addTag(String tag);
    boolean removeTag(String tag);
    boolean hasTag(String tag);
    boolean hasTags();
    Iterator<String> getTags();
}