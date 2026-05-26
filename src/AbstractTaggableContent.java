import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Abstract base class for content that can be tagged (Shows and Podcasts).
 * Manages the shared properties: title, author, and an alphabetically sorted set of tags.
 * @author Gonçalo Domingos and João Domingues
 */
public abstract class AbstractTaggableContent implements TaggableContent {
    private final String title;
    private final String author;
    private final Set<String> tags;

    public AbstractTaggableContent(String title, String author) {
        this.title = title;
        this.author = author;
        this.tags = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public void addTag(String tag) {
        tags.add(tag);
    }

    @Override
    public void removeTag(String tag) {
        tags.remove(tag);
    }

    @Override
    public boolean hasTag(String tag) {
        return tags.contains(tag);
    }

    @Override
    public Iterator<String> getTags() {
        return tags.iterator();
    }
    
    @Override
    public boolean hasTags() {
        return !tags.isEmpty();
    }
}