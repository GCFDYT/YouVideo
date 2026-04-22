package Core;

public record ShowClass(String author, String title, String videoId, String date) implements Show {

    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDate() {
        return date;
    }
}
