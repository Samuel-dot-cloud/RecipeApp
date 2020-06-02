
package com.studiofive.recipeapp.network;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.studiofive.recipeapp.models.Hit;

public class EdamamRecipesSearchResponse {

    @SerializedName("q")
    @Expose
    private String q;
    @SerializedName("from")
    @Expose
    private Integer from;
    @SerializedName("to")
    @Expose
    private Integer to;
    @SerializedName("more")
    @Expose
    private Boolean more;
    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("hits")
    @Expose
    private List<Hit> hits = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public EdamamRecipesSearchResponse() {
    }

    /**
     * 
     * @param hits
     * @param q
     * @param more
     * @param count
     * @param from
     * @param to
     */
    public EdamamRecipesSearchResponse(String q, Integer from, Integer to, Boolean more, Integer count, List<Hit> hits) {
        super();
        this.q = q;
        this.from = from;
        this.to = to;
        this.more = more;
        this.count = count;
        this.hits = hits;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public Integer getTo() {
        return to;
    }

    public void setTo(Integer to) {
        this.to = to;
    }

    public Boolean getMore() {
        return more;
    }

    public void setMore(Boolean more) {
        this.more = more;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<Hit> getHits() {
        return hits;
    }

    public void setHits(List<Hit> hits) {
        this.hits = hits;
    }

}
