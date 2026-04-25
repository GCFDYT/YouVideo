package Core;

public record Status<T>(boolean success, String message, T data) {

    public static <T> Status<T> error(String message) {
        return new Status<>(false, message, null);
    }

    public static <T> Status<T> success(String message) {
        return new Status<>(true, message, null);
    }

    public static <T> Status<T> success(String message, T data) {
        return new Status<>(true, message, data);
    }

    public static <T> Status<T> successWithData(T data) {
        return new Status<>(true, null, data);
    }
}