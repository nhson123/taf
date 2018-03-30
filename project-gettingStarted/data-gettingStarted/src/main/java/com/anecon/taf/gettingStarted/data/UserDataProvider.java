package com.anecon.taf.gettingStarted.data;

import com.anecon.taf.core.Cleanable;
import com.anecon.taf.core.dataprovider.ActionStack;
import com.anecon.taf.gettingStarted.model.User;
import com.anecon.taf.gettingStarted.rest.UserRestEspoKeywords;

public class UserDataProvider implements Cleanable {
    private final ActionStack undoStack = new ActionStack();
    private final UserRestEspoKeywords userKeywords;

    public UserDataProvider(UserRestEspoKeywords restEspoKeywords) {
        userKeywords = restEspoKeywords;
    }

    public User createUser() {
        final User user = userKeywords.createUser("username" + String.valueOf(randomNumber(4)),
                "password", "Mr.", "Vorname", "Nachname", false);
        undoStack.put(() -> userKeywords.deleteUser(user.getId()));

        return user;
    }

    public User createAdmin() {
        final User user = userKeywords.createUser("username" + String.valueOf(randomNumber(4)),
                "password", "Mr.", "Vorname", "Nachname", true);
        undoStack.put(() -> userKeywords.deleteUser(user.getId()));

        return user;
    }

    @Override
    public void cleanUp() {
        undoStack.executeAll();
    }

    private static int randomNumber(int maxDigits) {
        return (int) (Math.random() * (Math.pow(10, maxDigits) - 1));
    }
}
