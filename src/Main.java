import dataStructures.Iterator;
import Core.StreamingPlatform;
import Core.StreamingPlatformImpl;
import Core.PublishableVideo;
import Core.PremiumVideo;
import Core.SubtitleImpl;
import Core.Podcast;
import Core.PodcastEpisode;
import Core.Show;
import java.util.Locale;
import java.util.Scanner;

/**
 * Main class for the You Video streaming platform application.
 * Provides a command-line interface for interacting with the streaming platform,
 * allowing users to manage videos, podcasts, and shows through text commands.
 *
 * @author Gonçalo Domingos and João Domingues
 */
public class Main {
    // --- Command Names ---
    private static final String CREATE_PUBLISHABLE_VIDEO = "createpublishable";
    private static final String CREATE_PREMIUM_VIDEO = "createpremium";
    private static final String ADD_SUBTITLES_PREMIUM_VIDEO = "addsubtitle";
    private static final String DISPLAY_VIDEO_DATA = "getvideo";
    private static final String DISPLAY_VIDEO_SUBTITLES_LIST = "subtitles";
    private static final String DELETE_VIDEO = "removevideo";
    private static final String CREATE_PODCAST = "createpodcast";
    private static final String ADD_PODCAST_EPISODE = "addepisode";
    private static final String DISPLAY_PODCAST_DATA = "getpodcast";
    private static final String DISPLAY_PODCAST_EPISODE_LIST = "episodes";
    private static final String DISPLAY_AUTHOR_PODCAST_LIST = "authorpodcasts";
    private static final String DELETE_PODCAST = "removepodcast";
    private static final String CREATE_SHOW = "createshow";
    private static final String DISPLAY_SHOW_DATA = "getshow";
    private static final String DELETE_SHOW = "removeshow";
    private static final String DISPLAY_CMD_LIST = "help";
    private static final String EXIT_PROGRAM = "exit";

    // --- Output Messages ---
    private static final String CMD_ERR = "Unknown command. Type help to see available commands.";
    private static final String EXIT_MSG = "Bye!";
    private static final String VIDEO_CREATED = "Video %s created successfully.";
    private static final String PREMIUM_VIDEO_CREATED = "PREMIUM Video %s created successfully.";
    private static final String VIDEO_DISPLAY = "Video %s %d Title: %s%n";
    private static final String PREMIUM_VIDEO_DISPLAY = "PREMIUM Video %s %d Title: %s%n";
    private static final String VIDEO_FILE_INFO = "File: %s Publisher: %s Language: %s%n";
    private static final String INV_LANGUAGE = "Invalid language type.";
    private static final String INV_VALUE = "Invalid value.";
    private static final String VIDEO_ID_EXISTS = "Video with this ID already exists.";
    private static final String INV_SUBTITLE = "Invalid language type in subtitle.";
    private static final String PUBLISHABLE_NOT_FOUND = "Publishable Video %s does not exist.";
    private static final String VIDEO_NOT_FOUND = "Video does not exist.";
    private static final String NOT_PREMIUM = "This operation requires a Premium video.";
    private static final String NO_PREMIUM_VIDEO = "No Premium Video with ID.";
    private static final String SUBTITLE_ADDED = "SubtitleImpl added successfully.";
    private static final String SUBTITLES_HEADER = "Subtitles for video %s:";
    private static final String SUBTITLE_ENTRY = "- %s (%s)";
    private static final String VIDEO_REMOVED = "Video removed successfully.";
    private static final String CANT_REMOVE_EPISODE = "Cannot remove: " +
            "video is an episode of a podcast.";
    private static final String CANT_REMOVE_USED_VIDEO = "Cannot remove: " +
            "video is used in a show.";
    private static final String PODCAST_CREATED = "Podcast created successfully.";
    private static final String PODCAST_EXISTS = "Podcast with this title already exists.";
    private static final String PODCAST_NOT_FOUND = "Podcast does not exist.";
    private static final String PODCAST_INFO = "Podcast: %s Author: %s Language: %s";
    private static final String PODCAST_LATEST_EPISODE = "Latest episode date: %s";
    private static final String PODCAST_REMOVED = "Podcast removed successfully.";
    private static final String EPISODE_ADDED = "Episode added successfully.";
    private static final String EPISODE_ID_EXISTS = "Episode ID already exists in the system.";
    private static final String INV_EPISODE_DATE = "Episode date must be >= " +
            "than latest episode date.";
    private static final String EPISODES_HEADER = "Episodes for podcast %s:";
    private static final String EPISODE_ENTRY = "Episode %s: %d min Date: %s";
    private static final String EPISODE_URL = "URL: %s";
    private static final String NO_EPISODES = "No episodes available for this podcast.";
    private static final String AUTHOR_PODCASTS_HEADER = "Podcasts by author %s:";
    private static final String PODCAST_ENTRY = "Podcast: %s Author: %s Language: %s";
    private static final String NO_USER_PODCASTS = "No podcasts found for this author.";
    private static final String SHOW_CREATED = "Show created successfully.";
    private static final String SHOW_NOT_FOUND = "Video for show does not exist.";
    private static final String SHOW_EXISTS = "Show with this title already exists.";
    private static final String SHOW_INFO = "Show Date: %s Author: %s";
    private static final String SHOW_VIDEO = "Video: %s";
    private static final String SHOW_FALSE = "Show does not exist.";
    private static final String SHOW_REMOVED = "Show removed successfully.";
    private static final String HELP_MSG =
            CREATE_PUBLISHABLE_VIDEO + " - creates a new publishable video\n" +
                    CREATE_PREMIUM_VIDEO + " - creates a new publishable Premium video\n" +
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

    // --- Field Indexes for Common Video Fields ---
    private static final int IDX_VIDEO_ID = 0;
    private static final int IDX_VIDEO_DURATION = 1;
    private static final int IDX_URL = 2;
    private static final int IDX_PUBLISHER = 3;
    private static final int IDX_TITLE = 4;
    private static final int IDX_LANGUAGE_CODE = 5;
    private static final int NUM_FIELDS = 6;

    /**
     * Reads the common fields for video creation (ID, duration, URL, publisher, title, language).
     *
     * @param scanner the Scanner object for reading input
     * @return a String array containing the six common video fields
     */
    private static String[] readCommonVideoFields(Scanner scanner) {
        String[] fields = new String[NUM_FIELDS];
        fields[IDX_VIDEO_ID] = scanner.next();
        fields[IDX_VIDEO_DURATION] = String.valueOf(scanner.nextInt());
        fields[IDX_URL] = scanner.next();
        consumeLine(scanner);
        fields[IDX_PUBLISHER] = readLine(scanner);
        fields[IDX_TITLE] = readLine(scanner);
        fields[IDX_LANGUAGE_CODE] = scanner.next();
        consumeLine(scanner);
        return fields;
    }

    /**
     * Consumes the remaining line in the scanner input.
     * Used to clear the buffer after reading tokens.
     *
     * @param scanner the Scanner object for reading input
     */
    private static void consumeLine(Scanner scanner) {
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
    }

    /**
     * Reads an entire line from the scanner and removes trailing/leading spaces.
     *
     * @param scanner the Scanner object for reading input
     * @return the line read from the scanner
     */
    private static String readLine(Scanner scanner) {
        String line = scanner.nextLine();
        return line != null ? line.trim() : "";
    }

    /**
     * Main program loop that processes user commands until exit.
     * Reads commands from the scanner and delegates to the appropriate handler methods.
     *
     * @param scanner the Scanner object for reading input
     * @param platform the StreamingPlatform instance to operate on
     */
    public static void runProgram(Scanner scanner, StreamingPlatform platform) {
        String command;
        do {
            command = scanner.next();
            switch (command.toLowerCase()) {
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
                default -> {
                    System.out.println(CMD_ERR);
                    consumeLine(scanner);
                }
            }
        } while (!EXIT_PROGRAM.equals(command.toLowerCase()));
    }

    /**
     * Handles the creation of a new publishable video.
     * Reads video data from input, validates it, and adds it to the platform.
     *
     * @param scanner the Scanner object for reading input
     * @param platform the StreamingPlatform instance to operate on
     */
    private static void createPublishableVideo(Scanner scanner, StreamingPlatform platform) {
        String[] fields = readCommonVideoFields(scanner);
        String videoID = fields[IDX_VIDEO_ID];
        int duration = Integer.parseInt(fields[IDX_VIDEO_DURATION]);
        String language = fields[IDX_LANGUAGE_CODE];

        if (!platform.isValidLanguageCode(language)) {
            System.out.println(INV_LANGUAGE);
        } else if (duration <= 0) {
            System.out.println(INV_VALUE);
        } else if (platform.hasVideo(videoID)) {
            System.out.println(VIDEO_ID_EXISTS);
        } else {
            platform.addPublishableVideo(videoID, duration, fields[IDX_URL],
                    fields[IDX_PUBLISHER], fields[IDX_TITLE], language);
            System.out.printf(VIDEO_CREATED + "%n", videoID);
        }
    }

    /**
     * Handles the creation of a new premium video with subtitles.
     * Reads video data and subtitle information from input, validates it, and adds it to the platform.
     *
     * @param scanner the Scanner object for reading input
     * @param platform the StreamingPlatform instance to operate on
     */
    private static void createPremiumVideo(Scanner scanner, StreamingPlatform platform) {
        String[] fields = readCommonVideoFields(scanner);
        String subtitleUrl = scanner.next();
        String subtitleLanguage = scanner.next();
        consumeLine(scanner);

        String videoID = fields[IDX_VIDEO_ID];
        int duration = Integer.parseInt(fields[IDX_VIDEO_DURATION]);
        String language = fields[IDX_LANGUAGE_CODE];

        if (!platform.isValidLanguageCode(language)) {
            System.out.println(INV_LANGUAGE);
        } else if (!platform.isValidLanguageCode(subtitleLanguage)) {
            System.out.println(INV_SUBTITLE);
        } else if (duration <= 0) {
            System.out.println(INV_VALUE);
        } else if (platform.hasVideo(videoID)) {
            System.out.println(VIDEO_ID_EXISTS);
        } else {
            platform.addPremiumVideo(videoID, duration, fields[IDX_URL], fields[IDX_PUBLISHER],
                    fields[IDX_TITLE], language, subtitleUrl, subtitleLanguage);
            System.out.printf(PREMIUM_VIDEO_CREATED + "%n", videoID);
        }
    }

    /**
     * Handles adding a subtitle to an existing premium video.
     *
     * @param scanner the Scanner object for reading input
     * @param platform the StreamingPlatform instance to operate on
     */
    private static void createSubtitle(Scanner scanner, StreamingPlatform platform) {
        String videoID = scanner.next();
        String subtitleUrl = scanner.next();
        String subtitleLanguage = scanner.next();
        consumeLine(scanner);

        if (!platform.isValidLanguageCode(subtitleLanguage)) {
            System.out.println(INV_SUBTITLE);
        } else if (!platform.hasPublishableVideo(videoID)) {
            System.out.println(VIDEO_NOT_FOUND);
        } else if (!platform.isPremiumVideo(videoID)) {
            System.out.println(NOT_PREMIUM);
        } else {
            platform.addSubtitle(videoID, subtitleUrl, subtitleLanguage);
            System.out.println(SUBTITLE_ADDED);
        }
    }

    /**
     * Displays detailed information about a publishable video.
     * Shows different formatting for premium vs standard videos.
     *
     * @param scanner the Scanner object for reading input
     * @param platform the StreamingPlatform instance to operate on
     */
    private static void displayVideoData(Scanner scanner, StreamingPlatform platform) {
        String videoID = scanner.next();
        consumeLine(scanner);

        if (!platform.hasPublishableVideo(videoID)) {
            System.out.printf(PUBLISHABLE_NOT_FOUND + "%n", videoID);
        } else {
            PublishableVideo video = platform.getVideo(videoID);
            Locale locale = Locale.of(video.getLanguageCode());
            if (video instanceof PremiumVideo) {
                System.out.printf(PREMIUM_VIDEO_DISPLAY, video.getVideoID(),
                        video.getVideoDuration(), video.getTitle());
            } else {
                System.out.printf(VIDEO_DISPLAY, video.getVideoID(),
                        video.getVideoDuration(), video.getTitle());
            }
            System.out.printf(VIDEO_FILE_INFO, video.getUrl(), video.getPublisher(),
                    locale.getDisplayLanguage().toUpperCase());
        }
    }

    /**
     * Displays all subtitles associated with a premium video.
     *
     * @param scanner the Scanner object for reading input
     * @param platform the StreamingPlatform instance to operate on
     */
    private static void displayVideoSubtitleList(Scanner scanner, StreamingPlatform platform) {
        String videoID = scanner.next();
        consumeLine(scanner);

        if (!platform.isPremiumVideo(videoID)) {
            System.out.println(NO_PREMIUM_VIDEO);
        } else {
            PublishableVideo video = platform.getVideo(videoID);
            System.out.printf(SUBTITLES_HEADER + "%n", video.getTitle());

            Iterator<SubtitleImpl> it = platform.getSubtitles(videoID);
            while (it.hasNext()) {
                SubtitleImpl subtitleImpl = it.next();
                Locale locale = Locale.of(subtitleImpl.getSubtitleLanguage());
                System.out.printf(SUBTITLE_ENTRY + "%n", subtitleImpl.getSubtitleUrl(),
                        locale.getDisplayLanguage().toUpperCase());
            }
        }
    }

    /**
     * Handles the removal of a video from the platform.
     * Checks for constraints (video used in podcast or show) before removal.
     *
     * @param scanner the Scanner object for reading input
     * @param platform the StreamingPlatform instance to operate on
     */
    private static void deleteVideo(Scanner scanner, StreamingPlatform platform) {
        String videoID = scanner.next();
        consumeLine(scanner);

        if (!platform.hasVideo(videoID)) {
            System.out.println(VIDEO_NOT_FOUND);
        } else if (platform.hasPodcastEpisode(videoID)) {
            System.out.println(CANT_REMOVE_EPISODE);
        } else if (platform.isVideoUsedInShow(videoID)) {
            System.out.println(CANT_REMOVE_USED_VIDEO);
        } else {
            platform.removeVideo(videoID);
            System.out.println(VIDEO_REMOVED);
        }
    }

    /**
     * Handles the creation of a new podcast.
     *
     * @param scanner the Scanner object for reading input
     * @param platform the StreamingPlatform instance to operate on
     */
    private static void createPodcast(Scanner scanner, StreamingPlatform platform) {
        String title = readLine(scanner);
        String author = readLine(scanner);
        String language = scanner.next();
        consumeLine(scanner);

        if (!platform.isValidLanguageCode(language)) {
            System.out.println(INV_LANGUAGE);
        } else if (platform.hasPodcast(title)) {
            System.out.println(PODCAST_EXISTS);
        } else {
            platform.addPodcast(title, author, language);
            System.out.println(PODCAST_CREATED);
        }
    }

    /**
     * Handles the addition of a new episode to an existing podcast.
     * Validates episode date against the podcast's latest episode.
     *
     * @param scanner the Scanner object for reading input
     * @param platform the StreamingPlatform instance to operate on
     */
    private static void createPodcastEpisode(Scanner scanner, StreamingPlatform platform) {
        String title = readLine(scanner);
        String videoID = scanner.next();
        int duration = scanner.nextInt();
        String url = scanner.next();
        String date = scanner.next();
        consumeLine(scanner);
        String episodeTitle = "Episode " + videoID;

        if (duration <= 0) {
            System.out.println(INV_VALUE);
        } else if (!platform.hasPodcast(title)) {
            System.out.println(PODCAST_NOT_FOUND);
        } else if (platform.hasVideo(videoID)) {
            System.out.println(EPISODE_ID_EXISTS);
        } else if (!platform.isValidEpisodeDate(title, date)) {
            System.out.println(INV_EPISODE_DATE);
        } else {
            platform.addPodcastEpisode(title, videoID, duration, url, date, episodeTitle);
            System.out.println(EPISODE_ADDED);
        }
    }

    /**
     * Displays information about a podcast including its latest episode date.
     *
     * @param scanner the Scanner object for reading input
     * @param platform the StreamingPlatform instance to operate on
     */
    private static void displayPodcastData(Scanner scanner, StreamingPlatform platform) {
        String title = readLine(scanner);

        if (!platform.hasPodcast(title)) {
            System.out.println(PODCAST_NOT_FOUND);
        } else {
            Podcast podcast = platform.getPodcast(title);
            Locale locale = Locale.of(podcast.getLanguageCode());
            System.out.printf(PODCAST_INFO + "%n", podcast.getTitle(),
                    podcast.getAuthor(), locale.getDisplayLanguage().toUpperCase());

            if (podcast.hasEpisodes()) {
                System.out.printf(PODCAST_LATEST_EPISODE + "%n",
                        podcast.getLatestEpisode().getDate());
            }
        }
    }

    /**
     * Displays all episodes of a podcast with their details.
     *
     * @param scanner the Scanner object for reading input
     * @param platform the StreamingPlatform instance to operate on
     */
    private static void displayPodcastEpisodes(Scanner scanner, StreamingPlatform platform) {
        String title = readLine(scanner);

        if (!platform.hasPodcast(title)) {
            System.out.println(PODCAST_NOT_FOUND);
        } else {
            Podcast podcast = platform.getPodcast(title);
            if (!podcast.hasEpisodes()) {
                System.out.println(NO_EPISODES);
            } else {
                System.out.printf(EPISODES_HEADER + "%n", title);
                Iterator<PodcastEpisode> it = platform.getPodcastEpisodes(title);
                while (it.hasNext()) {
                    PodcastEpisode ep = it.next();
                    System.out.printf(EPISODE_ENTRY + "%n", ep.getVideoID(),
                            ep.getVideoDuration(), ep.getDate());
                    System.out.printf(EPISODE_URL + "%n", ep.getUrl());
                }
            }
        }
    }

    /**
     * Displays all podcasts created by a specific author.
     *
     * @param scanner the Scanner object for reading input
     * @param platform the StreamingPlatform instance to operate on
     */
    private static void displayAuthorPodcastList(Scanner scanner, StreamingPlatform platform) {
        String author = readLine(scanner);

        if (!platform.hasAuthorPodcasts(author)) {
            System.out.println(NO_USER_PODCASTS);
        } else {
            System.out.printf(AUTHOR_PODCASTS_HEADER + "%n", author);
            Iterator<Podcast> it = platform.getAuthorPodcasts(author);
            while (it.hasNext()) {
                Podcast podcast = it.next();
                Locale locale = Locale.of(podcast.getLanguageCode());
                System.out.printf(PODCAST_ENTRY + "%n", podcast.getTitle(),
                        podcast.getAuthor(), locale.getDisplayLanguage().toUpperCase());
            }
        }
    }

    /**
     * Handles the removal of a podcast from the platform.
     *
     * @param scanner the Scanner object for reading input
     * @param platform the StreamingPlatform instance to operate on
     */
    private static void deletePodcast(Scanner scanner, StreamingPlatform platform) {
        String title = readLine(scanner);

        if (!platform.hasPodcast(title)) {
            System.out.println(PODCAST_NOT_FOUND);
        } else {
            platform.removePodcast(title);
            System.out.println(PODCAST_REMOVED);
        }
    }

    /**
     * Handles the creation of a new show using an existing video.
     *
     * @param scanner the Scanner object for reading input
     * @param platform the StreamingPlatform instance to operate on
     */
    private static void createShow(Scanner scanner, StreamingPlatform platform) {
        String author = readLine(scanner);
        String videoID = scanner.next();
        String date = scanner.next();
        consumeLine(scanner);

        if (!platform.hasPublishableVideo(videoID)) {
            System.out.println(SHOW_NOT_FOUND);
        } else {
            String videoTitle = platform.getVideo(videoID).getTitle();
            if (platform.hasShow(videoTitle)) {
                System.out.println(SHOW_EXISTS);
            } else {
                platform.addShow(author, videoID, date);
                System.out.println(SHOW_CREATED);
            }
        }
    }

    /**
     * Displays information about a show.
     *
     * @param scanner the Scanner object for reading input
     * @param platform the StreamingPlatform instance to operate on
     */
    private static void displayShowData(Scanner scanner, StreamingPlatform platform) {
        String title = readLine(scanner);

        if (!platform.hasShow(title)) {
            System.out.println(SHOW_FALSE);
        } else {
            Show show = platform.getShow(title);
            System.out.printf(SHOW_INFO + "%n", show.date(), show.author());
            System.out.printf(SHOW_VIDEO + "%n", show.videoID());
        }
    }

    /**
     * Handles the removal of a show from the platform.
     *
     * @param scanner the Scanner object for reading input
     * @param platform the StreamingPlatform instance to operate on
     */
    private static void deleteShow(Scanner scanner, StreamingPlatform platform) {
        String title = readLine(scanner);

        if (!platform.hasShow(title)) {
            System.out.println(SHOW_FALSE);
        } else {
            platform.removeShow(title);
            System.out.println(SHOW_REMOVED);
        }
    }

    /**
     * The main entry point of the application.
     * Sets up the locale, creates the platform instance, and starts the program loop.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        Locale.setDefault(Locale.of("EN", "GB"));
        StreamingPlatform platform = new StreamingPlatformImpl();
        Scanner scanner = new Scanner(System.in);
        runProgram(scanner, platform);
        scanner.close();
    }
}