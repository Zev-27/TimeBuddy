import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class PageStorageManager {
    private static final Path FILE = Paths.get("pages.json");
    private static final Gson gson = new Gson();

    public static List<Page> load() {
        if (!Files.exists(FILE)) return new ArrayList<>();
        try (Reader r = Files.newBufferedReader(FILE)) {
            Type t = new TypeToken<List<Page>>(){}.getType();
            return gson.fromJson(r, t);
        } catch (IOException e) { e.printStackTrace(); return new ArrayList<>(); }
    }
    public static void save(List<Page> pages) {
        try (Writer w = Files.newBufferedWriter(FILE)) {
            gson.toJson(pages, w);
        } catch (IOException e) { e.printStackTrace(); }
    }
}
