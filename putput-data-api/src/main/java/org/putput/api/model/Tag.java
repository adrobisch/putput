
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
    "id",
    "taggableId",
    "name",
    "text",
    "_links"
})
public class Tag {

    @JsonProperty("id")
    private String id;
    @JsonProperty("taggableId")
    private String taggableId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("text")
    private String text;
    /**
     * The links of a tag
     * 
     */
    @JsonProperty("_links")
    private TagLinks Links;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The id
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public Tag withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * 
     * @return
     *     The taggableId
     */
    @JsonProperty("taggableId")
    public String getTaggableId() {
        return taggableId;
    }

    /**
     * 
     * @param taggableId
     *     The taggableId
     */
    @JsonProperty("taggableId")
    public void setTaggableId(String taggableId) {
        this.taggableId = taggableId;
    }

    public Tag withTaggableId(String taggableId) {
        this.taggableId = taggableId;
        return this;
    }

    /**
     * 
     * @return
     *     The name
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public Tag withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * 
     * @return
     *     The text
     */
    @JsonProperty("text")
    public String getText() {
        return text;
    }

    /**
     * 
     * @param text
     *     The text
     */
    @JsonProperty("text")
    public void setText(String text) {
        this.text = text;
    }

    public Tag withText(String text) {
        this.text = text;
        return this;
    }

    /**
     * The links of a tag
     * 
     * @return
     *     The Links
     */
    @JsonProperty("_links")
    public TagLinks getLinks() {
        return Links;
    }

    /**
     * The links of a tag
     * 
     * @param Links
     *     The _links
     */
    @JsonProperty("_links")
    public void setLinks(TagLinks Links) {
        this.Links = Links;
    }

    public Tag withLinks(TagLinks Links) {
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

    public Tag withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}
