
package models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sub {

    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("tag")
    @Expose
    private String tag;
    @SerializedName("schemaOrgTag")
    @Expose
    private Object schemaOrgTag;
    @SerializedName("total")
    @Expose
    private Double total;
    @SerializedName("hasRDI")
    @Expose
    private Boolean hasRDI;
    @SerializedName("daily")
    @Expose
    private Double daily;
    @SerializedName("unit")
    @Expose
    private String unit;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Sub() {
    }

    /**
     * 
     * @param schemaOrgTag
     * @param total
     * @param unit
     * @param daily
     * @param hasRDI
     * @param label
     * @param tag
     */
    public Sub(String label, String tag, Object schemaOrgTag, Double total, Boolean hasRDI, Double daily, String unit) {
        super();
        this.label = label;
        this.tag = tag;
        this.schemaOrgTag = schemaOrgTag;
        this.total = total;
        this.hasRDI = hasRDI;
        this.daily = daily;
        this.unit = unit;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Object getSchemaOrgTag() {
        return schemaOrgTag;
    }

    public void setSchemaOrgTag(Object schemaOrgTag) {
        this.schemaOrgTag = schemaOrgTag;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Boolean getHasRDI() {
        return hasRDI;
    }

    public void setHasRDI(Boolean hasRDI) {
        this.hasRDI = hasRDI;
    }

    public Double getDaily() {
        return daily;
    }

    public void setDaily(Double daily) {
        this.daily = daily;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

}
