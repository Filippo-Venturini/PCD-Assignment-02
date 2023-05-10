package rx;

import rx.model.Model;
import rx.view.GUIView;
import rx.controller.Controller;

public class RxGUIView {
    public static void main(String[] args){
        Model model = new Model();
        Controller controller = new Controller(model);
        GUIView view = new GUIView(controller);
    }
}
