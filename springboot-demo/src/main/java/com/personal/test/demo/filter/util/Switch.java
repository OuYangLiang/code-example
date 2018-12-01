package com.personal.test.demo.filter.util;

import java.util.HashMap;
import java.util.Map;

public final class Switch {

    private static final Map<String, Config> switchCfg;
    static {
        switchCfg = new HashMap<>();

        switchCfg.put("/hello/", new Config(true, "http://192.168.6.149:9000"));
        switchCfg.put("/people", new Config(true, "http://192.168.6.149:9000"));
    }

    private static volatile Switch instance;
    private Switch() {

    }

    public static Switch getInstance() {
        if (null == instance) {
            synchronized (Switch.class) {
                if (null == instance) {
                    instance = new Switch();
                }
            }
        }

        return instance;
    }

    public boolean isGlobalSwitchOpen() {
        return false;
    }

    public boolean isUrlOpen(String url) {
        Config cfg = switchCfg.get(url);
        if (null == cfg) {
            for (Map.Entry<String, Config> entry : switchCfg.entrySet()) {
                if (url.startsWith(entry.getKey())) {
                    cfg = entry.getValue();
                }
            }
        }

        if (null == cfg) {
            throw new RuntimeException("Url [" + url + "] is not configured.");
        }

        return cfg.switchStatus;
    }

    public String addressOfUrl(String url) {
        Config cfg = switchCfg.get(url);
        if (null == cfg) {
            for (Map.Entry<String, Config> entry : switchCfg.entrySet()) {
                if (url.startsWith(entry.getKey())) {
                    cfg = entry.getValue();
                }
            }
        }

        if (null == cfg) {
            throw new RuntimeException("Url [" + url + "] is not configured.");
        }

        return cfg.address;
    }

    private static class Config {
        private boolean switchStatus;
        private String address;

        Config(boolean switchStatus, String address) {
            super();
            this.switchStatus = switchStatus;
            this.address = address;
        }
    }
}
