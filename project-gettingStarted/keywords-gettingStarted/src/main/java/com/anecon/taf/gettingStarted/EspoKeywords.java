package com.anecon.taf.gettingStarted;

import com.anecon.taf.core.InstanceManager;
import com.anecon.taf.gettingStarted.rest.RestEspoKeywords;
import com.anecon.taf.gettingStarted.ui.UiEspoKeywords;

public class EspoKeywords extends InstanceManager {

    public RestEspoKeywords rest() {
        return getInstance(RestEspoKeywords.class);
    }

    public UiEspoKeywords ui() {
        return getInstance(UiEspoKeywords.class);
    }
}
