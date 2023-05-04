package virtual_threads;

import virtual_threads.controller.Controller;
import virtual_threads.model.Model;
import virtual_threads.view.ConsoleView;

public class VirtualThreadsCLIMain {
    public static void main(String[] args){
        Model model = new Model();
        Controller controller = new Controller(model);
        ConsoleView view = new ConsoleView();
        view.setController(controller);
        view.start();
    }
}
