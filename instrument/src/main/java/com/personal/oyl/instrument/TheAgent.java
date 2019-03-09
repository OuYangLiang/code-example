package com.personal.oyl.instrument;

import java.lang.instrument.Instrumentation;

public class TheAgent {
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("Premain executing........");
        inst.addTransformer(new TheClassFileTransformer());
    }
}
