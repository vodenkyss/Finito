package data;
import model.TaskFolder;
import java.io.*;
import java.util.ArrayList;

/**
 * Class for serializing the data
 */
    public class DataManager {

        public static  String file = "data.ser";

        /**
         * Saves the task folders to file
         * @param folders the list of objects to be saved
         */
        public static void saveFolders(ArrayList<TaskFolder> folders) {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                oos.writeObject(folders);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * Loads the task folders from file
         * @return
         */
        public static ArrayList<TaskFolder> loadFolders() {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                return (ArrayList<TaskFolder>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                return new ArrayList<>();
            }
        }

    }


