
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
    "streamItem",
    "markerInfo",
    "_links"
})
public class StreamItemResource {

    @JsonProperty("streamItem")
    private StreamItem streamItem;
    @JsonProperty("markerInfo")
    private MarkerInfo markerInfo;
    /**
     * The links of a stream item
     * 
     */
    @JsonProperty("_links")
    private StreamItemLinks Links;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The streamItem
     */
    @JsonProperty("streamItem")
    public StreamItem getStreamItem() {
        return streamItem;
    }

    /**
     * 
     * @param streamItem
     *     The streamItem
     */
    @JsonProperty("streamItem")
    public void setStreamItem(StreamItem streamItem) {
        this.streamItem = streamItem;
    }

    public StreamItemResource withStreamItem(StreamItem streamItem) {
        this.streamItem = streamItem;
        return this;
    }

    /**
     * 
     * @return
     *     The markerInfo
     */
    @JsonProperty("markerInfo")
    public MarkerInfo getMarkerInfo() {
        return markerInfo;
    }

    /**
     * 
     * @param markerInfo
     *     The markerInfo
     */
    @JsonProperty("markerInfo")
    public void setMarkerInfo(MarkerInfo markerInfo) {
        this.markerInfo = markerInfo;
    }

    public StreamItemResource withMarkerInfo(MarkerInfo markerInfo) {
        this.markerInfo = markerInfo;
        return this;
    }

    /**
     * The links of a stream item
     * 
     * @return
     *     The Links
     */
    @JsonProperty("_links")
    public StreamItemLinks getLinks() {
        return Links;
    }

    /**
     * The links of a stream item
     * 
     * @param Links
     *     The _links
     */
    @JsonProperty("_links")
    public void setLinks(StreamItemLinks Links) {
        this.Links = Links;
    }

    public StreamItemResource withLinks(StreamItemLinks Links) {
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

    public StreamItemResource withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}
