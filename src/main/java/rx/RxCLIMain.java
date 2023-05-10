package rx;

import rx.controller.Controller;
import rx.view.ConsoleView;

public class RxCLIMain {
    public static void main(String[] args){
        Controller controller = new Controller();
        ConsoleView consoleView = new ConsoleView();
        consoleView.setController(controller);
        consoleView.start();
    }
}
