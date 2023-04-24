package executors;

import executors.controller.Controller;
import executors.view.ConsoleView;
import executors.model.Model;

public class ExecutorsMain {
    public static void main(String[] args){
        Model model = new Model();
        Controller controller = new Controller(model);
        ConsoleView view = new ConsoleView();
        view.setController(controller);

        view.start();
    }
}
