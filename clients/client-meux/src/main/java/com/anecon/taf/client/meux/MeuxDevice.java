package com.anecon.taf.client.meux;

/**
 * Used by {@link MeuxClient} as parent for individiaul elements.
 * <p>
 * Usage: {@code MeuxCDevice.build().name("WINDOWSMOBILE39"}
 */
public class MeuxDevice extends MeuxDescription {
    private static final MeuxDevice EMPTY_INSTANCE = new MeuxDevice();
    public String name=null;

    private MeuxDevice() {
        super(null, "MobileDevice");
    }

    private MeuxDevice(MeuxDevice meuxDevice, String key, String value) {
        super(meuxDevice, key, value);
        if(key.equalsIgnoreCase("name")){
            this.name = value;
        }
    }

    public static MeuxDevice build() {
        return EMPTY_INSTANCE;
    }

    public MeuxDevice status(String status) {
        return new MeuxDevice(this, "status", status);
    }

    public MeuxDevice model(String model) {
        return new MeuxDevice(this, "model", model);
    }

    public MeuxDevice name(String name) {
        return new MeuxDevice(this, "name", name);
    }

    public MeuxDevice manufacturer(String manufacturer) {
        return new MeuxDevice(this, "manufacturer", manufacturer);
    }

    public MeuxDevice osName(String osName) {
        return new MeuxDevice(this, "osName", osName);
    }

    public MeuxDevice versionOs(String versionOs) {
        return new MeuxDevice(this, "versionOS", versionOs);
    }

    public MeuxDevice versionOsMajor(String versionOsMajor) {
        return new MeuxDevice(this, "versionOSMajor", versionOsMajor);
    }

    public MeuxDevice versionOsMinor(String versionOsMinor) {
        return new MeuxDevice(this, "versionOSMinor", versionOsMinor);
    }
}
