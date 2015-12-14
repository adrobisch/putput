
package org.putput.api.model;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "dislikes",
    "isDislike",
    "isFavorite",
    "isLike",
    "isReadLater",
    "likes"
})
public class MarkerInfo {

    @JsonProperty("dislikes")
    private Double dislikes;
    @JsonProperty("isDislike")
    private Boolean isDislike;
    @JsonProperty("isFavorite")
    private Boolean isFavorite;
    @JsonProperty("isLike")
    private Boolean isLike;
    @JsonProperty("isReadLater")
    private Boolean isReadLater;
    @JsonProperty("likes")
    private Double likes;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The dislikes
     */
    @JsonProperty("dislikes")
    public Double getDislikes() {
        return dislikes;
    }

    /**
     * 
     * @param dislikes
     *     The dislikes
     */
    @JsonProperty("dislikes")
    public void setDislikes(Double dislikes) {
        this.dislikes = dislikes;
    }

    public MarkerInfo withDislikes(Double dislikes) {
        this.dislikes = dislikes;
        return this;
    }

    /**
     * 
     * @return
     *     The isDislike
     */
    @JsonProperty("isDislike")
    public Boolean getIsDislike() {
        return isDislike;
    }

    /**
     * 
     * @param isDislike
     *     The isDislike
     */
    @JsonProperty("isDislike")
    public void setIsDislike(Boolean isDislike) {
        this.isDislike = isDislike;
    }

    public MarkerInfo withIsDislike(Boolean isDislike) {
        this.isDislike = isDislike;
        return this;
    }

    /**
     * 
     * @return
     *     The isFavorite
     */
    @JsonProperty("isFavorite")
    public Boolean getIsFavorite() {
        return isFavorite;
    }

    /**
     * 
     * @param isFavorite
     *     The isFavorite
     */
    @JsonProperty("isFavorite")
    public void setIsFavorite(Boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    public MarkerInfo withIsFavorite(Boolean isFavorite) {
        this.isFavorite = isFavorite;
        return this;
    }

    /**
     * 
     * @return
     *     The isLike
     */
    @JsonProperty("isLike")
    public Boolean getIsLike() {
        return isLike;
    }

    /**
     * 
     * @param isLike
     *     The isLike
     */
    @JsonProperty("isLike")
    public void setIsLike(Boolean isLike) {
        this.isLike = isLike;
    }

    public MarkerInfo withIsLike(Boolean isLike) {
        this.isLike = isLike;
        return this;
    }

    /**
     * 
     * @return
     *     The isReadLater
     */
    @JsonProperty("isReadLater")
    public Boolean getIsReadLater() {
        return isReadLater;
    }

    /**
     * 
     * @param isReadLater
     *     The isReadLater
     */
    @JsonProperty("isReadLater")
    public void setIsReadLater(Boolean isReadLater) {
        this.isReadLater = isReadLater;
    }

    public MarkerInfo withIsReadLater(Boolean isReadLater) {
        this.isReadLater = isReadLater;
        return this;
    }

    /**
     * 
     * @return
     *     The likes
     */
    @JsonProperty("likes")
    public Double getLikes() {
        return likes;
    }

    /**
     * 
     * @param likes
     *     The likes
     */
    @JsonProperty("likes")
    public void setLikes(Double likes) {
        this.likes = likes;
    }

    public MarkerInfo withLikes(Double likes) {
        this.likes = likes;
        return this;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public MarkerInfo withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}
