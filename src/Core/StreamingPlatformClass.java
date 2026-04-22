package Core;

import dataStructures.Array;
import dataStructures.ArrayClass;

public class StreamingPlatformClass implements StreamingPlatform{

    private Array videos;
    private Array podcasts;
    private Array shows;
    private Array authors;

    public StreamingPlatformClass() {
        videos = new ArrayClass();
        podcasts = new ArrayClass();
        shows = new ArrayClass();
        authors = new ArrayClass();
    }
}
