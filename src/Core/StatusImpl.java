package Core;

public record StatusImpl<D>(boolean success, String message, D data) implements Status<D> {

    public static <D> StatusImpl<D> error(String message) {
        return new StatusImpl<>(false, message, null);
    }

    public static <D> StatusImpl<D> success(String message) {
        return new StatusImpl<>(true, message, null);
    }

    public static <D> StatusImpl<D> success(String message, D data) {
        return new StatusImpl<>(true, message, data);
    }

    public static <D> StatusImpl<D> successWithData(D data) {
        return new StatusImpl<>(true, null, data);
    }
}