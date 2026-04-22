package Core;

import dataStructures.Array;

public interface PremiumVideo extends PublishableVideo{

    void addSubtitle(Subtitle newSubtitle);

    Array getSubtitles();
}
