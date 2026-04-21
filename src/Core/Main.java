package Core;

public class Main {
    // Command names:

    // General
    public static final String CMD_LIST = "help";
    public static final String EXIT_PROGRAM = "exit";

    // Video + Subtitles
    public static final String PUBLISHABLE_VIDEO = "createpublishable";
    public static final String PREMIUM_VIDEO = "createpremium";
    public static final String SUBTITLES_PREMIUM_VIDEO = "addsubtitle";
    public static final String SUBTITLES_LIST = "subtitles";
    public static final String VIDEO_DATA = "getvideo";
    public static final String DELETE_VIDEO = "removevideo";

    // Podcast + Episodes
    public static final String PODCAST = "createpodcast";
    public static final String PODCAST_EPISODE = "addepisode";
    public static final String PODCAST_DATA = "getpodcast";
    public static final String PODCAST_EPISODE_LIST = "episodes";
    public static final String USER_PODCASTS_LIST = "authorpodcasts";
    public static final String DELETE_PODCASTS = "removepodcast";

    // Show
    public static final String SHOW = "createshow";
    public static final String SHOW_DATA = "getshow";
    public static final String DELETE_SHOW = "removeshow";

    // Output messages related to commands:

    // General
    public static final String CMD_ERR = "Unknown command. Type help to see available commands.";
    public static final String EXIT_MSG = "Bye!";

    // Help
    public static final String HELP_HEADER = "Available commands:";

    // Video + Subtitles
    public static final String VIDEO_CREATED = "Video %s created successfully.";
    public static final String INV_LANGUAGE = "Invalid language type.";
    public static final String INV_VALUE = "Invalid value.";
    public static final String VIDEO_ID_EXISTS = "Video with this ID already exists.";
    public static final String PUBLISHABLE_NOT_FOUND = "Publishable Video %s does not exist.";
    public static final String PREMIUM_VIDEO_CREATED = "PREMIUM Video %s created successfully.";
    public static final String INV_SUBTITLE = "Invalid language type in subtitle.";
    public static final String VIDEO_REMOVED = "Video removed successfully.";
    public static final String CANT_REMOVE_EPISODE = "Cannot remove: video is an episode of a podcast.";
    public static final String CANT_REMOVE_USED_VIDEO = "Cannot remove: video is used in a show.";
    public static final String SUBTITLE_ADDED = "Subtitle added successfully.";
    public static final String VIDEO_NOT_FOUND = "Video does not exist.";
    public static final String NOT_PREMIUM = "This operation requires a Premium video.";
    public static final String SUBTITLES_HEADER = "Subtitles for video %s:";
    public static final String SUBTITLE_ENTRY = "- %s (%s)";
    public static final String NO_PREMIUM_VIDEO = "No Premium Video with ID.";

    // Podcast + Episodes
    public static final String PODCAST_CREATED = "Podcast created successfully.";
    public static final String PODCAST_EXISTS = "Podcast with this title already exists.";
    public static final String PODCAST_INFO = "Podcast: %s Author: %s Language: %s";
    public static final String PODCAST_LATEST_EPISODE = "Latest episode date: %s";
    public static final String AUTHOR_PODCASTS_HEADER = "Podcasts by author %s:";
    public static final String PODCAST_ENTRY = "Podcast: %s Author: %s Language: %s";
    public static final String NO_USER_PODCASTS = "No podcasts found for this author.";
    public static final String PODCAST_REMOVED = "Podcast removed successfully.";
    public static final String EPISODE_ADDED = "Episode added successfully.";
    public static final String PODCAST_NOT_FOUND = "Podcast does not exist.";
    public static final String EPISODE_ID_EXISTS = "Episode ID already exists in the system.";
    public static final String INV_EPISODE_DATE = "Episode date must be >= than latest episode date.";
    public static final String EPISODES_HEADER = "Episodes for podcast %s:";
    public static final String EPISODE_ENTRY = "Episode %s: %d min Date: %s";
    public static final String EPISODE_URL = "URL: %s";
    public static final String NO_EPISODES = "No episodes available for this podcast.";

    // Show
    public static final String SHOW_CREATED = "Show created successfully.";
    public static final String SHOW_NOT_FOUND = "Video for show does not exist.";
    public static final String SHOW_EXISTS = "Show with this title already exists.";
    public static final String SHOW_INFO = "Show Date: %s Author: %s";
    public static final String SHOW_VIDEO = "Video: %s";
    public static final String SHOW_FALSE = "Show does not exist.";
    public static final String SHOW_REMOVED = "Show removed successfully.";
}
