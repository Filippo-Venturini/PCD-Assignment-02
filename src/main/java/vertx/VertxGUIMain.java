package vertx;

import io.vertx.core.Vertx;
import utils.SetupInfo;
import vertx.controller.Controller;
import vertx.model.Model;
import vertx.view.ConsoleAgent;
import vertx.view.GUIAgent;

public class VertxGUIMain {

    public static void main(String[] args){
        Vertx vertx = Vertx.vertx();
        Model model = new Model();
        Controller controller = new Controller(model);

        vertx.deployVerticle(new GUIAgent(controller));
    }
}
