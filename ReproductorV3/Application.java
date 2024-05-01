package ReproductorV3;

import java.io.InputStreamReader;

public class Application {
    
    public static void main(String[] args) {
        try {
            Model model = new Model();
            View view = new View(model);
            view.setupScreen();
            InputStreamReader in = new InputStreamReader(System.in);
            Controller controller = new Controller(in, model);
            controller.processInput();  // Bucle de entrada del teclado
        } catch (Exception e) {
            System.err.println("Error al iniciar la aplicación: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
