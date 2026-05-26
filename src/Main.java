import java.util.Iterator;
import java.util.Locale;
import java.util.Scanner;

/**
 * Main class for the You Video streaming platform application.
 * Provides a command-line interface for interacting with the streaming platform.
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
    
    // Phase 2 Commands
    private static final String AUTHORS_PRODUCTIVITY = "authorsproductivity";
    private static final String ADD_TAG = "addtag";
    private static final String REMOVE_TAG = "removetag";
    private static final String TAGGED = "tagged";
    
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
    private static final String SUBTITLE_ADDED = "Subtitle added successfully.";
    private static final String SUBTITLES_HEADER = "Subtitles for video %s:";
    private static final String SUBTITLE_ENTRY = "- %s (%s)";
    private static final String VIDEO_REMOVED = "Video removed successfully.";
    private static final String CANT_REMOVE_EPISODE = "Cannot remove: video is an episode of a podcast.";
    private static final String CANT_REMOVE_USED_VIDEO = "Cannot remove: video is used in a show.";
    
    private static final String PODCAST_CREATED = "Podcast created successfully.";
    private static final String PODCAST_EXISTS = "Podcast with this title already exists.";
    private static final String PODCAST_NOT_FOUND = "Podcast does not exist.";
    private static final String PODCAST_INFO = "Podcast: %s Author: %s Language: %s";
    private static final String PODCAST_LATEST_EPISODE = "Latest episode date: %s";
    private static final String PODCAST_REMOVED = "Podcast removed successfully.";
    private static final String EPISODE_ADDED = "Episode added successfully.";
    private static final String EPISODE_ID_EXISTS = "Episode ID already exists in the system.";
    private static final String INV_EPISODE_DATE = "Episode date must be >= than latest episode date.";
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
    
    private static final String TAG_ADDED = "Tag added successfully.";
    private static final String TAG_REMOVED = "Tag removed successfully.";
    private static final String TITLE_NOT_FOUND = "Title does not exist.";
    private static final String ALREADY_TAGGED = "Title is already tagged with %s.%n";
    private static final String NOT_TAGGED = "Title is not tagged with %s.%n";
    private static final String INVALID_TAG_PARAMS = "Invalid tagged parameters.";
    private static final String NO_TAGGED_CONTENT = "No content tagged with %s.%n";
    private static final String TAGS_HEADER = "Tags:";

    // --- Field Indexes for Common Video Fields ---
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
        fields[IDX_PUBLISHER] = readLine(scanner);
        fields[IDX_TITLE] = readLine(scanner);
        fields[IDX_LANGUAGE_CODE] = scanner.next();
        consumeLine(scanner);
        return fields;
    }

    private static void consumeLine(Scanner scanner) {
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
    }

    private static String readLine(Scanner scanner) {
        String line = scanner.nextLine();
        return line != null ? line.trim() : "";
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
                
                // Phase 2 Handlers
                case AUTHORS_PRODUCTIVITY -> displayAuthorsProductivity(platform);
                case ADD_TAG -> addTag(scanner, platform);
                case REMOVE_TAG -> removeTag(scanner, platform);
                case TAGGED -> displayTaggedContent(scanner, platform);
                
                case DISPLAY_CMD_LIST -> System.out.println("Available commands..."); // Keep your help msg here
                case EXIT_PROGRAM -> System.out.println(EXIT_MSG);
                default -> System.out.println(CMD_ERR);
            }
        } while (!EXIT_PROGRAM.equalsIgnoreCase(command));
    }

    // --- Phase 1 Methods ---
    
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

    private static void displayVideoData(Scanner scanner, StreamingPlatform platform) {
        String videoID = scanner.next();
        consumeLine(scanner);

        if (!platform.hasPublishableVideo(videoID)) {
            System.out.printf(PUBLISHABLE_NOT_FOUND + "%n", videoID);
        } else {
            PublishableVideo video = platform.getVideo(videoID);
            if (video instanceof PremiumVideo) {
                System.out.printf(PREMIUM_VIDEO_DISPLAY, video.getVideoID(),
                        video.getVideoDuration(), video.getTitle());
            } else {
                System.out.printf(VIDEO_DISPLAY, video.getVideoID(),
                        video.getVideoDuration(), video.getTitle());
            }
            Locale locale = Locale.of(video.getLanguageCode());
            System.out.printf(VIDEO_FILE_INFO, video.getUrl(), video.getPublisher(),
                    locale.getDisplayLanguage(Locale.ENGLISH).toUpperCase());
        }
    }

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

    private static void displayPodcastData(Scanner scanner, StreamingPlatform platform) {
        String title = readLine(scanner);

        if (!platform.hasPodcast(title)) {
            System.out.println(PODCAST_NOT_FOUND);
        } else {
            Podcast podcast = platform.getPodcast(title);
            System.out.printf(PODCAST_INFO + "%n", podcast.getTitle(),
                    podcast.getAuthor(), podcast.getLanguageCode().toUpperCase());
            
            if (podcast.hasEpisodes()) {
                System.out.printf(PODCAST_LATEST_EPISODE + "%n", podcast.getLatestEpisode().getDate());
            }
            
            // Phase 2 Update: Print Tags
            AbstractTaggableContent taggable = (AbstractTaggableContent) podcast;
            if (taggable.hasTags()) {
                System.out.println(TAGS_HEADER);
                Iterator<String> tagsIt = taggable.getTags();
                while(tagsIt.hasNext()) {
                    System.out.println(tagsIt.next());
                }
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
                    System.out.printf(EPISODE_ENTRY + "%n", ep.getVideoID(),
                            ep.getVideoDuration(), ep.getDate());
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

            Iterator<Podcast> iterator = platform.getAuthorPodcasts(author);
            while (iterator.hasNext()) {
                Podcast podcast = iterator.next();
                System.out.printf(PODCAST_ENTRY + "%n", podcast.getTitle(),
                        podcast.getAuthor(), podcast.getLanguageCode().toUpperCase());
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

    private static void displayShowData(Scanner scanner, StreamingPlatform platform) {
        String title = readLine(scanner);

        if (!platform.hasShow(title)) {
            System.out.println(SHOW_FALSE);
        } else {
            Show show = platform.getShow(title);
            System.out.printf(SHOW_INFO + "%n", show.getDate(), show.getAuthor()); // Assuming getter changes made
            PublishableVideo video = platform.getVideo(show.getVideoID());
            System.out.printf(SHOW_VIDEO + "%n", video.getTitle());
            
            // Phase 2 Update: Print Tags
            AbstractTaggableContent taggable = (AbstractTaggableContent) show;
            if (taggable.hasTags()) {
                System.out.println(TAGS_HEADER);
                Iterator<String> tagsIt = taggable.getTags();
                while(tagsIt.hasNext()) {
                    System.out.println(tagsIt.next());
                }
            }
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

    // --- Phase 2 New Methods ---

    private static void displayAuthorsProductivity(StreamingPlatform platform) {
        if (!platform.hasProductiveAuthors()) {
            System.out.println("No productive authors.");
            return;
        }
        
        System.out.println("Authors productivity:");
        Iterator<Author> it = platform.getAuthorsByProductivity();
        while (it.hasNext()) {
            Author author = it.next();
            System.out.printf("%s with %d contributions.%n", 
                author.getName(), author.getProductivity());
        }
    }

    private static void addTag(Scanner scanner, StreamingPlatform platform) {
        String title = readLine(scanner);
        String tag = readLine(scanner);

        if (!platform.hasPodcast(title) && !platform.hasShow(title)) {
            System.out.println(TITLE_NOT_FOUND);
        } else if (platform.isTitleTaggedWith(title, tag)) {
            System.out.printf(ALREADY_TAGGED, tag);
        } else {
            platform.addTagToTitle(title, tag);
            System.out.println(TAG_ADDED);
        }
    }

    private static void removeTag(Scanner scanner, StreamingPlatform platform) {
        String title = readLine(scanner);
        String tag = readLine(scanner);

        if (!platform.hasPodcast(title) && !platform.hasShow(title)) {
            System.out.println(TITLE_NOT_FOUND);
        } else if (!platform.isTitleTaggedWith(title, tag)) {
            System.out.printf(NOT_TAGGED, tag);
        } else {
            platform.removeTagFromTitle(title, tag);
            System.out.println(TAG_REMOVED);
        }
    }

    private static void displayTaggedContent(Scanner scanner, StreamingPlatform platform) {
        String tag = scanner.next();
        String filter = scanner.next().toUpperCase(); 
        String order = scanner.next().toUpperCase(); 
        consumeLine(scanner);

        boolean validFilter = filter.equals("ALL") || filter.equals("SHOW") || filter.equals("PODCAST");
        boolean validOrder = order.equals("ASC") || order.equals("DES");

        if (!validFilter || !validOrder) {
            System.out.println(INVALID_TAG_PARAMS);
            return;
        }

        Iterator<AbstractTaggableContent> it = platform.getTaggedContent(tag, filter, order);
        
        if (!it.hasNext()) {
            System.out.printf(NO_TAGGED_CONTENT, tag);
            return;
        }

        System.out.printf("Content tagged with %s in %s order:%n", 
            tag, order.equals("ASC") ? "Ascending" : "Descending");
        
        while (it.hasNext()) {
            AbstractTaggableContent content = it.next();
            String type = content instanceof Show ? "Show" : "Podcast";
            System.out.printf("%s Title: %s Author: %s%n", type, content.getTitle(), content.getAuthor());
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