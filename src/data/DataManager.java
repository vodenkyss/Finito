package data;


import model.TaskFolder;

import java.io.*;
import java.util.ArrayList;

    public class DataManager {

        private static  String file = "data.ser";

        public static void saveFolders(ArrayList<TaskFolder> folders) {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                oos.writeObject(folders);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public static ArrayList<TaskFolder> loadFolders() {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                return (ArrayList<TaskFolder>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                return new ArrayList<>();
            }
        }
    }


