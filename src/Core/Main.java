package Core;

import java.util.Scanner;

public class Main {
    // Command names:

    // General
    public static final String SHOW_CMD_LIST = "help";
    public static final String EXIT_PROGRAM = "exit";

    // Video + Subtitles
    public static final String CREATE_PUBLISHABLE_VIDEO = "createpublishable";
    public static final String CREATE_PREMIUM_VIDEO = "createpremium";
    public static final String ADD_SUBTITLES_PREMIUM_VIDEO = "addsubtitle";
    public static final String SHOW_VIDEO_SUBTITLES_LIST = "subtitles";
    public static final String SHOW_VIDEO_DATA = "getvideo";
    public static final String DELETE_VIDEO = "removevideo";

    // Podcast + Episodes
    public static final String CREATE_PODCAST = "createpodcast";
    public static final String CREATE_PODCAST_EPISODE = "addepisode";
    public static final String SHOW_PODCAST_DATA = "getpodcast";
    public static final String SHOW_PODCAST_EPISODE_LIST = "episodes";
    public static final String SHOW_USER_PODCASTS_LIST = "authorpodcasts";
    public static final String DELETE_PODCASTS = "removepodcast";

    // Show
    public static final String CREATE_SHOW = "createshow";
    public static final String SHOW_SHOW_DATA = "getshow";
    public static final String DELETE_SHOW = "removeshow";

    // Output messages related to commands:

    // General
    public static final String CMD_ERR = "Unknown command. Type help to see available commands.";
    public static final String EXIT_MSG = "Bye!";

    // Help
    public static final String HELP_MSG = "createpublishable - creates a new publishable video\n" +
            "createpremium - creates a new publishable Premium video\n" +
            "addsubtitle - adds subtitle to Premium video\n" +
            "getvideo - presents publishable video data from its id\n" +
            "subtitles - Lists Premium video subtitles\n" +
            "createpodcast - creates a new podcast with no episodes\n" +
            "addepisode - adds an episode to a podcast\n" +
            "getpodcast - presents podcast data from its title\n" +
            "episodes - List podcast episodes\n" +
            "authorpodcasts - List all podcasts of an author\n" +
            "removepodcast - removes a podcast\n" +
            "createshow - creates show using an existing publishable video\n" +
            "getshow - presents show data from its title\n" +
            "removeshow - removes a show\n" +
            "removevideo - removes a publishable video\n" +
            "help - shows the available commands\n" +
            "exit - terminates the execution of the program";

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

    public static void runProgram(Scanner scanner, StreamingPlatform platform) {
        String command;
        do {
            command = scanner.next();
            switch (command)  {
                case CREATE_PUBLISHABLE_VIDEO -> createPublishableVideo(scanner, platform);
                case CREATE_PREMIUM_VIDEO -> createPremiumVideo(scanner, platform);
                case ADD_SUBTITLES_PREMIUM_VIDEO -> createSubtitle(scanner, platform);
                case SHOW_VIDEO_SUBTITLES_LIST -> showVideoSubtitleList(platform);
                case SHOW_VIDEO_DATA -> showVideoData(scanner, platform);
                case DELETE_VIDEO -> deleteVideo(scanner, platform);
                case CREATE_PODCAST -> createPodcast(scanner, platform);
                case CREATE_PODCAST_EPISODE -> createPodcastEpisode(scanner, platform);
                case SHOW_PODCAST_DATA -> showPodcastData(scanner, platform);
                case SHOW_PODCAST_EPISODE_LIST -> showPodcastEpisodes(scanner, platform);
                case SHOW_USER_PODCASTS_LIST -> showUserPodcastList(scanner, platform);
                case DELETE_PODCASTS -> deletePodcast(scanner, platform);
                case CREATE_SHOW -> createShow(scanner, platform);
                case SHOW_SHOW_DATA -> showShowData(scanner, platform);
                case DELETE_SHOW -> deleteShow(scanner, platform);
                case SHOW_CMD_LIST -> System.out.println(HELP_MSG);
                case EXIT_PROGRAM -> System.out.println(EXIT_MSG);
                default -> System.out.println(CMD_ERR);
            }
        } while (!EXIT_PROGRAM.equals(command));
    }

    private static void deleteShow(Scanner scanner, StreamingPlatform platform) {
    }

    private static void showShowData(Scanner scanner, StreamingPlatform platform) {
        
    }

    private static void createShow(Scanner scanner, StreamingPlatform platform) {
        
    }

    private static void deletePodcast(Scanner scanner, StreamingPlatform platform) {
        
    }

    private static void showUserPodcastList(Scanner scanner, StreamingPlatform platform) {
        
    }

    private static void showPodcastEpisodes(Scanner scanner, StreamingPlatform platform) {
    }

    private static void showPodcastData(Scanner scanner, StreamingPlatform platform) {
        
    }

    private static void createPodcastEpisode(Scanner scanner, StreamingPlatform platform) {
        
    }

    private static void createPodcast(Scanner scanner, StreamingPlatform platform) {
        
    }

    private static void deleteVideo(Scanner scanner, StreamingPlatform platform) {
        
    }

    private static void showVideoData(Scanner scanner, StreamingPlatform platform) {
        
    }

    private static void showVideoSubtitleList(StreamingPlatform platform) {
        
    }

    private static void createSubtitle(Scanner scanner, StreamingPlatform platform) {
        
    }

    private static void createPremiumVideo(Scanner scanner, StreamingPlatform platform) {
    }

    private static void createPublishableVideo(Scanner scanner, StreamingPlatform platform) {
        String videoId = scanner.next(); int videoDuration = scanner.nextInt();
        String url = scanner.next(); scanner.nextLine();

        String publisher = scanner.nextLine();
        String title = scanner.nextLine();
        String language = scanner.next(); scanner.nextLine();
        platform.addPublishableVideo(videoId, videoDuration, url, publisher, title, language);
    }

    public static void main(String[] args) {
        StreamingPlatform platform = new StreamingPlatformClass();
        Scanner scanner = new Scanner(System.in);
        runProgram(scanner, platform);
        scanner.close();
    }
}
