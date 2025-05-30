import GUI.TaskManagerGUI;

public class App {

    private TaskManagerGUI gui;

    public void start() {
        log("Launching Finito!...");
        loadData();
        gui = new TaskManagerGUI();
        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
    }

    private void loadData() {
        log("Loading data...");
    }

    private void shutdown() {
        log("Saving and shutting down the app...");
    }

    private void log(String message) {
        System.out.println("[APP] " + message);
    }
}
