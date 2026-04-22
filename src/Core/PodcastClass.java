package Core;

public record PodcastClass(String title, String author, String languageCode) implements Podcast {

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public String getLanguageCode() {
        return languageCode;
    }
}
