package org.acme;
public class DomainClassesMetadataa0f73a0eb55f4f498647a1247b85f3b3 {

    public static final org.drools.model.DomainClassMetadata java_lang_Integer_Metadata_INSTANCE = new java_lang_Integer_Metadata();
    private static class java_lang_Integer_Metadata implements org.drools.model.DomainClassMetadata {

        @Override
        public Class<?> getDomainClass() {
            return java.lang.Integer.class;
        }

        @Override
        public int getPropertiesSize() {
            return 8;
        }

        @Override
        public int getPropertyIndex( String name ) {
            switch(name) {
                case "this": return 0;
                case "byteValue": return 1;
                case "describeConstable": return 2;
                case "doubleValue": return 3;
                case "floatValue": return 4;
                case "intValue": return 5;
                case "longValue": return 6;
                case "shortValue": return 7;
             }
             throw new RuntimeException("Unknown property '" + name + "' for class class class java.lang.Integer");
        }
    }
    public static final org.drools.model.DomainClassMetadata java_util_List_Metadata_INSTANCE = new java_util_List_Metadata();
    private static class java_util_List_Metadata implements org.drools.model.DomainClassMetadata {

        @Override
        public Class<?> getDomainClass() {
            return java.util.List.class;
        }

        @Override
        public int getPropertiesSize() {
            return 15;
        }

        @Override
        public int getPropertyIndex( String name ) {
            switch(name) {
                case "this": return 0;
                case "empty": return 1;
                case "first": return 2;
                case "last": return 3;
                case "parallelStream": return 4;
                case "stream": return 5;
                case "iterator": return 6;
                case "listIterator": return 7;
                case "of": return 8;
                case "removeFirst": return 9;
                case "removeLast": return 10;
                case "reversed": return 11;
                case "size": return 12;
                case "spliterator": return 13;
                case "toArray": return 14;
             }
             throw new RuntimeException("Unknown property '" + name + "' for class class interface java.util.List");
        }
    }
    public static final org.drools.model.DomainClassMetadata org_acme_LoanApplication_Metadata_INSTANCE = new org_acme_LoanApplication_Metadata();
    private static class org_acme_LoanApplication_Metadata implements org.drools.model.DomainClassMetadata {

        @Override
        public Class<?> getDomainClass() {
            return org.acme.LoanApplication.class;
        }

        @Override
        public int getPropertiesSize() {
            return 5;
        }

        @Override
        public int getPropertyIndex( String name ) {
            switch(name) {
                case "this": return 0;
                case "amount": return 1;
                case "applicant": return 2;
                case "approved": return 3;
                case "deposit": return 4;
             }
             throw new RuntimeException("Unknown property '" + name + "' for class class class org.acme.LoanApplication");
        }
    }
}