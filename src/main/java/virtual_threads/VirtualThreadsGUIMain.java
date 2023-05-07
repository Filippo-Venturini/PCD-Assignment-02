package virtual_threads;

import virtual_threads.controller.Controller;
import virtual_threads.model.Model;
import virtual_threads.view.GUIView;

public class VirtualThreadsGUIMain {
    public static void main(String[] args){
        Model model = new Model();
        Controller controller = new Controller(model);
        GUIView view = new GUIView(controller);
    }
}
