
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
    "userName",
    "displayName",
    "about",
    "rssFeeds"
})
public class UserInfo {

    @JsonProperty("userName")
    private String userName;
    @JsonProperty("displayName")
    private String displayName;
    @JsonProperty("about")
    private String about;
    @JsonProperty("rssFeeds")
    private List<RssFeed> rssFeeds = new ArrayList<RssFeed>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The userName
     */
    @JsonProperty("userName")
    public String getUserName() {
        return userName;
    }

    /**
     * 
     * @param userName
     *     The userName
     */
    @JsonProperty("userName")
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public UserInfo withUserName(String userName) {
        this.userName = userName;
        return this;
    }

    /**
     * 
     * @return
     *     The displayName
     */
    @JsonProperty("displayName")
    public String getDisplayName() {
        return displayName;
    }

    /**
     * 
     * @param displayName
     *     The displayName
     */
    @JsonProperty("displayName")
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public UserInfo withDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

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

    public UserInfo withAbout(String about) {
        this.about = about;
        return this;
    }

    /**
     * 
     * @return
     *     The rssFeeds
     */
    @JsonProperty("rssFeeds")
    public List<RssFeed> getRssFeeds() {
        return rssFeeds;
    }

    /**
     * 
     * @param rssFeeds
     *     The rssFeeds
     */
    @JsonProperty("rssFeeds")
    public void setRssFeeds(List<RssFeed> rssFeeds) {
        this.rssFeeds = rssFeeds;
    }

    public UserInfo withRssFeeds(List<RssFeed> rssFeeds) {
        this.rssFeeds = rssFeeds;
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

    public UserInfo withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}
