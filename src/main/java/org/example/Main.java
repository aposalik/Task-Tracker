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
import java.util.Scanner;


public class Main {
    private static final String FILE_PATH = "tasks.json";
    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Please Enter the Command: ");
            System.out.println(" add , list ,  delete ");
            System.out.print(":> ");
            String command = scanner.nextLine();

            switch (command) {
                case "add":
//
                    System.out.println("Add Description :> ");
                    String description = scanner.nextLine();

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
                
                case "delete":
                try {
                    System.out.println("Please enter the ID of the task you want to delete: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    
                    // Load existing tasks
                    List<Task> tasksToDelete = loadTasksFromFile();
                    
                    // Find and remove the task with the given ID
                    boolean removed = false;
                    for (int i = 0; i < tasksToDelete.size(); i++) {
                        if (tasksToDelete.get(i).getId() == id) {
                            tasksToDelete.remove(i);
                            removed = true;
                            break;
                        }
                    }
                    
                    if (removed) {
                        // Save updated list
                        saveTasksToFile(tasksToDelete);
                        System.out.println("Task " + id + " deleted successfully.");
                    } else {
                        System.out.println("Task with ID " + id + " not found.");
                    }
                    
                } catch (NumberFormatException e) {
                    System.out.println("Error: Task ID must be a number.");
                }
                break;

                case "update":
                    System.out.println("Write the Id of todo :> ");
                    int idtoUpdate = Integer.parseInt(scanner.nextLine());

                    List<Task> tasksToUpdate = loadTasksFromFile();
                    boolean updated = false;
                    for (int i = 0; i < tasksToUpdate.size(); i++) {
                        if (tasksToUpdate.get(i).getId() == idtoUpdate) {
                            System.out.println("Write the Description of the task :> ");
                            String desforUpdate = scanner.nextLine();
                            tasksToUpdate.get(i).setDescription(desforUpdate);
                            updated = true;
                            break;
                        }
                    }
                    if (updated) {
                        saveTasksToFile(tasksToUpdate);
                        System.out.println("Task " + idtoUpdate + " updated successfully.");
                    }else {
                        System.out.println("Task with ID " + idtoUpdate + " not found.");
                    }
                    break;

                case "mark":
                    System.out.println("Please enter the ID of the task you want to mark :> ");
                    int idofMark = Integer.parseInt(scanner.nextLine());
                    List<Task> tasksToMark = loadTasksFromFile();
                    boolean marked = false;
                    for (int i = 0; i < tasksToMark.size(); i++) {
                        if (tasksToMark.get(i).getId() == idofMark) {
                            System.out.println("Change the Status of the task :> ");
                            String desforMark = scanner.nextLine();
                            tasksToMark.get(i).setStatus(desforMark);
                            marked = true;
                            break;
                        }
                    }
                    if (marked) {
                        saveTasksToFile(tasksToMark);
                        System.out.println("Task " + idofMark + " marked successfully.");
                    }else {
                        System.out.println("Task with ID " + idofMark + " not found.");
                    }
                    break;

                
            default:
                System.out.println("Unknown command: " + command);
        }

            System.out.print("Would you like to continue? [y/n]");
            String answer = scanner.nextLine();
            if (answer.toLowerCase().equals("n")) {
                break;
            }
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
