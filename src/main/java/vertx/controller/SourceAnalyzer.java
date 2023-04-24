package vertx.controller;

import io.vertx.core.eventbus.EventBus;
import utils.SetupInfo;

public interface SourceAnalyzer {
    void getReport(SetupInfo setupInfo, EventBus eventBus);
}
