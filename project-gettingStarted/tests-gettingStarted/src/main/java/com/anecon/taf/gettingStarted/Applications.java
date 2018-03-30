package com.anecon.taf.gettingStarted;

import com.anecon.taf.core.InstanceManager;

public class Applications extends InstanceManager {

    public EspoKeywords espo() {
        return getInstance(EspoKeywords.class);
    }
}
