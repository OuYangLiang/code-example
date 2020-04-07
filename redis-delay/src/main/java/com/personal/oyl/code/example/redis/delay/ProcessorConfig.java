package com.personal.oyl.code.example.redis.delay;

import java.util.HashMap;
import java.util.Map;

/**
 * @author OuYang Liang
 * @since 2019-09-24
 */
public final class ProcessorConfig {
    private static volatile ProcessorConfig instance;
    private ProcessorConfig() {

    }

    public static ProcessorConfig instance() {
        if (null == instance) {
            synchronized (ProcessorConfig.class) {
                if (null == instance) {
                    instance = new ProcessorConfig();
                }
            }
        }

        return instance;
    }

    private Map<String, MessageProcessor> cfg = new HashMap<>();

    MessageProcessor getProcessor(String topic) {
        if (this.cfg.containsKey(topic)) {
            return this.cfg.get(topic);
        }

        return null;
    }

    public void setProcessor(String topic, MessageProcessor processor) {
        this.cfg.put(topic, processor);
    }

}
