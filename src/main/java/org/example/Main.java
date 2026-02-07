package org.example;
import org.example.Task;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.io.*;
import java.util.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.time.LocalDateTime;


public class Main {
    private static final String FILE_PATH = "tasks.json";
    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: task-cli <command> [arguments]");
            return;
        }

        String command = args[0];
        switch (command) {
            case "add":
                if (args.length < 2) {
                    System.out.println("Usage: task-cli add <description>");
                    return;
                }
                String description = args[1];
                
                // Load existing tasks
                List<Task> tasks = loadTasksFromFile();
                
                // Generate new ID
                int newId = generateId(tasks);
                
                // Create and add new task
                Task newTask = new Task(newId, description);
                tasks.add(newTask);
                
                // Save back to JSON
                saveTasksToFile(tasks);
                
                System.out.println("Task added successfully (ID: " + newId + ")");
                break;
                
            case "list":
                List<Task> allTasks = loadTasksFromFile();
                if (allTasks.isEmpty()) {
                    System.out.println("No tasks found.");
                } else {
                    for (Task task : allTasks) {
                        System.out.println(task);
                    }
                }
                break;
                
            default:
                System.out.println("Unknown command: " + command);
        }
    }

    // FIXED: Changed to FileReader and returns List<Task>
    private static List<Task> loadTasksFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }

        try (FileReader reader = new FileReader(FILE_PATH)) {
            Type listType = new TypeToken<ArrayList<Task>>(){}.getType();
            List<Task> loadedTasks = gson.fromJson(reader, listType);
            
            return loadedTasks != null ? loadedTasks : new ArrayList<>();

        } catch (FileNotFoundException e) {
            System.out.println("No existing tasks file found. Starting fresh.");
            return new ArrayList<>();
        } catch (com.google.gson.JsonSyntaxException e) {
            System.out.println("Corrupted tasks file. Starting fresh.");
            return new ArrayList<>();
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private static void saveTasksToFile(List<Task> tasks) {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(tasks, writer);
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    // Helper method to generate new ID
    private static int generateId(List<Task> tasks) {
        if (tasks.isEmpty()) {
            return 1;
        }
        return tasks.stream()
                .mapToInt(Task::getId)
                .max()
                .orElse(0) + 1;
    }
}
