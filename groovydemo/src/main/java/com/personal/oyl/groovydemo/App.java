package com.personal.oyl.groovydemo;

import groovy.lang.Binding;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;

import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * @author OuYang Liang
 * @since 2019-05-28
 */
public class App {
    /*public static void main(String[] args) throws IllegalAccessException, InstantiationException, IOException {
        GroovyClassLoader loader = new GroovyClassLoader();
        try (InputStream is = App.class.getResourceAsStream("/TestGroovy0.groovy")) {
            try (Reader r = new InputStreamReader(is)) {
                Class<?> claz = loader.parseClass(r, "TestGroovy0");
                if (Predicate.class.isAssignableFrom(claz)) {
                    Predicate p = (Predicate) claz.newInstance();
                    System.out.println(p.shouldDo("Hello"));
                } else {
                    System.out.println(claz);
                }
            }
        }
    }*/

    public static void main(String[] args) throws ResourceException, ScriptException, InterruptedException {

        String[] paths = {"\\"};
        URL[] urls = new URL[]{App.class.getResource("/")};
        GroovyScriptEngine engine = new GroovyScriptEngine(urls);
        Binding binding = new Binding();
        binding.setVariable("$a", new AppContext());
        while (true) {
            Object rlt = engine.run("Rule_0001.groovy", binding);
            if (rlt instanceof Predicate) {
                Predicate p = (Predicate) rlt;
                Order order = new Order(1, "002");
                System.out.println(p.test(order));
            }

            TimeUnit.MILLISECONDS.sleep(500);
        }
    }
}
