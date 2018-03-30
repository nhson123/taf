package com.anecon.taf.client.white;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RemoteUiClientWrapper implements IRemoteUIClient {
    private static final Logger log = LoggerFactory.getLogger(RemoteUiClientWrapper.class);

    private IRemoteUIClient uiClientInstance;

    public RemoteUiClientWrapper(IRemoteUIClient uiClientInstance) {
        this.uiClientInstance = uiClientInstance;
    }

    @Override
    public void startApplication(ApplicationConfiguration config) {
        this.uiClientInstance.startApplication(config);
    }

    @Override
    public void stopApplication() {
        this.uiClientInstance.stopApplication();
    }

    @Override
    public void stopApplication(ApplicationConfiguration config) {
        this.uiClientInstance.stopApplication(config);
    }

    @Override
    public ApplicationConfiguration getRunningApplication() {
        return this.uiClientInstance.getRunningApplication();
    }

    @Override
    public void click(SearchProperties searchProperties) {
        try {
            log.trace("CLICK - SearchProperties: \"{}\"", searchProperties.toString());
            this.uiClientInstance.click(searchProperties);
        } catch (Exception ex) {
            throw this.failWithScreenshot(ex);
        }
    }

    @Override
    public void click(SearchProperties searchProperties, int times) {
        // Search properties are clones so that by setting the action count value the original search properties are not altered
        SearchProperties props = new SearchProperties(searchProperties);
        props.setActionCount(times);
        this.click(props);
    }

    @Override
    public void setText(SearchProperties searchProperties, String text) {
        try {
            if (text == null) {
                throw new IllegalArgumentException("Text cannot be null");
            }
            String base64Encoded = java.util.Base64.getEncoder().encodeToString(text.getBytes());
            log.trace("Base64 encoded value from \"{}\" is: \"{}\"", text, base64Encoded);
            log.trace("SETTEXT - SearchProperties: \"{}\"", searchProperties.toString());
            this.uiClientInstance.setText(searchProperties, base64Encoded);
        } catch (Exception ex) {
            throw this.failWithScreenshot(ex);
        }
    }

    @Override
    public void setText(SearchProperties searchProperties, String text, int times) {
        // Search properties are clones so that by setting the action count value the original search properties are not altered
        SearchProperties props = new SearchProperties(searchProperties);
        props.setActionCount(times);
        this.setText(props, text);
    }

    @Override
    public void focus(SearchProperties searchProperties) {
        try {
            log.trace("FOCUS - SearchProperties: \"{}\"", searchProperties.toString());
            this.uiClientInstance.focus(searchProperties);
        } catch (Exception ex) {
            throw this.failWithScreenshot(ex);
        }
    }

    @Override
    public String getText(SearchProperties searchProperties) {
        try {
            log.trace("GETTEXT - SearchProperties: \"{}\"", searchProperties.toString());
            return this.uiClientInstance.getText(searchProperties).replaceAll("\"", "");
        } catch (Exception ex) {
            throw this.failWithScreenshot(ex);
        }
    }

    @Override
    public int selectRows(SearchProperties searchProperties, String cellContent) {
        try {
            String base64Encoded = java.util.Base64.getEncoder().encodeToString(cellContent.getBytes());
            log.trace("Base64 encoded value from \"{}\" is: \"{}\"", cellContent, base64Encoded);
            log.trace("SELECTROWS - SearchProperties: \"{}\"", searchProperties.toString());
            return this.uiClientInstance.selectRows(searchProperties, base64Encoded);
        } catch (Exception ex) {
            throw this.failWithScreenshot(ex);
        }
    }

    @Override
    public boolean isVisible(SearchProperties searchProperties) {
        try {
            log.trace("ISVISIBLE - SearchProperties: \"{}\"", searchProperties.toString());
            return this.uiClientInstance.isVisible(searchProperties);
        } catch (Exception ex) {
            throw this.failWithScreenshot(ex);
        }
    }

    @Override
    public boolean isEnabled(SearchProperties searchProperties) {
        try {
            log.trace("ISENABLED - SearchProperties: \"{}\"", searchProperties.toString());
            return this.uiClientInstance.isEnabled(searchProperties);
        } catch (Exception ex) {
            throw this.failWithScreenshot(ex);
        }
    }

    @Override
    public boolean isChecked(SearchProperties searchProperties) {
        try {
            log.trace("ISCHECKED - SearchProperties: \"{}\"", searchProperties.toString());
            return this.uiClientInstance.isChecked(searchProperties);
        } catch (Exception ex) {
            throw this.failWithScreenshot(ex);
        }
    }

    @Override
    public String takeScreenshot(SearchProperties searchProperties) {
        log.trace("TAKESCREENSHOT - SearchProperties: \"{}\"", searchProperties == null ? "EMPTY" : searchProperties.toString());
        return this.uiClientInstance.takeScreenshot(searchProperties).replace("\\\\", "\\").replace("\"", "");
    }

    @Override
    public String getClipboardContent() {
        try {
            String rawText = this.uiClientInstance.getClipboardContent();
            rawText = rawText.replace("\\t", "\t").replace("\\r", "\r").replace("\\n", "\n").replace("\"", "");
            return rawText;
        } catch (Exception ex) {
            throw this.failWithScreenshot(ex);
        }
    }

    @Override
    public void setClipboardContent(String newContent) {
        try {
            String base64Encoded = java.util.Base64.getEncoder().encodeToString(newContent.getBytes("UTF-8"));
            log.trace("Base64 encoded value from \"{}\" is: \"{}\"", newContent, base64Encoded);
            this.uiClientInstance.setClipboardContent("=" + base64Encoded); // we have to prefix the value because C# WebAPI otherwise discards body content for URL-encoded HTTP Post messages
        } catch (Exception ex) {
            throw this.failWithScreenshot(ex);
        }
    }

    private IllegalStateException failWithScreenshot(Exception cause) {
        log.error("Error during RemoteUiClient interaction", cause);
        String path = this.takeScreenshot(null);
        return new IllegalStateException(
                String.format("Error during RemoteUiClient interaction:\n<br />%s\n<br/><img src=\"%s\"></img>", path, path),
                cause);

    }
}
