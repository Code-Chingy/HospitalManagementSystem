package com.techupstudio.school_management_system.base.sqlite_database;

public class TableField {

    private String _name, _type, _default;
    private boolean nullable, primarykey, unique;

    public static class Type {
        public static final String INTEGER = "INTEGER";
        public static final String TEXT = "TEXT";
        public static final String VARCHAR = "VARCHAR";
        public static final String CHAR = "CHAR";
        public static final String DATE = "DATE";
        public static final String DATETIME = "DATETIME";
    }

    public static class IntegerField extends TableField{
        public IntegerField(String name) {
            super(name, Type.INTEGER);
        }
    }
    public static class TextField extends TableField{
        public TextField(String name) {
            super(name, Type.TEXT);
        }
    }
    public static class DateField extends TableField{
        public DateField(String name) {
            super(name, Type.DATE);
        }
    }
    public static class DateTimeField extends TableField{
        public DateTimeField(String name) {
            super(name, Type.DATETIME);
        }
    }
    public static class CharField extends TableField{
        public CharField(String name) {
            super(name, Type.CHAR);
        }
    }
    public static class VarCharField extends TableField{
        public VarCharField(String name, int maxLength) {
            super(name, Type.VARCHAR+"("+maxLength+")");
        }
    }

    public TableField(String name){
        init(name, "");
    }

    public TableField(String name, String type){
       init(name, type);
    }

    private void init(String name, String type){
        _name = name;
        _type = type;
        nullable = true;
        primarykey = false;
        unique = false;
    }

    public TableField setNullable(boolean isNullable){
        this.nullable = isNullable;
        return this;
    }
    public TableField setAsPrimaryKey(boolean isPrimaryKey){
        this.primarykey = isPrimaryKey;
        return this;
    }
    public TableField setAsUnique(boolean isUnique){
        this.unique = isUnique;
        return this;
    }
    public TableField setDefault(String _default){
        this._default = _default;
        return this;
    }

    @Override
    public String toString() {
        String complete =  _name+" "+_type.toUpperCase();
        if (primarykey)
            complete += " PRIMARY KEY";
        if (unique)
            complete += " UNIQUE";
        if (!nullable)
            complete += " NOT NULL";

        return complete;
    }
}
