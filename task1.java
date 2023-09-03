import java.io.*;
import java.util.*;

// This is the Anime class. It represents an anime with a name and a rating.
class Anime {
    String name;
    float rating;

    // constructor.
    public Anime(String name, float rating) {
        this.name = name;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public float getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return "Anime{" +
                "name='" + name + '\'' +
                ", rating=" + rating +
                '}';
    }
}

// This is the AnimeCache class. It stores the most recently accessed anime in a cache.
class AnimeCache {
    private int capacity;
    private Map<String, Anime> cache;
    private LinkedList<String> lru;

    
    public AnimeCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>();
        this.lru = new LinkedList<>();
    }

    // This method adds a new anime to the cache. If the cache is full, it removes the least recently used anime to make space.
    public void put(String name, Anime anime) {
        if (cache.size() == capacity) {
            String lruKey = lru.removeFirst();
            cache.remove(lruKey);
        }
        cache.put(name, anime);
        lru.addLast(name);
    }

    // This method gets an anime from the cache. If the anime is not in the cache, it returns null.
    public Anime get(String name) {
        if (cache.containsKey(name)) {
            lru.remove(name);
            lru.addLast(name);
            return cache.get(name);
        }
        return null;
    }
}

// This is the main class.
public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to this basic anime reader program. Designed with a lot of hate to JAVA!! ");
        String filename = "C:\\Users\\Haitham\\Desktop\\sample-input.txt";
        String logFilename = "C:\\Users\\Haitham\\Desktop\\LOG.txt";
        BufferedWriter logWriter = new BufferedWriter(new FileWriter(logFilename));
        RandomAccessFile file = new RandomAccessFile("C:\\Users\\Haitham\\Desktop\\sample-input.txt", "rw");
        Map<String, Float> animeRatings = new HashMap<>();
        AnimeCache cache = new AnimeCache(10);

        // This block of code reads the anime.txt file and stores the anime names and ratings in a HashMap.

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length < 2) {
                    System.out.println("Invalid line: " + line);
                    logWriter.write("Invalid line: " + line + "\n");
                    continue;
                }
                String name = String.join(" ", Arrays.copyOfRange(parts, 0, parts.length - 1));
                try {
                    float rating = Float.parseFloat(parts[parts.length - 1]);
                    animeRatings.put(name.toLowerCase(), rating);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid rating for anime " + name + ": " + parts[parts.length - 1]);
                    logWriter.write("Invalid rating for anime " + name + ": " + parts[parts.length - 1] + "\n");
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found: " + filename);
            logWriter.write("Error: File not found: " + filename + "\n");
            logWriter.close();
            return;
        } catch (IOException e) {
            System.out.println("Error: Could not read file: " + filename);
            logWriter.write("Error: Could not read file: " + filename + "\n");
            logWriter.close();
            return;
        }

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter a number or an anime name: ");
            String input = scanner.nextLine();
            logWriter.write("User input: " + input + "\n");

            if (input.equalsIgnoreCase("man of a culture")) {
                System.out.println("Program stopped. GoodBye!!");
                logWriter.write("Program stopped by user\n");
                break;
            }

            try {
                float ratingThreshold = Float.parseFloat(input);
                if (ratingThreshold < 0 || ratingThreshold > 10) {
                    System.out.println("Invalid input. Rating must be between 0 and 10.");
                    logWriter.write("Invalid input. Rating must be between 0 and 10.\n");
                    continue;
                }
                System.out.println("Anime with rating of " + ratingThreshold + " or higher:");
                for (Map.Entry<String, Float> entry : animeRatings.entrySet()) {
                    if (entry.getValue() >= ratingThreshold) {
                        System.out.println(entry.getKey() + " " + entry.getValue());
                        logWriter.write("Program output: "+entry.getKey() + " " + entry.getValue() + "\n");
                    }
                }
            } catch (NumberFormatException e) {
                String animeName = input.toLowerCase();
                Anime anime = cache.get(animeName);
                if (anime != null) {
                    System.out.println(anime.name + " " + anime.rating);
                    logWriter.write("Program output: " + anime.name + " " + anime.rating + "\n");
                    continue;
                }

                if (animeRatings.containsKey(animeName)) {
                    float rating = animeRatings.get(animeName);
                    System.out.println(input + " " + rating);
                    logWriter.write("Program output: " + input + " " + rating + "\n");
                    cache.put(animeName, new Anime(input, rating));
                } else {
                    System.out.println("Anime not found.");
                    logWriter.write("Anime not found.\n");
                    while (true) {
                        System.out.print("Enter a rating for this anime: ");
                        logWriter.write("Enter a rating for this anime: \n");

                        float rating;
                        try {
                            rating = scanner.nextFloat();
                            scanner.nextLine();
                            if (rating < 0 || rating > 10) {
                                System.out.println("Invalid input. Rating must be between 0 and 10.");
                                logWriter.write("Invalid input. Rating must be between 0 and 10.\n");
                                continue;
                            }
                        } catch (InputMismatchException k) {
                            System.out.println("Invalid input. Rating must be a number between 0 and 10.");
                            logWriter.write("Invalid input. Rating must be between 0 and 10.\n");
                            scanner.nextLine();
                            continue;
                        }

                        animeRatings.put(animeName, rating);
                        cache.put(animeName, new Anime(input, rating));
                        System.out.println("Added new anime with name " + input + " and rating " + rating);
                        logWriter.write("Program output: Added new anime with name " + input + " and rating " + rating + "\n");
                        file.seek(file.length());
                        file.writeUTF("\n"+animeName + " " + rating);
                        file.seek(0);
                        break;
                    }
                }
            }
        }

        logWriter.close();
    }
}
