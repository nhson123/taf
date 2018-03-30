package com.anecon.taf.client.meux;

import com.anecon.taf.client.ui.api.Element;
import com.anecon.taf.client.ui.api.Elements;
import com.anecon.taf.client.ui.api.UiClient;
import com.anecon.taf.client.ui.api.Window;
import com.anecon.taf.client.ui.api.table.Table;
import com.anecon.taf.core.AutomationFrameworkException;
import com.anecon.taf.core.Cleanable;
import com.anecon.taf.core.util.Wait;
import meuxplugin.meuxsystem.exceptions.MeuxException;
import meuxplugin.meuxsystem.exceptions.MeuxRunException;
import meuxplugin.meuxsystem.meuxdmrunnereclipse.MeuxDMRunnerEclipse;
import meuxplugin.meuxsystem.meuxdmrunnerinterface.IMeuxDMRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.SocketException;
import java.util.Arrays;

public class MeuxClient implements UiClient<MeuxDescription>, Cleanable {
    private static final Logger log = LoggerFactory.getLogger(MeuxClient.class);
    private static final int ACTION_DELAY_MS = MeuxConfig.get().actionDelayMs();
    private static final int FIND_TIMEOUT_MS = MeuxConfig.get().findTimeoutMs();

    private com.anecon.taf.client.meux.MeuxDevice meuxDevice;
    private IMeuxDMRunner meuxRunner = null;

    public MeuxClient(com.anecon.taf.client.meux.MeuxDevice meuxDevice) {
        this.meuxDevice = meuxDevice;
        initalize();
    }

    private boolean initalize() {
        if (meuxRunner != null) {
            quit();
        }
        meuxRunner = new MeuxDMRunnerEclipse();

        try {
            meuxRunner.beginRun();
        } catch (SocketException e) {
            throw new AutomationFrameworkException("Error while initializing meuxRunner", e);
        }

        return true;
    }

    @Override
    public void cleanUp() {
        quit();
    }

    private Object execute(String identifierXml, String command, String... args) {
        log.trace("Executing {} against element identified by {} with args {}", command, identifierXml, Arrays.toString(args));

        final long actionTimeout = System.currentTimeMillis() + ACTION_DELAY_MS;
        final long findTimeout = System.currentTimeMillis() + FIND_TIMEOUT_MS;

        Object objectId;
        do {
            objectId = findObjectId(identifierXml);
        } while (objectId == null && System.currentTimeMillis() < findTimeout);
        if (objectId == null) {
            throw new AutomationFrameworkException("Couldn't find element identified by \n" + identifierXml);
        }
        log.trace("Found object id: {}", objectId);
        Wait.till(actionTimeout);

        try {
            return meuxRunner.run(objectId, command, args);
        } catch (MeuxException e) {
            throw new AutomationFrameworkException("Couldn't execute command " + command + " with args "
                + Arrays.toString(args) + " for object " + objectId + " identified by " + identifierXml, e);
        }
    }

    private Object findObjectId(String identifierXml) {
        log.trace("Trying to find object identified by {}", identifierXml);
        return meuxRunner.findObjectId(identifierXml, null);
    }

    @Override
    public void quit() {
        meuxRunner.endRun();
        try {
            meuxRunner.disconnect();
        } catch (UnsatisfiedLinkError e) {
            log.warn("Error while disconnecting", e);
        }
    }

    @Override
    public Window window() {
        throw new UnsupportedOperationException("M-eux client isn't capable of this operation");
    }

    public MeuxDevice device() {
        return new MeuxDevice();
    }

    @Override
    public MeuxElement element(MeuxDescription locator) {
        return new MeuxElement(locator);
    }

    @Override
    public Elements<MeuxDescription> elements(MeuxDescription locator) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Table<MeuxDescription> table(MeuxDescription tableLocator, MeuxDescription rowLocator) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public class MeuxDevice {
        private MeuxDevice() {
            // instantiation only allowed for MeuxClient
        }

        public void launchAppCf35(String applicationPath, String workingDirectory, String appArguments) {
            execute(meuxDevice.toString(), "NetCFAppLaunch35", applicationPath, workingDirectory, appArguments);
        }
    }

    public class MeuxElement implements Element<MeuxDescription> {
        private final String identifierXml;

        private MeuxElement(MeuxDescription locator) {
            identifierXml = locator.toString();
        }

        @Override
        public boolean isSelected() {
            throw new UnsupportedOperationException("Not yet implemented");
        }

        @Override
        public String getAttribute(String attributeName) {
            throw new UnsupportedOperationException("Not yet implemented");
        }

        @Override
        public String getText() {
            log.debug("Retrieving text from element identified by {}", identifierXml);
            try {
                return (String) (meuxRunner.getProperties(findObjectId(identifierXml), new String[]{"text"})[0]);
            } catch (MeuxException | IndexOutOfBoundsException e) {
                throw new AutomationFrameworkException("Can't get text from object identified by " + identifierXml, e);
            }
        }

        @Override
        public String getElementType() {
            throw new UnsupportedOperationException("Not yet implemented");
        }

        @Override
        public boolean isEnabled() {
            throw new UnsupportedOperationException("Not yet implemented");
        }

        @Override
        public boolean isDisplayed() {
            log.debug("Checking if element is displayed, identified by {}", identifierXml);

            try {
                return findObjectId(identifierXml) != null;
            } catch (Exception e) {
                return false;
            }
        }

        @Override
        public void click() {
            // could be "Press" or "Tap" as well...
            log.debug("Clicking element identified by {}", identifierXml);
            execute("Push");
        }

        @Override
        public void clear() {
            log.debug("Clearing element identified by {}", identifierXml);
            sendKeys("");
        }

        @Override
        public void sendKeys(CharSequence... keys) {
            final String typeText = keysToString(keys);
            log.debug("Sending keys {} to element identified by {}", typeText, identifierXml);

            execute("Type", typeText);
        }

        public String getNetCFROProperty(String property) {
            log.debug("getting Property  {} of element identified by {}", property, identifierXml);

            return execute("GetNetCFROProperty", property).toString();
        }

        public String getROProperty(String property) {
            log.debug("getting Property  {} of element identified by {}", property, identifierXml);
            try {
                return (String) (meuxRunner.getProperties(findObjectId(identifierXml), new String[]{property})[0]);
            } catch (MeuxException | IndexOutOfBoundsException e) {
                throw new AutomationFrameworkException("Can't get text from object identified by " + identifierXml, e);
            }
        }

        public void pressKeys(CharSequence... keys) {
            final String typeText = keysToString(keys);
            log.debug("Pressing keys {} for element identified by {}", typeText, identifierXml);

            execute("Press", typeText);
        }

        private String keysToString(CharSequence... keys) {
            final StringBuilder sb = new StringBuilder();
            Arrays.stream(keys).forEach(sb::append);
            return sb.toString();
        }

        @Override
        public File takeScreenshot() {
            throw new UnsupportedOperationException("Not yet implemented");
        }

        @Override
        public void selectDropdown(String visibleText) {
            log.debug("Selecting dropdown {} of element identified by {}", visibleText, identifierXml);
            execute("Select", visibleText);
        }

        @Override
        public MeuxElement getChild(MeuxDescription locator) {
            throw new UnsupportedOperationException("Not yet implemented");
        }

        public void clickBaseButtons() {
            log.debug("Clicking base buttons");
            execute("Tap", "3", "3");
        }

        public File bitmapSave(File file) {
            log.debug("Taking screenshot");
            try {
                meuxRunner.bitmapSave(findObjectId(identifierXml).toString(), file.getAbsolutePath().replace('/', '\\'));
                return file;
            } catch (MeuxRunException ex) {
                throw new AutomationFrameworkException("Couldn't save bitmap");
            }
        }

        public File bitmapSave() {
            try {
                return bitmapSave(File.createTempFile("meux-screenshot", ".png"));
            } catch (IOException e) {
                throw new AutomationFrameworkException("Can't create temporary file", e);
            }
        }


        public void kill() {
            log.debug("Killing the application to which the form belongs");
            execute("Kill");
        }

        private Object execute(String command, String... args) {
            return MeuxClient.this.execute(identifierXml, command, args);
        }
    }
}
