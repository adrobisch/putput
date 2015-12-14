
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


/**
 * The links of a profile
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "self",
    "stream",
    "follow",
    "unfollow"
})
public class ProfileLinks {

    /**
     * A HAL link
     * 
     */
    @JsonProperty("self")
    private HalLink self;
    /**
     * A HAL link
     * 
     */
    @JsonProperty("stream")
    private HalLink stream;
    /**
     * A HAL link
     * 
     */
    @JsonProperty("follow")
    private HalLink follow;
    /**
     * A HAL link
     * 
     */
    @JsonProperty("unfollow")
    private HalLink unfollow;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * A HAL link
     * 
     * @return
     *     The self
     */
    @JsonProperty("self")
    public HalLink getSelf() {
        return self;
    }

    /**
     * A HAL link
     * 
     * @param self
     *     The self
     */
    @JsonProperty("self")
    public void setSelf(HalLink self) {
        this.self = self;
    }

    public ProfileLinks withSelf(HalLink self) {
        this.self = self;
        return this;
    }

    /**
     * A HAL link
     * 
     * @return
     *     The stream
     */
    @JsonProperty("stream")
    public HalLink getStream() {
        return stream;
    }

    /**
     * A HAL link
     * 
     * @param stream
     *     The stream
     */
    @JsonProperty("stream")
    public void setStream(HalLink stream) {
        this.stream = stream;
    }

    public ProfileLinks withStream(HalLink stream) {
        this.stream = stream;
        return this;
    }

    /**
     * A HAL link
     * 
     * @return
     *     The follow
     */
    @JsonProperty("follow")
    public HalLink getFollow() {
        return follow;
    }

    /**
     * A HAL link
     * 
     * @param follow
     *     The follow
     */
    @JsonProperty("follow")
    public void setFollow(HalLink follow) {
        this.follow = follow;
    }

    public ProfileLinks withFollow(HalLink follow) {
        this.follow = follow;
        return this;
    }

    /**
     * A HAL link
     * 
     * @return
     *     The unfollow
     */
    @JsonProperty("unfollow")
    public HalLink getUnfollow() {
        return unfollow;
    }

    /**
     * A HAL link
     * 
     * @param unfollow
     *     The unfollow
     */
    @JsonProperty("unfollow")
    public void setUnfollow(HalLink unfollow) {
        this.unfollow = unfollow;
    }

    public ProfileLinks withUnfollow(HalLink unfollow) {
        this.unfollow = unfollow;
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

    public ProfileLinks withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}
