package com.personal.oyl.code.example.main;

import java.util.LinkedList;
import java.util.List;

public class AnalyzedType {
    private String typeName;
    private String typeDesc;
    private List<TypeField> fields;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
    
    public String getTypeDesc() {
        return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
    }

    public List<TypeField> getFields() {
        return fields;
    }

    public void setFields(List<TypeField> fields) {
        this.fields = fields;
    }

    public void addField(TypeField field) {
        if (null == fields) {
            fields = new LinkedList<>();
        }

        fields.add(field);
    }

    @Override
    public String toString() {
        return "AnalyzedType [typeName=" + typeName + ", typeDesc=" + typeDesc + ", fields=" + fields + "]";
    }

    public static class TypeField {
        private String fieldName;
        private String fieldDesc;
        private String fieldType;

        public TypeField(String fieldName, String fieldDesc, String fieldType) {
            super();
            this.fieldName = fieldName;
            this.fieldDesc = fieldDesc;
            this.fieldType = fieldType;
        }

        public String getFieldDesc() {
            return fieldDesc;
        }

        public void setFieldDesc(String fieldDesc) {
            this.fieldDesc = fieldDesc;
        }

        public String getFieldName() {
            return fieldName;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

        public String getFieldType() {
            return fieldType;
        }

        public void setFieldType(String type) {
            this.fieldType = type;
        }

        @Override
        public String toString() {
            return "TypeField [fieldName=" + fieldName + ", fieldDesc=" + fieldDesc + ", fieldType=" + fieldType
                    + "]";
        }

    }
}
