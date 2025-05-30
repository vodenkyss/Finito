import GUI.TaskManagerGUI;

/**
 *  The main application class for launching the Finito task manager.
 */
public class App {

    private TaskManagerGUI gui;

    /**
     * Starts the application by logging the launch message, loading data, and initializing the GUI.
     * A shutdown hook is also added to save data when the application is closed.
     */
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
