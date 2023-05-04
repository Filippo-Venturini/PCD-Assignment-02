package virtual_threads.view;

import virtual_threads.controller.Controller;
import utils.*;

import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class ConsoleView {
    private Controller controller;
    private Result finalReport;

    public void setController(Controller controller){
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

        try {
            finalReport = this.controller.getReport(setupInfo).get();

            System.out.println("Files ranking:");
            for(AnalyzedFile result : finalReport.getRanking()){
                System.out.println(result.path() + " has: " + result.lines() + " lines.");
            }
            System.out.println("\nFiles distribution:");
            for(Map.Entry<Interval, Integer> entry : finalReport.getDistribution().entrySet()){
                System.out.println(entry.getKey() + " : " + entry.getValue());
            }

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
