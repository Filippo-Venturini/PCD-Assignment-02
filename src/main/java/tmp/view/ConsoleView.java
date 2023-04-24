package tmp.view;

import tmp.controller.Controller;
import tmp.model.SetupInfo;
import utils.Strings;

import java.util.Scanner;

public class ConsoleView {

    private Controller controller;

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void start(){
        Scanner scanner = new Scanner(System.in);

        System.out.print("Root directory: ");
        final String dir = scanner.nextLine();

        String tmp;
        do{
            System.out.print("Number of files to visualize: ");
            tmp = scanner.nextLine();
        }while (!Strings.isNumeric(tmp) || Integer.parseInt(tmp) <= 0);
        final Integer nFiles = Integer.parseInt(tmp);

        do{
            System.out.print("Number of intervals: ");
            tmp = scanner.nextLine();
        }while (!Strings.isNumeric(tmp) || Integer.parseInt(tmp) <= 0);
        final Integer nIntervals = Integer.parseInt(tmp);

        do{
            System.out.print("Last interval max: ");
            tmp = scanner.nextLine();
        }while (!Strings.isNumeric(tmp) || Integer.parseInt(tmp) <= 0);
        final Integer lastInterval = Integer.parseInt(tmp);

        final SetupInfo setupInfo = new SetupInfo(dir, nFiles, nIntervals, lastInterval);

        this.controller.getReport(setupInfo).thenAccept(r -> {
            System.out.println(r.ranking());
            System.out.println(r.distribution());
        });
    }
}
