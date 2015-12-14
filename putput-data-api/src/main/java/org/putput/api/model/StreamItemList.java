
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


/**
 * A HAL collection
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "count",
    "items",
    "_links"
})
public class StreamItemList {

    @JsonProperty("count")
    private Integer count;
    @JsonProperty("items")
    private List<StreamItemResource> items = new ArrayList<StreamItemResource>();
    /**
     * The links of a stream item list
     * 
     */
    @JsonProperty("_links")
    private StreamItemListLinks Links;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The count
     */
    @JsonProperty("count")
    public Integer getCount() {
        return count;
    }

    /**
     * 
     * @param count
     *     The count
     */
    @JsonProperty("count")
    public void setCount(Integer count) {
        this.count = count;
    }

    public StreamItemList withCount(Integer count) {
        this.count = count;
        return this;
    }

    /**
     * 
     * @return
     *     The items
     */
    @JsonProperty("items")
    public List<StreamItemResource> getItems() {
        return items;
    }

    /**
     * 
     * @param items
     *     The items
     */
    @JsonProperty("items")
    public void setItems(List<StreamItemResource> items) {
        this.items = items;
    }

    public StreamItemList withItems(List<StreamItemResource> items) {
        this.items = items;
        return this;
    }

    /**
     * The links of a stream item list
     * 
     * @return
     *     The Links
     */
    @JsonProperty("_links")
    public StreamItemListLinks getLinks() {
        return Links;
    }

    /**
     * The links of a stream item list
     * 
     * @param Links
     *     The _links
     */
    @JsonProperty("_links")
    public void setLinks(StreamItemListLinks Links) {
        this.Links = Links;
    }

    public StreamItemList withLinks(StreamItemListLinks Links) {
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

    public StreamItemList withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}
