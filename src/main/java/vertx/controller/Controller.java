package vertx.controller;

import io.vertx.core.eventbus.EventBus;
import utils.SetupInfo;

public class Controller implements SourceAnalyzer{
    @Override
    public void getReport(SetupInfo setupInfo, EventBus eventBus) {
        eventBus.publish("scan", setupInfo);
    }
}
