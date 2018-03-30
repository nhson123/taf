package com.anecon.taf.client.ranorex;

import com.anecon.taf.core.AutomationFrameworkException;
import com.anecon.taf.core.Cleanable;
import com.anecon.taf.core.EventBusHolder;
import com.anecon.taf.core.util.SleepReasons;
import com.anecon.taf.core.util.Sleeper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Socket;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RanorexClient implements Cleanable {
    private static final Logger log = LoggerFactory.getLogger(RanorexClient.class);
    private static final RanorexConfig.RanorexConfigSpec config = RanorexConfig.get();

    private final Gson gson = new GsonBuilder().serializeNulls().create();
    private ZMQ.Context context;
    private Socket requester;
    private ZMQ.Poller item;
    private int actionCounter = 0;

    public RanorexClient() {
        startRanorex();
        connectToClient();
        EventBusHolder.register(this);
    }

    private void disconnectClient() {
        item.unregister(requester);
        requester.disconnect("tcp://localhost:" + config.port());
        requester.close();
        context.term();
    }

    private void connectToClient() {
        context = ZMQ.context(1);
        item = new ZMQ.Poller(1);
        requester = context.socket(ZMQ.REQ);
        requester.connect("tcp://localhost:" + config.port());
        item.register(requester, ZMQ.Poller.POLLIN);
    }

    private void startRanorex() {
        try {
            Runtime.getRuntime().exec("taskkill /F /IM RanorexClient.exe");
        } catch (IOException e) {
            throw new RuntimeException("Error: ", e);
        }
        try {
            Sleeper.sleep(5, SleepReasons.TIME);
            Runtime.getRuntime().exec(RanorexConfig.get().ranorexClientPath() + " " + config.port());
            Sleeper.sleep(5, SleepReasons.TIME);
        } catch (Exception ex) {
            throw new RuntimeException("Could not Start RanorexClient", ex);
        }
    }

    public void startApp(String app, String args) {
        final Map<String, Object> params = new HashMap<>();
        params.put("Rvalue", app);
        params.put("args", args);
        send("start", app, params);
    }

    public void sapFlavor(Boolean value) {
        final Map<String, Object> params = new HashMap<>();
        params.put("value", value);
        send("SAPFLAVOR", "", params);
    }
    
    public void startMobileApp(String app, int platform, int connectiontype, String ip, String devicename) {
        final Map<String, Object> params = new HashMap<>();
        params.put("platform", platform);
        params.put("connectiontype", connectiontype);
        params.put("ip", ip);
        params.put("devicename", devicename);
        send("startMobileApp", app, params);
    }

    public void closeMobileApp(String by) {
        final Map<String, Object> params = new HashMap<>();
        send("closeMobileApp", by, params);
    }

    public void click(String by) {
        final Map<String, Object> params = new HashMap<>();
        send("click", by, params);
    }

    public void clickXY(String by, String x, String y) {
        final Map<String, Object> params = new HashMap<>();
        params.put("x", x);
        params.put("y", y);
        send("click_x_y", by, params);
    }


    public void clickUp(String by) {
        final Map<String, Object> params = new HashMap<>();
        send("click_up", by, params);
    }

    public void clickDown(String by) {
        final Map<String, Object> params = new HashMap<>();
        send("click_down", by, params);
    }


    public int countElements(String by) {
        final Map<String, Object> params = new HashMap<>();
        return Integer.parseInt(send("countElements", by, params));
    }

    public void rightClickXY(String by, String x, String y) {
        final Map<String, Object> params = new HashMap<>();
        params.put("x", x);
        params.put("y", y);
        send("right_click_x_y", by, params);
    }

    public void doubleClickXY(String by, String x, String y) {
        final Map<String, Object> params = new HashMap<>();
        params.put("x", x);
        params.put("y", y);
        send("double_click_x_y", by, params);
    }

    public void pasteFromClipboard() {
        final Map<String, Object> params = new HashMap<>();
        send("pastefromClipboard", "", params);
    }

    public void copyToClipboard() {
        final Map<String, Object> params = new HashMap<>();
        send("copyToClipboard", "", params);
    }

    public void doubleClick(String by) {
        final Map<String, Object> params = new HashMap<>();
        send("doubleclick", by, params);
    }

    public void sapDoubleClick(String by) {
        final Map<String, Object> params = new HashMap<>();
        send("sapdoubleclick", by, params);
    }

    public void rightClick(String by) {
        final Map<String, Object> params = new HashMap<>();
        send("rightclick", by, params);
    }

    public void rightClickTabPage(String by) {
        final Map<String, Object> params = new HashMap<>();
        send("rightclickTabpage", by, params);
    }

    public void type(String by, String what) {
        final Map<String, Object> params = new HashMap<>();
        params.put("what", what);
        send("type", by, params);
    }

    // Gibt Text in ein Element ein
    public void typeDelay(String by, String what, Integer duration) {
        final Map<String, Object> params = new HashMap<>();
        params.put("what", what);
        params.put("duration", duration.toString());
        send("type_delay", by, params);
    }

    // Gibt Text in ein Element ein
    public void mobileKeyPress(String by, String what) {
        final Map<String, Object> params = new HashMap<>();
        params.put("sequence", what);
        send("PressKeys", by, params);
    }

    public void move(String by) {
        final Map<String, Object> params = new HashMap<>();
        send("move", by, params);
    }

    public void moveToXY(String by, int x, int y) {
        final Map<String, Object> params = new HashMap<>();
        params.put("x", x);
        params.put("y", y);
        send("move_to_x_y", by, params);
    }

    public void touch(String by) {
        final Map<String, Object> params = new HashMap<>();
        params.put("at", "CENTER");
        send("touch", by, params);
    }

    public void longTouch(String by) {
        final Map<String, Object> params = new HashMap<>();
        params.put("at", "CENTER");
        send("longtouch", by, params);
    }

    public void setAttributeText(String by, String what) {
        final Map<String, Object> params = new HashMap<>();
        params.put("attribute", "Text");
        params.put("value", what);
        send("setAttribute", by, params);
    }

    public void setAttribute(String by, String attribute, String what) {
        final Map<String, Object> params = new HashMap<>();
        params.put("attribute", attribute);
        params.put("value", what);
        send("setAttribute", by, params);
    }

    public void swipe(String by, int angle, double distance, int duration) {
        final Map<String, Object> params = new HashMap<>();
        params.put("angle", angle);
        params.put("distance", distance);
        params.put("duration", duration);
        send("swipe", by, params);
    }

    public void find(String by) {
        final Map<String, Object> params = new HashMap<>();
        send("find", by, params);
    }

    public boolean isPresent(String by) {
        try {
            final Map<String, Object> params = new HashMap<>();
            return Boolean.parseBoolean(send("isVisible", by, params));
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isPresent(String by, Integer duration) {
        try {
            final Map<String, Object> params = new HashMap<>();
            params.put("duration", duration);
            Date stop;
            if (waitForElement(by, duration)) {
                stop = DateUtils.addSeconds(new Date(), duration);
                while (stop.after(new Date())) {
                    if (Boolean.parseBoolean(send("isVisible", by, params))) {
                        return true;
                    }
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public void ensureVisible(String by) {
        final Map<String, Object> params = new HashMap<>();
        send("ensurevisible", by, params);
    }

    public String getValue(String by, String attribute) {
        final Map<String, Object> params = new HashMap<>();
        params.put("attribute", attribute);
        return send("get", by, params);
    }

    public Integer getChildsCountbyAttribute(String by, String attribute, String value) {
        final Map<String, Object> params = new HashMap<>();
        params.put("attribute", attribute);
        params.put("value", value);
        String result = send("getChildsCountbyAttribute", by, params);
        if (result.isEmpty()) {
            return 0;
        } else {
            return Integer.parseInt(result);
        }

    }

    public String getChildPathbyIndex(String by, String attribute, String value, int index) {
        final Map<String, Object> params = new HashMap<>();
        params.put("attribute", attribute);
        params.put("value", value);
        params.put("index", index);
        return send("getChildPathbyIndex", by, params);
    }

    public String takeSnapshot(String path) {
        final Map<String, Object> params = new HashMap<>();
        return send("takeSnapshot", path, params);
    }

    public String takeScreenshot(String path) {
        final Map<String, Object> params = new HashMap<>();
        return send("takeScreenshot", path, params);
    }

    public void clearField(String by) {
        final Map<String, Object> params = new HashMap<>();
        send("clear", by, params);
    }

    public void selectMenuItem(String by) {
        final Map<String, Object> params = new HashMap<>();
        send("SelectMenuItem", by, params);
    }

    public void selectTabPage(String by) {
        final Map<String, Object> params = new HashMap<>();
        send("SelectTabpage", by, params);
    }

    public String getChildsCountbyAdapter(String by, String adapter) {
        final Map<String, Object> params = new HashMap<>();
        params.put("adapter", adapter);
        return send("getChildsCountbyAdapter", by, params);
    }

    public Boolean waitForElement(String by, Integer duration) {
        final Map<String, Object> params = new HashMap<>();
        params.put("duration", duration);
        return Boolean.parseBoolean(send("waitForElement", by, params));
    }

    public String getParent(String by) {
        final Map<String, Object> params = new HashMap<>();
        return send("getParent", by, params);
    }

    public Boolean comparePicture(String by, String picBase64, double similarity) {
        final Map<String, Object> params = new HashMap<>();
        params.put("picture", picBase64);
        params.put("similarity", similarity);
        return Boolean.parseBoolean(send("comparepicture", by, params));
    }
    
    public void openBrowser(String browser, String url) {
        final Map<String, Object> params = new HashMap<>();
        params.put("browser", browser);
        params.put("url", url);
        send("OpenBrowser", "", params);
    }

    private String send(String action, String by, final Map<String, Object> params) {
        String toSend = "";
        final Map<String, Object> hm = new HashMap<>();
        hm.put("action", action);
        hm.put("path", by);
        hm.put("param", params);
        try {
            toSend = Base64.encodeBase64String(gson.toJson(hm).getBytes("UTF8"));
        } catch (UnsupportedEncodingException e) {
            System.out.println("UnsupportedEncodingException: " + e.getMessage());
        }

        requester.send(toSend);
        String decodedReply;
        item.poll(config.timeoutMs());

        if (item.pollin(0)) {
            String reply = requester.recvStr(0);
            decodedReply = new String(Base64.decodeBase64(reply));
            actionCounter = actionCounter + 1;
        } else {
            System.out.println("Timeout " + action);
            System.out.println("Action counter: " + actionCounter);
            actionCounter = 0;
            restartClient();

            return send(action, by, params);
        }


        if (decodedReply.startsWith("RanorexError")) {
            onError(action, by);
        }
        return decodedReply;
    }

    public void restartClient() {
        System.out.println("handle timeout");
        disconnectClient();
        System.out.println("disconnect client");
        startRanorex();
        System.out.println("restart ranorex");
        connectToClient();
        System.out.println("reconnect client");
    }

    @Override
    public void cleanUp() {
        try {
            Runtime.getRuntime().exec("taskkill /F /IM RanorexClient.exe");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void onError(String action, String path) {
        throw new AutomationFrameworkException("Ranorex Failed " + action + " on " + path);

    }

    public final Map<String, String> getAttrFromObjList(String path, String attr) {
        int elements = countElements(path);
        final Map<String, String> hm = new HashMap<>();
        String val, orig;
        int duplette = 0;
        for (Integer count = 1; count <= elements; count++) {
            orig = getValue(path + "[" + count + "]", attr);
            val = orig;
            while (hm.containsKey(val)) {
                duplette++;
                val = orig + "_" + duplette;
            }
            hm.put(val, count.toString());
        }

        return hm;
    }
}
