package com.anecon.taf.gettingStarted.data;

import com.anecon.taf.core.InstanceManager;
import com.anecon.taf.gettingStarted.EspoKeywords;

public class EspoDataProvider extends InstanceManager {
    private EspoKeywords espoKeywords;

    public EspoDataProvider(EspoKeywords espoKeywords) {
        this.espoKeywords = espoKeywords;
    }

    public UserDataProvider user() {
        return getInstance(UserDataProvider.class, espoKeywords.rest().users());
    }

    public AccountDataProvider account() {
        return getInstance(AccountDataProvider.class, espoKeywords.rest().accounts());
    }
}
