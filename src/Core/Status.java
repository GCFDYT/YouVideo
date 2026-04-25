package Core;

public record Status<T>(boolean success, String message, T data) {

    // 1. For Errors (No data needed)
    public static <T> Status<T> error(String message) {
        return new Status<>(false, message, null);
    }

    // 2. For Simple Successes (Just a message, like "Removed successfully")
    public static <T> Status<T> success(String message) {
        return new Status<>(true, message, null);
    }

    // 3. For Successes that need a message AND data (like returning a videoId for a printf)
    public static <T> Status<T> success(String message, T data) {
        return new Status<>(true, message, data);
    }

    // 4. For Getters (Just returning the requested data object)
    public static <T> Status<T> successWithData(T data) {
        return new Status<>(true, null, data);
    }
}