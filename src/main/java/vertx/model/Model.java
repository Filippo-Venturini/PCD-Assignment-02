package vertx.model;

import utils.Result;

public class Model {
    private Result result;
    private String rootFolderAgentID;

    public void setResult(Result result){
        this.result = result;
    }

    public Result getResult(){
        return this.result;
    }

    public String getRootFolderAgentID() {
        return rootFolderAgentID;
    }

    public void setRootFolderAgentID(String rootFolderAgentID) {
        this.rootFolderAgentID = rootFolderAgentID;
    }
}
