import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class StorageManager {
    private static final Gson gson = new Gson();
    private static final String TASK_FILE = "tasks.json";

    public static void saveTasks(List<Task> tasks) {
        try (FileWriter writer = new FileWriter(TASK_FILE)) {
            gson.toJson(tasks, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Task> loadTasks() {
        if (!Files.exists(Paths.get(TASK_FILE))) return new ArrayList<>();

        try (FileReader reader = new FileReader(TASK_FILE)) {
            Type listType = new TypeToken<ArrayList<Task>>() {}.getType();
            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}