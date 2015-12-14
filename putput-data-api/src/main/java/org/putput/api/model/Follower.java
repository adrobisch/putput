
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
    "follower",
    "followed"
})
public class Follower {

    @JsonProperty("follower")
    private String follower;
    @JsonProperty("followed")
    private String followed;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The follower
     */
    @JsonProperty("follower")
    public String getFollower() {
        return follower;
    }

    /**
     * 
     * @param follower
     *     The follower
     */
    @JsonProperty("follower")
    public void setFollower(String follower) {
        this.follower = follower;
    }

    public Follower withFollower(String follower) {
        this.follower = follower;
        return this;
    }

    /**
     * 
     * @return
     *     The followed
     */
    @JsonProperty("followed")
    public String getFollowed() {
        return followed;
    }

    /**
     * 
     * @param followed
     *     The followed
     */
    @JsonProperty("followed")
    public void setFollowed(String followed) {
        this.followed = followed;
    }

    public Follower withFollowed(String followed) {
        this.followed = followed;
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

    public Follower withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}
