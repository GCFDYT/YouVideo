package Core;

import dataStructures.Iterator;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    // Command names:
    public static final String CREATE_PUBLISHABLE_VIDEO = "createpublishable";
    public static final String CREATE_PREMIUM_VIDEO = "createpremium";
    public static final String ADD_SUBTITLES_PREMIUM_VIDEO = "addsubtitle";
    public static final String DISPLAY_VIDEO_DATA = "getvideo";
    public static final String DISPLAY_VIDEO_SUBTITLES_LIST = "subtitles";
    public static final String DELETE_VIDEO = "removevideo";
    public static final String CREATE_PODCAST = "createpodcast";
    public static final String ADD_PODCAST_EPISODE = "addepisode";
    public static final String DISPLAY_PODCAST_DATA = "getpodcast";
    public static final String DISPLAY_PODCAST_EPISODE_LIST = "episodes";
    public static final String DISPLAY_AUTHOR_PODCAST_LIST = "authorpodcasts";
    public static final String DELETE_PODCAST = "removepodcast";
    public static final String CREATE_SHOW = "createshow";
    public static final String DISPLAY_SHOW_DATA = "getshow";
    public static final String DELETE_SHOW = "removeshow";
    public static final String DISPLAY_CMD_LIST = "help";
    public static final String EXIT_PROGRAM = "exit";

    // Output messages
    public static final String CMD_ERR = "Unknown command. Type help to see available commands.";
    public static final String EXIT_MSG = "Bye!";
    public static final String VIDEO_CREATED = "Video %s created successfully.";
    public static final String PREMIUM_VIDEO_CREATED = "PREMIUM Video %s created successfully.";
    public static final String VIDEO_DISPLAY = "Video %s %d Title: %s%n";
    public static final String PREMIUM_VIDEO_DISPLAY = "PREMIUM Video %s %d Title: %s%n";
    public static final String VIDEO_FILE_INFO = "File: %s Publisher: %s Language: %s%n";
    public static final String INV_LANGUAGE = "Invalid language type.";
    public static final String INV_VALUE = "Invalid value.";
    public static final String VIDEO_ID_EXISTS = "Video with this ID already exists.";
    public static final String INV_SUBTITLE = "Invalid language type in subtitle.";
    public static final String PUBLISHABLE_NOT_FOUND = "Publishable Video %s does not exist.";
    public static final String VIDEO_NOT_FOUND = "Video does not exist.";
    public static final String NOT_PREMIUM = "This operation requires a Premium video.";
    public static final String NO_PREMIUM_VIDEO = "No Premium Video with ID.";
    public static final String SUBTITLE_ADDED = "Subtitle added successfully.";
    public static final String SUBTITLES_HEADER = "Subtitles for video %s:";
    public static final String SUBTITLE_ENTRY = "- %s (%s)";
    public static final String VIDEO_REMOVED = "Video removed successfully.";
    public static final String CANT_REMOVE_EPISODE = "Cannot remove: video is an episode of a podcast.";
    public static final String CANT_REMOVE_USED_VIDEO = "Cannot remove: video is used in a show.";
    public static final String PODCAST_CREATED = "Podcast created successfully.";
    public static final String PODCAST_EXISTS = "Podcast with this title already exists.";
    public static final String PODCAST_NOT_FOUND = "Podcast does not exist.";
    public static final String PODCAST_INFO = "Podcast: %s Author: %s Language: %s";
    public static final String PODCAST_LATEST_EPISODE = "Latest episode date: %s";
    public static final String PODCAST_REMOVED = "Podcast removed successfully.";
    public static final String EPISODE_ADDED = "Episode added successfully.";
    public static final String EPISODE_ID_EXISTS = "Episode ID already exists in the system.";
    public static final String INV_EPISODE_DATE = "Episode date must be >= than latest episode date.";
    public static final String EPISODES_HEADER = "Episodes for podcast %s:";
    public static final String EPISODE_ENTRY = "Episode %s: %d min Date: %s";
    public static final String EPISODE_URL = "URL: %s";
    public static final String NO_EPISODES = "No episodes available for this podcast.";
    public static final String AUTHOR_PODCASTS_HEADER = "Podcasts by author %s:";
    public static final String PODCAST_ENTRY = "Podcast: %s Author: %s Language: %s";
    public static final String NO_USER_PODCASTS = "No podcasts found for this author.";
    public static final String SHOW_CREATED = "Show created successfully.";
    public static final String SHOW_NOT_FOUND = "Video for show does not exist.";
    public static final String SHOW_EXISTS = "Show with this title already exists.";
    public static final String SHOW_INFO = "Show Date: %s Author: %s";
    public static final String SHOW_VIDEO = "Video: %s";
    public static final String SHOW_FALSE = "Show does not exist.";
    public static final String SHOW_REMOVED = "Show removed successfully.";

    public static final String HELP_MSG =
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

    private static final int IDX_VIDEO_ID = 0;
    private static final int IDX_VIDEO_DURATION = 1;
    private static final int IDX_URL = 2;
    private static final int IDX_PUBLISHER = 3;
    private static final int IDX_TITLE = 4;
    private static final int IDX_LANGUAGE_CODE = 5;
    private static final int NUM_FIELDS = 6;

    private static String[] readCommonVideoFields(Scanner scanner) {
        String[] fields = new String[NUM_FIELDS];
        fields[IDX_VIDEO_ID] = scanner.next();
        fields[IDX_VIDEO_DURATION] = String.valueOf(scanner.nextInt());
        fields[IDX_URL] = scanner.next();
        consumeLine(scanner);
        fields[IDX_PUBLISHER] = scanner.nextLine();
        fields[IDX_TITLE] = scanner.nextLine();
        fields[IDX_LANGUAGE_CODE] = scanner.next();
        consumeLine(scanner);
        return fields;
    }

    private static void consumeLine(Scanner scanner) {
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
    }

    private static String readTokenAndConsumeLine(Scanner scanner) {
        String token = scanner.next();
        consumeLine(scanner);
        return token;
    }

    private static String readLine(Scanner scanner) {
        return scanner.nextLine();
    }

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
                default -> System.out.println(CMD_ERR);
            }
        } while (!EXIT_PROGRAM.equals(command));
    }

    private static void createPublishableVideo(Scanner scanner, StreamingPlatform platform) {
        String[] fields = readCommonVideoFields(scanner);
        String videoId = fields[IDX_VIDEO_ID];
        int duration = Integer.parseInt(fields[IDX_VIDEO_DURATION]);
        String language = fields[IDX_LANGUAGE_CODE];

        if (!platform.isValidLanguageCode(language)) {
            System.out.println(INV_LANGUAGE);
        } else if (duration <= 0) {
            System.out.println(INV_VALUE);
        } else if (platform.hasVideo(videoId)) {
            System.out.println(VIDEO_ID_EXISTS);
        } else {
            platform.addPublishableVideo(videoId, duration, fields[IDX_URL], fields[IDX_PUBLISHER], fields[IDX_TITLE], language);
            System.out.printf(VIDEO_CREATED + "%n", videoId);
        }
    }

    private static void createPremiumVideo(Scanner scanner, StreamingPlatform platform) {
        String[] fields = readCommonVideoFields(scanner);
        String subtitleUrl = readTokenAndConsumeLine(scanner);
        String subtitleLanguage = readTokenAndConsumeLine(scanner);

        String videoId = fields[IDX_VIDEO_ID];
        int duration = Integer.parseInt(fields[IDX_VIDEO_DURATION]);
        String language = fields[IDX_LANGUAGE_CODE];

        if (!platform.isValidLanguageCode(language)) {
            System.out.println(INV_LANGUAGE);
        } else if (!platform.isValidLanguageCode(subtitleLanguage)) {
            System.out.println(INV_SUBTITLE);
        } else if (duration <= 0) {
            System.out.println(INV_VALUE);
        } else if (platform.hasVideo(videoId)) {
            System.out.println(VIDEO_ID_EXISTS);
        } else {
            platform.addPremiumVideo(videoId, duration, fields[IDX_URL], fields[IDX_PUBLISHER], fields[IDX_TITLE], language, subtitleUrl, subtitleLanguage);
            System.out.printf(PREMIUM_VIDEO_CREATED + "%n", videoId);
        }
    }

    private static void createSubtitle(Scanner scanner, StreamingPlatform platform) {
        String videoID = readTokenAndConsumeLine(scanner);
        String subtitleUrl = readTokenAndConsumeLine(scanner);
        String subtitleLanguage = readTokenAndConsumeLine(scanner);

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

    private static void displayVideoData(Scanner scanner, StreamingPlatform platform) {
        String videoId = readTokenAndConsumeLine(scanner);

        if (!platform.hasPublishableVideo(videoId)) {
            System.out.printf(PUBLISHABLE_NOT_FOUND + "%n", videoId);
        } else {
            PublishableVideo video = platform.getVideo(videoId);
            Locale locale = Locale.of(video.getLanguageCode());

            if (video instanceof PremiumVideo) {
                System.out.printf(PREMIUM_VIDEO_DISPLAY, video.getVideoID(), video.getVideoDuration(), video.getTitle());
            } else {
                System.out.printf(VIDEO_DISPLAY, video.getVideoID(), video.getVideoDuration(), video.getTitle());
            }
            System.out.printf(VIDEO_FILE_INFO, video.getUrl(), video.getPublisher(), locale.getDisplayLanguage().toUpperCase());
        }
    }

    private static void displayVideoSubtitleList(Scanner scanner, StreamingPlatform platform) {
        String videoId = readTokenAndConsumeLine(scanner);

        if (!platform.isPremiumVideo(videoId)) {
            System.out.println(NO_PREMIUM_VIDEO);
        } else {
            PublishableVideo video = platform.getVideo(videoId);
            System.out.printf(SUBTITLES_HEADER + "%n", video.getTitle());

            Iterator<Subtitle> it = platform.getSubtitles(videoId);
            while (it.hasNext()) {
                Subtitle subtitle = it.next();
                Locale locale = Locale.of(subtitle.getSubtitleLanguage());
                System.out.printf(SUBTITLE_ENTRY + "%n", subtitle.getSubtitleUrl(), locale.getDisplayLanguage().toUpperCase());
            }
        }
    }

    private static void deleteVideo(Scanner scanner, StreamingPlatform platform) {
        String videoId = readTokenAndConsumeLine(scanner);

        if (!platform.hasVideo(videoId)) {
            System.out.println(VIDEO_NOT_FOUND);
        } else if (platform.hasPodcastEpisode(videoId)) {
            System.out.println(CANT_REMOVE_EPISODE);
        } else if (platform.isVideoUsedInShow(videoId)) {
            System.out.println(CANT_REMOVE_USED_VIDEO);
        } else {
            platform.removeVideo(videoId);
            System.out.println(VIDEO_REMOVED);
        }
    }

    private static void createPodcast(Scanner scanner, StreamingPlatform platform) {
        String title = readLine(scanner);
        String author = readLine(scanner);
        String language = readTokenAndConsumeLine(scanner);

        if (!platform.isValidLanguageCode(language)) {
            System.out.println(INV_LANGUAGE);
        } else if (platform.hasPodcast(title)) {
            System.out.println(PODCAST_EXISTS);
        } else {
            platform.addPodcast(title, author, language);
            System.out.println(PODCAST_CREATED);
        }
    }

    private static void createPodcastEpisode(Scanner scanner, StreamingPlatform platform) {
        String title = readLine(scanner);
        String videoId = readTokenAndConsumeLine(scanner);
        int duration = scanner.nextInt();
        String url = readTokenAndConsumeLine(scanner);
        String date = readTokenAndConsumeLine(scanner);

        if (duration <= 0) {
            System.out.println(INV_VALUE);
        } else if (!platform.hasPodcast(title)) {
            System.out.println(PODCAST_NOT_FOUND);
        } else if (platform.hasVideo(videoId)) {
            System.out.println(EPISODE_ID_EXISTS);
        } else if (!platform.isValidEpisodeDate(title, date)) {
            System.out.println(INV_EPISODE_DATE);
        } else {
            platform.addPodcastEpisode(title, videoId, duration, url, date);
            System.out.println(EPISODE_ADDED);
        }
    }

    private static void displayPodcastData(Scanner scanner, StreamingPlatform platform) {
        String title = readLine(scanner);

        if (!platform.hasPodcast(title)) {
            System.out.println(PODCAST_NOT_FOUND);
        } else {
            Podcast podcast = platform.getPodcast(title);
            Locale locale = Locale.of(podcast.getLanguageCode());
            System.out.printf(PODCAST_INFO + "%n", podcast.getTitle(), podcast.getAuthor(), locale.getDisplayLanguage().toUpperCase());

            if (podcast.hasEpisodes()) {
                System.out.printf(PODCAST_LATEST_EPISODE + "%n", podcast.getLatestEpisode().getDate());
            }
        }
    }

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
                    System.out.printf(EPISODE_ENTRY + "%n", ep.getVideoID(), ep.getVideoDuration(), ep.getDate());
                    System.out.printf(EPISODE_URL + "%n", ep.getUrl());
                }
            }
        }
    }

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
                System.out.printf(PODCAST_ENTRY + "%n", podcast.getTitle(), podcast.getAuthor(), locale.getDisplayLanguage().toUpperCase());
            }
        }
    }

    private static void deletePodcast(Scanner scanner, StreamingPlatform platform) {
        String title = readLine(scanner);

        if (!platform.hasPodcast(title)) {
            System.out.println(PODCAST_NOT_FOUND);
        } else {
            platform.removePodcast(title);
            System.out.println(PODCAST_REMOVED);
        }
    }

    private static void createShow(Scanner scanner, StreamingPlatform platform) {
        String author = readLine(scanner);
        String videoId = readTokenAndConsumeLine(scanner);
        String date = readTokenAndConsumeLine(scanner);

        if (!platform.hasPublishableVideo(videoId)) {
            System.out.println(SHOW_NOT_FOUND);
        } else {
            String videoTitle = platform.getVideo(videoId).getTitle();
            if (platform.hasShow(videoTitle)) {
                System.out.println(SHOW_EXISTS);
            } else {
                platform.addShow(author, videoId, date);
                System.out.println(SHOW_CREATED);
            }
        }
    }

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

    private static void deleteShow(Scanner scanner, StreamingPlatform platform) {
        String title = readLine(scanner);

        if (!platform.hasShow(title)) {
            System.out.println(SHOW_FALSE);
        } else {
            platform.removeShow(title);
            System.out.println(SHOW_REMOVED);
        }
    }

    public static void main(String[] args) {
        Locale.setDefault(Locale.of("EN", "GB"));
        StreamingPlatform platform = new StreamingPlatformImpl();
        Scanner scanner = new Scanner(System.in);
        runProgram(scanner, platform);
        scanner.close();
    }
}