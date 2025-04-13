import GUI.TaskManagerGUI;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        TaskManagerGUI taskManagerGUI = new TaskManagerGUI();
        taskManagerGUI.initialize();
    }
}