package executors;

import executors.controller.Controller;
import executors.model.Model;
import executors.view.ConsoleView;
import executors.view.GUIView;

public class ExecutorsGUIMain {
    public static void main(String[] args){
        Model model = new Model();
        Controller controller = new Controller(model);
        GUIView view = new GUIView(controller);
    }
}
