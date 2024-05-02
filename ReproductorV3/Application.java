package ReproductorV3;

import java.io.InputStreamReader;

public class Application {
    
    public static void main(String[] args) {
        try {
            Model model = new Model();
            InputStreamReader in = new InputStreamReader(System.in);
            Controller controller = new Controller(in, model);
            View view = new View(model);
            //view.setupScreen();
            controller.processInput();  // Bucle de entrada del teclado
        } catch (Exception e) {
            System.err.println("Error al iniciar la aplicación: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
