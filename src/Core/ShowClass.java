package Core;

public record ShowClass(String author, String title, String videoId, String date) implements Show {

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }
}
