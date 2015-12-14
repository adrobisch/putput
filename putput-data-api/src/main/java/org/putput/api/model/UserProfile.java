
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
    "profile",
    "following",
    "_links"
})
public class UserProfile {

    @JsonProperty("profile")
    private ProfileInfo profile;
    @JsonProperty("following")
    private Boolean following;
    /**
     * The links of a profile
     * 
     */
    @JsonProperty("_links")
    private ProfileLinks Links;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The profile
     */
    @JsonProperty("profile")
    public ProfileInfo getProfile() {
        return profile;
    }

    /**
     * 
     * @param profile
     *     The profile
     */
    @JsonProperty("profile")
    public void setProfile(ProfileInfo profile) {
        this.profile = profile;
    }

    public UserProfile withProfile(ProfileInfo profile) {
        this.profile = profile;
        return this;
    }

    /**
     * 
     * @return
     *     The following
     */
    @JsonProperty("following")
    public Boolean getFollowing() {
        return following;
    }

    /**
     * 
     * @param following
     *     The following
     */
    @JsonProperty("following")
    public void setFollowing(Boolean following) {
        this.following = following;
    }

    public UserProfile withFollowing(Boolean following) {
        this.following = following;
        return this;
    }

    /**
     * The links of a profile
     * 
     * @return
     *     The Links
     */
    @JsonProperty("_links")
    public ProfileLinks getLinks() {
        return Links;
    }

    /**
     * The links of a profile
     * 
     * @param Links
     *     The _links
     */
    @JsonProperty("_links")
    public void setLinks(ProfileLinks Links) {
        this.Links = Links;
    }

    public UserProfile withLinks(ProfileLinks Links) {
        this.Links = Links;
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

    public UserProfile withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}
