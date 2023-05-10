package rx;

import rx.controller.Controller;
import rx.model.Model;
import rx.view.ConsoleView;

public class RxCLIMain {
    public static void main(String[] args){
        Model model = new Model();
        Controller controller = new Controller(model);
        ConsoleView consoleView = new ConsoleView();
        consoleView.setController(controller);
        consoleView.start();
    }
}
