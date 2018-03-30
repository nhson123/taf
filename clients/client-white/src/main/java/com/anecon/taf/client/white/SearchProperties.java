package com.anecon.taf.client.white;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fatboyindustrial.gsonjodatime.Converters;
import com.google.gson.GsonBuilder;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public class SearchProperties {
    private String technologyName;
    private String automationId;
    private String name;
    private String windowTitle;
    private String controlType;
    private Integer index;
    private int childIndex;
    private int tabIndex;
    private String childControlType;
    private int actionCount;
    private Vector relativeOffset;

    public SearchProperties() {

    }

    public SearchProperties(SearchProperties original) {
        this.technologyName = original.technologyName;
        this.automationId = original.automationId;
        this.name = original.name;
        this.windowTitle = original.windowTitle;
        this.controlType = original.controlType;
        this.index = original.index;
        this.childIndex = original.childIndex;
        this.tabIndex = original.tabIndex;
        this.childControlType = original.childControlType;
        this.actionCount = original.actionCount;
        this.relativeOffset = original.relativeOffset;
    }

    public String getChildControlType() {
        return childControlType;
    }

    public void setChildControlType(String childControlType) {
        this.childControlType = childControlType;
    }

    public String getAutomationId() {
        return automationId;
    }

    public void setAutomationId(String automationId) {
        this.automationId = automationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTechnologyName() {
        return technologyName;
    }

    public void setTechnologyName(String technologyName) {
        this.technologyName = technologyName;
    }

    public String getWindowTitle() {
        return windowTitle;
    }

    public void setWindowTitle(String windowTitle) {
        this.windowTitle = windowTitle;
    }

    public String getControlType() {
        return controlType;
    }

    public void setControlType(String controlType) {
        this.controlType = controlType;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public int getChildIndex() {
        return childIndex;
    }

    public void setChildIndex(int childIndex) {
        this.childIndex = childIndex;
    }

    public int getTabIndex() {
        return tabIndex;
    }

    public void setTabIndex(int tabIndex) {
        this.tabIndex = tabIndex;
    }

    public int getActionCount() {
        return actionCount;
    }

    public void setActionCount(int actionCount) {
        this.actionCount = actionCount;
    }

    public Vector getRelativeOffset() {
        return relativeOffset;
    }

    public void setRelativeOffset(Vector relativeOffset) {
        this.relativeOffset = relativeOffset;
    }

    @Override
    public String toString() {
        return Converters.registerAll(new GsonBuilder()).setPrettyPrinting().create().toJson(this);
    }
}
