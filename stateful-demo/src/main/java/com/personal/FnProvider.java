package com.personal;

import org.apache.flink.statefun.sdk.FunctionType;
import org.apache.flink.statefun.sdk.StatefulFunction;
import org.apache.flink.statefun.sdk.StatefulFunctionProvider;

/**
 * @author OuYang Liang
 * @since 2021-04-01
 */
public class FnProvider implements StatefulFunctionProvider {
    @Override
    public StatefulFunction functionOfType(FunctionType type) {
        if (TestFunction.type.equals(type)) {
            return new TestFunction();
        }

        return null;
    }
}
