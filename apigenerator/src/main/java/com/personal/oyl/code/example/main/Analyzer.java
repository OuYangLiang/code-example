package com.personal.oyl.code.example.main;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.personal.oyl.code.example.annotation.DESC;
import com.personal.oyl.code.example.annotation.SERVICE;
import com.personal.oyl.code.example.main.AnalyzedType.TypeField;
import com.personal.oyl.code.example.main.MethodResult.MethodParameter;
import com.personal.oyl.code.example.service.CustomerService;

public class Analyzer {
    private static ThreadLocal<Map<String, AnalyzedType>> local = new ThreadLocal<Map<String, AnalyzedType>>() {

        @Override
        protected Map<String, AnalyzedType> initialValue() {
            return new HashMap<>();
        }
        
    };
    
    public ServiceResult analyze(Class<?> klass) {
        ServiceResult result = new ServiceResult();
        result.setServiceName(klass.getCanonicalName());
        SERVICE serviceAnno = klass.getAnnotation(SERVICE.class);
        if (null != serviceAnno) {
            result.setServiceDesc(serviceAnno.value());
        }
        
        Method[] methods = klass.getDeclaredMethods();
        if (null != methods) {
            for (Method m : methods) {
                result.addMethodResult(analyze(m));
            }
        }
        
        return result;
    }
    
    private MethodResult analyze(Method method) {
        MethodResult methodResult = new MethodResult();
        methodResult.setMethodName(method.getName());
        DESC anno = method.getAnnotation(DESC.class);
        if (null != anno) {
            methodResult.setMethodDesc(anno.value());
        }
        methodResult.setReturnType(method.getReturnType().getCanonicalName());
        this.analyzeType(method.getReturnType());
        
        Parameter[] params = method.getParameters();
        if (null != params) {
            for (Parameter parameter : params) {
                MethodParameter mp = new MethodParameter();
                mp.setParamName(parameter.getName());
                mp.setParamType(parameter.getType().getCanonicalName());
                anno = parameter.getAnnotation(DESC.class);
                if (null != anno) {
                    mp.setParamDesc(anno.value());
                    mp.setMandatory(anno.mandatory());
                }
                methodResult.addParameter(mp);
                this.analyzeType(parameter.getType());
            }
        }
        
        Class<?>[] exceptions = method.getExceptionTypes();
        if (null != params) {
            for (Class<?> exception : exceptions) {
                methodResult.addException(exception.getCanonicalName());
                this.analyzeType(exception);
            }
        }
        
        return methodResult;
    }
    
    private void analyzeType(Class<?> klass) {
        if (local.get().containsKey(klass.getCanonicalName())) {
            return;
        }
        
        if (null == klass.getClassLoader()) {
            return;
        }
        
        AnalyzedType result = new AnalyzedType();
        result.setTypeName(klass.getCanonicalName());
        
        String typeDesc = null;
        DESC anno = klass.getAnnotation(DESC.class);
        if (null != anno) {
            typeDesc = anno.value();
        }
        result.setTypeDesc(typeDesc);
        
        Field[] fields = this.getFields(klass);
        if (null != fields) {
            for (Field field : fields) {
                if (klass.isEnum() && field.getName().equals("ENUM$VALUES")) {
                    continue;
                }
                
                String fieldDesc = null;
                DESC fieldAnno = field.getAnnotation(DESC.class);
                if (null != fieldAnno) {
                    fieldDesc = fieldAnno.value();
                }
                
                result.addField(new TypeField(field.getName(), fieldDesc, field.getType().getCanonicalName()));
                if (!klass.equals(field.getType())) {
                    this.analyzeType(field.getType());
                }
            }
            
            local.get().put(klass.getCanonicalName(), result);
        }
    }
    
    private Field[] getFields(Class<?> klass) {
        Field[] currentFields = klass.getDeclaredFields();
        Field[] superFields = null;
        
        Class<?> superClazz = klass.getSuperclass();
        
        
        if (null != superClazz && 
                (!superClazz.getName().equals("java.lang.Object") && !superClazz.getName().equals("java.lang.Enum"))) {
            superFields = this.getFields(superClazz);
        }
        
        if (null != superFields) {
            List<Field> list = new LinkedList<>();
            list.addAll(Arrays.asList(superFields));
            list.addAll(Arrays.asList(currentFields));
            
            Field[] rlt = new Field[list.size()];
            list.toArray(rlt);
            
            return rlt;
        }
        
        return currentFields;
    }
    
    public static void main(String[] args) {
        System.out.println(new Analyzer().analyze(CustomerService.class));
        System.out.println(local.get());
        local.remove();
    }
}
