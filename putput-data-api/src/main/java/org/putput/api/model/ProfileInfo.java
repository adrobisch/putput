
package org.putput.api.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    "about",
    "followers",
    "following",
    "itemCount"
})
public class ProfileInfo {

    @JsonProperty("about")
    private String about;
    @JsonProperty("followers")
    private List<Follower> followers = new ArrayList<Follower>();
    @JsonProperty("following")
    private List<Follower> following = new ArrayList<Follower>();
    @JsonProperty("itemCount")
    private Integer itemCount;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The about
     */
    @JsonProperty("about")
    public String getAbout() {
        return about;
    }

    /**
     * 
     * @param about
     *     The about
     */
    @JsonProperty("about")
    public void setAbout(String about) {
        this.about = about;
    }

    public ProfileInfo withAbout(String about) {
        this.about = about;
        return this;
    }

    /**
     * 
     * @return
     *     The followers
     */
    @JsonProperty("followers")
    public List<Follower> getFollowers() {
        return followers;
    }

    /**
     * 
     * @param followers
     *     The followers
     */
    @JsonProperty("followers")
    public void setFollowers(List<Follower> followers) {
        this.followers = followers;
    }

    public ProfileInfo withFollowers(List<Follower> followers) {
        this.followers = followers;
        return this;
    }

    /**
     * 
     * @return
     *     The following
     */
    @JsonProperty("following")
    public List<Follower> getFollowing() {
        return following;
    }

    /**
     * 
     * @param following
     *     The following
     */
    @JsonProperty("following")
    public void setFollowing(List<Follower> following) {
        this.following = following;
    }

    public ProfileInfo withFollowing(List<Follower> following) {
        this.following = following;
        return this;
    }

    /**
     * 
     * @return
     *     The itemCount
     */
    @JsonProperty("itemCount")
    public Integer getItemCount() {
        return itemCount;
    }

    /**
     * 
     * @param itemCount
     *     The itemCount
     */
    @JsonProperty("itemCount")
    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }

    public ProfileInfo withItemCount(Integer itemCount) {
        this.itemCount = itemCount;
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

    public ProfileInfo withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}
