package virtual_threads.model;

import utils.Result;
import utils.SetupInfo;

public class Model {
    private Result result;

    public void init(SetupInfo setupInfo){
        this.result = new Result(setupInfo.nIntervals(), setupInfo.lastIntervalLowerBound(), setupInfo.nFiles());
    }

    public Result getResult(){
        return this.result;
    }
}
