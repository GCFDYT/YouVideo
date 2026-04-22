package Core;

import java.util.Locale;
import java.util.Scanner;

public class Main {
    // Command names:

    // Core.Video + Subtitles
    public static final String CREATE_PUBLISHABLE_VIDEO = "createpublishable";
    public static final String CREATE_PREMIUM_VIDEO = "createpremium";
    public static final String ADD_SUBTITLES_PREMIUM_VIDEO = "addsubtitle";
    public static final String DISPLAY_VIDEO_DATA = "getvideo";
    public static final String DISPLAY_VIDEO_SUBTITLES_LIST = "subtitles";
    public static final String DELETE_VIDEO = "removevideo";

    // Podcast + Episodes
    public static final String CREATE_PODCAST = "createpodcast";
    public static final String ADD_PODCAST_EPISODE = "addepisode";
    public static final String DISPLAY_PODCAST_DATA = "getpodcast";
    public static final String DISPLAY_PODCAST_EPISODE_LIST = "episodes";
    public static final String DISPLAY_AUTHOR_PODCAST_LIST = "authorpodcasts";
    public static final String DELETE_PODCAST = "removepodcast";

    // Show
    public static final String CREATE_SHOW = "createshow";
    public static final String DISPLAY_SHOW_DATA = "getshow";
    public static final String DELETE_SHOW = "removeshow";

    // General
    public static final String DISPLAY_CMD_LIST = "help";
    public static final String EXIT_PROGRAM = "exit";

    // Output messages related to commands:

    // General
    public static final String CMD_ERR = "Unknown command. Type help to see available commands.";
    public static final String EXIT_MSG = "Bye!";

    // Help
    public static final String HELP_MSG = CREATE_PUBLISHABLE_VIDEO + " - creates a new publishable video\n" +
            CREATE_PREMIUM_VIDEO  + " - creates a new publishable Premium video\n" +
            ADD_SUBTITLES_PREMIUM_VIDEO + " - adds subtitle to Premium video\n" +
            DISPLAY_VIDEO_DATA + " - presents publishable video data from its id\n" +
            DISPLAY_VIDEO_SUBTITLES_LIST + " - Lists Premium video subtitles\n" +
            CREATE_PODCAST + " - creates a new podcast with no episodes\n" +
            ADD_PODCAST_EPISODE + " - adds an episode to a podcast\n" +
            DISPLAY_PODCAST_DATA + " - presents podcast data from its title\n" +
            DISPLAY_PODCAST_EPISODE_LIST + " - List podcast episodes\n" +
            DISPLAY_AUTHOR_PODCAST_LIST + " - List all podcasts of an author\n" +
            DELETE_PODCAST + " - removes a podcast\n" +
            CREATE_SHOW + " - creates show using an existing publishable video\n" +
            DISPLAY_SHOW_DATA + " - presents show data from its title\n" +
            DELETE_SHOW + " - removes a show\n" +
            DELETE_VIDEO + " - removes a publishable video\n" +
            DISPLAY_CMD_LIST + " - shows the available commands\n" +
            EXIT_PROGRAM + " - terminates the execution of the program";

    // Core.Video + Subtitles
    public static final String VIDEO_CREATED = "Core.Video %s created successfully.";
    public static final String INV_LANGUAGE = "Invalid language type.";
    public static final String INV_VALUE = "Invalid value.";
    public static final String VIDEO_ID_EXISTS = "Core.Video with this ID already exists.";
    public static final String PUBLISHABLE_NOT_FOUND = "Publishable Core.Video %s does not exist.";
    public static final String PREMIUM_VIDEO_CREATED = "PREMIUM Core.Video %s created successfully.";
    public static final String INV_SUBTITLE = "Invalid language type in subtitle.";
    public static final String VIDEO_REMOVED = "Core.Video removed successfully.";
    public static final String CANT_REMOVE_EPISODE = "Cannot remove: video is an episode of a podcast.";
    public static final String CANT_REMOVE_USED_VIDEO = "Cannot remove: video is used in a show.";
    public static final String SUBTITLE_ADDED = "Subtitle added successfully.";
    public static final String VIDEO_NOT_FOUND = "Core.Video does not exist.";
    public static final String NOT_PREMIUM = "This operation requires a Premium video.";
    public static final String SUBTITLES_HEADER = "Subtitles for video %s:";
    public static final String SUBTITLE_ENTRY = "- %s (%s)";
    public static final String NO_PREMIUM_VIDEO = "No Premium Core.Video with ID.";

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
    public static final String SHOW_NOT_FOUND = "Core.Video for show does not exist.";
    public static final String SHOW_EXISTS = "Show with this title already exists.";
    public static final String SHOW_INFO = "Show Date: %s Author: %s";
    public static final String SHOW_VIDEO = "Core.Video: %s";
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
                case DISPLAY_VIDEO_SUBTITLES_LIST -> displayVideoSubtitleList(scanner, platform);
                case DISPLAY_VIDEO_DATA -> displayVideoData(scanner, platform);
                case DELETE_VIDEO -> deleteVideo(scanner, platform);
                case CREATE_PODCAST -> createPodcast(scanner, platform);
                case ADD_PODCAST_EPISODE -> createPodcastEpisode(scanner, platform);
                case DISPLAY_PODCAST_DATA -> displayPodcastData(scanner, platform);
                case DISPLAY_PODCAST_EPISODE_LIST -> displayPodcastEpisodes(scanner, platform);
                case DISPLAY_AUTHOR_PODCAST_LIST -> displayAuthorPodcastList(scanner, platform);
                case DELETE_PODCAST -> deletePodcast(scanner, platform);
                case CREATE_SHOW -> createShow(scanner, platform);
                case DISPLAY_SHOW_DATA -> displayShowData(scanner, platform);
                case DELETE_SHOW -> deleteShow(scanner, platform);
                case DISPLAY_CMD_LIST -> System.out.println(HELP_MSG);
                case EXIT_PROGRAM -> System.out.println(EXIT_MSG);
                default -> System.out.println(CMD_ERR);
            }
        } while (!EXIT_PROGRAM.equals(command));
    }

    private static void deleteShow(Scanner scanner, StreamingPlatform platform) {
        String title = scanner.nextLine();
        platform.removeShow(title);
    }

    private static void displayShowData(Scanner scanner, StreamingPlatform platform) {
        String title =  scanner.nextLine();
        platform.getShow(title);
    }

    private static void createShow(Scanner scanner, StreamingPlatform platform) {
        String author = scanner.nextLine();
        String videoId = scanner.next(); scanner.nextLine();
        String date = scanner.next(); scanner.nextLine();
        platform.addShow(author, videoId, date);
    }

    private static void deletePodcast(Scanner scanner, StreamingPlatform platform) {
        String title = scanner.nextLine();
        platform.removePodcast(title);
    }

    private static void displayAuthorPodcastList(Scanner scanner, StreamingPlatform platform) {
        String author = scanner.nextLine();
        platform.getAuthorPodcasts(author);
    }

    private static void displayPodcastEpisodes(Scanner scanner, StreamingPlatform platform) {
        String title = scanner.nextLine();
        platform.getPodcastEpisodes(title);
    }

    private static void displayPodcastData(Scanner scanner, StreamingPlatform platform) {
        String title = scanner.nextLine();
        platform.getPodcast(title);
    }

    private static void createPodcastEpisode(Scanner scanner, StreamingPlatform platform) {
        String title = scanner.nextLine();
        String videoId = scanner.next(); scanner.nextLine();
        int videoDuration = scanner.nextInt();
        String url = scanner.next(); scanner.nextLine();
        String date = scanner.next(); scanner.nextLine();
        platform.addPodcastEpisode(title, videoId, videoDuration, url, date);
    }

    private static void createPodcast(Scanner scanner, StreamingPlatform platform) {
        String title = scanner.nextLine();
        String author = scanner.nextLine();
        String languageCode = scanner.next(); scanner.nextLine();
        platform.addPodcast(title, author, languageCode);
    }

    private static void deleteVideo(Scanner scanner, StreamingPlatform platform) {
        String videoId = scanner.next(); scanner.nextLine();
        platform.removeVideo(videoId);
    }

    private static void displayVideoData(Scanner scanner, StreamingPlatform platform) {
        String videoId = scanner.next(); scanner.nextLine();
        platform.getVideo(videoId);
    }

    private static void displayVideoSubtitleList(Scanner scanner, StreamingPlatform platform) {
        String videoId = scanner.next(); scanner.nextLine();
        platform.getSubtitleList(videoId);
    }

    private static void createSubtitle(Scanner scanner, StreamingPlatform platform) {
        String videoId = scanner.next(); String subtitleUrl = scanner.next(); scanner.nextLine();
        String subtitleLanguageCode = scanner.next(); scanner.nextLine();
        platform.addSubtitle(videoId, subtitleUrl, subtitleLanguageCode);
    }

    private static void createPremiumVideo(Scanner scanner, StreamingPlatform platform) {
        String videoId = scanner.next(); int videoDuration = scanner.nextInt();
        String url = scanner.next(); scanner.nextLine();

        String publisher = scanner.nextLine();
        String title = scanner.nextLine();
        String languageCode = scanner.next(); scanner.nextLine();
        String subtitleUrl = scanner.next(); scanner.nextLine();
        String subtitleLanguageCode = scanner.next(); scanner.nextLine();
        platform.addPremiumVideo(videoId, videoDuration, url, publisher, title, languageCode, subtitleUrl, subtitleLanguageCode);
    }

    private static void createPublishableVideo(Scanner scanner, StreamingPlatform platform) {
        String videoId = scanner.next(); int videoDuration = scanner.nextInt();
        String url = scanner.next(); scanner.nextLine();

        String publisher = scanner.nextLine();
        String title = scanner.nextLine();
        String languageCode = scanner.next(); scanner.nextLine();
        platform.addPublishableVideo(videoId, videoDuration, url, publisher, title, languageCode);
    }

    public static void main(String[] args) {
        StreamingPlatform platform = new StreamingPlatformClass();
        Scanner scanner = new Scanner(System.in);
        runProgram(scanner, platform);
        scanner.close();
    }
}
