
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
 * The links of a file
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "self",
    "content",
    "tags"
})
public class FileLinks {

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
    @JsonProperty("content")
    private HalLink content;
    /**
     * A HAL link
     * 
     */
    @JsonProperty("tags")
    private HalLink tags;
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

    public FileLinks withSelf(HalLink self) {
        this.self = self;
        return this;
    }

    /**
     * A HAL link
     * 
     * @return
     *     The content
     */
    @JsonProperty("content")
    public HalLink getContent() {
        return content;
    }

    /**
     * A HAL link
     * 
     * @param content
     *     The content
     */
    @JsonProperty("content")
    public void setContent(HalLink content) {
        this.content = content;
    }

    public FileLinks withContent(HalLink content) {
        this.content = content;
        return this;
    }

    /**
     * A HAL link
     * 
     * @return
     *     The tags
     */
    @JsonProperty("tags")
    public HalLink getTags() {
        return tags;
    }

    /**
     * A HAL link
     * 
     * @param tags
     *     The tags
     */
    @JsonProperty("tags")
    public void setTags(HalLink tags) {
        this.tags = tags;
    }

    public FileLinks withTags(HalLink tags) {
        this.tags = tags;
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

    public FileLinks withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}
