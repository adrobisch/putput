
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
    "itemId",
    "markerType"
})
public class NewMarker {

    @JsonProperty("itemId")
    private String itemId;
    @JsonProperty("markerType")
    private String markerType;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The itemId
     */
    @JsonProperty("itemId")
    public String getItemId() {
        return itemId;
    }

    /**
     * 
     * @param itemId
     *     The itemId
     */
    @JsonProperty("itemId")
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public NewMarker withItemId(String itemId) {
        this.itemId = itemId;
        return this;
    }

    /**
     * 
     * @return
     *     The markerType
     */
    @JsonProperty("markerType")
    public String getMarkerType() {
        return markerType;
    }

    /**
     * 
     * @param markerType
     *     The markerType
     */
    @JsonProperty("markerType")
    public void setMarkerType(String markerType) {
        this.markerType = markerType;
    }

    public NewMarker withMarkerType(String markerType) {
        this.markerType = markerType;
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

    public NewMarker withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}
