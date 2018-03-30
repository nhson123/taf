package com.anecon.taf.gettingStarted;

import com.anecon.taf.core.InstanceManager;
import com.anecon.taf.gettingStarted.data.EspoDataProvider;

public class DataProvider extends InstanceManager {
    private final Applications apps;

    public DataProvider(Applications apps) {
        this.apps = apps;
    }

    public EspoDataProvider espo() {
        return getInstance(EspoDataProvider.class, apps.espo());
    }
}
