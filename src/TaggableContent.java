import java.util.Iterator;

/**
 * Interface representing content that can be tagged (Shows and Podcasts).
 * @author Gonçalo Domingos and João Domingues
 */
public interface TaggableContent {
    String getTitle();
    String getAuthor();
    
    void addTag(String tag);
    void removeTag(String tag);
    boolean hasTag(String tag);
    boolean hasTags();
    Iterator<String> getTags();
}