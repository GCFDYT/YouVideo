package Core;

public interface Status<D> {
    boolean success();

    String message();

    D data();
}