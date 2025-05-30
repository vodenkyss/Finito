
public class Main {
    public static void main(String[] args) {
        try {
            App app= new App();
            app.start();
        }catch (Exception e){
            System.out.println("Error while launching the app:");
            e.printStackTrace();
        }

    }
}