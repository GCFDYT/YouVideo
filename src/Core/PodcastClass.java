package Core;

public record PodcastClass(String title, String author, String languageCode) implements Podcast {

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getLanguageCode() {
        return languageCode;
    }
}
