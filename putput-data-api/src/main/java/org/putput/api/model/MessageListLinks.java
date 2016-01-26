
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
 * The links of a message list
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "self",
    "nextPage",
    "previousPage"
})
public class MessageListLinks {

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
    @JsonProperty("nextPage")
    private HalLink nextPage;
    /**
     * A HAL link
     * 
     */
    @JsonProperty("previousPage")
    private HalLink previousPage;
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

    public MessageListLinks withSelf(HalLink self) {
        this.self = self;
        return this;
    }

    /**
     * A HAL link
     * 
     * @return
     *     The nextPage
     */
    @JsonProperty("nextPage")
    public HalLink getNextPage() {
        return nextPage;
    }

    /**
     * A HAL link
     * 
     * @param nextPage
     *     The nextPage
     */
    @JsonProperty("nextPage")
    public void setNextPage(HalLink nextPage) {
        this.nextPage = nextPage;
    }

    public MessageListLinks withNextPage(HalLink nextPage) {
        this.nextPage = nextPage;
        return this;
    }

    /**
     * A HAL link
     * 
     * @return
     *     The previousPage
     */
    @JsonProperty("previousPage")
    public HalLink getPreviousPage() {
        return previousPage;
    }

    /**
     * A HAL link
     * 
     * @param previousPage
     *     The previousPage
     */
    @JsonProperty("previousPage")
    public void setPreviousPage(HalLink previousPage) {
        this.previousPage = previousPage;
    }

    public MessageListLinks withPreviousPage(HalLink previousPage) {
        this.previousPage = previousPage;
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

    public MessageListLinks withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}
