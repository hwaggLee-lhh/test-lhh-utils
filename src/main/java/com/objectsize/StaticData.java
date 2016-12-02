package com.objectsize;

/**
 * This is a value object class that stores the Packge name and field name for a
 * field.
 *
 * @author Sandeep Jindal, Kuldeep Singh Virdi, Rajiv Goyal
 */
class StaticData {

    private String packageName;
    private String fieldName;

    /**
     * Default Constuctor
     *
     */
    public StaticData() {
    }

    /**
     * Overloaded constructor
     *
     * @param _packageName
     * @param _fieldName
     */
    public StaticData(String _packageName, String _fieldName) {
        super();
        this.packageName = _packageName;
        this.fieldName = _fieldName;

    }

    @Override
    public int hashCode() {
        return packageName.length();
    }

    @Override
    public boolean equals(Object o) {
        StaticData staticData = (StaticData) o;
        return (staticData.packageName.equalsIgnoreCase(packageName) && staticData.fieldName.equalsIgnoreCase(fieldName));
    }

//    public int compareTo(StaticData staticData) {
//        String thisFullString = staticData.packageName + staticData.fieldName;
//        String thatFullString = packageName + fieldName;
//        return thisFullString.compareTo(thatFullString);
//    }

    @Override
    public String toString() {
        return packageName + "." + fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
