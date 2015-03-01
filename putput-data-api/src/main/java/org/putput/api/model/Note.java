
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
    "shortText",
    "content",
    "version",
    "_links"
})
public class Note {

    @JsonProperty("id")
    private String id;
    @JsonProperty("shortText")
    private String shortText;
    @JsonProperty("content")
    private String content;
    @JsonProperty("version")
    private Integer version;
    /**
     * The links of a note
     * 
     */
    @JsonProperty("_links")
    private NoteLinks Links;
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

    public Note withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * 
     * @return
     *     The shortText
     */
    @JsonProperty("shortText")
    public String getShortText() {
        return shortText;
    }

    /**
     * 
     * @param shortText
     *     The shortText
     */
    @JsonProperty("shortText")
    public void setShortText(String shortText) {
        this.shortText = shortText;
    }

    public Note withShortText(String shortText) {
        this.shortText = shortText;
        return this;
    }

    /**
     * 
     * @return
     *     The content
     */
    @JsonProperty("content")
    public String getContent() {
        return content;
    }

    /**
     * 
     * @param content
     *     The content
     */
    @JsonProperty("content")
    public void setContent(String content) {
        this.content = content;
    }

    public Note withContent(String content) {
        this.content = content;
        return this;
    }

    /**
     * 
     * @return
     *     The version
     */
    @JsonProperty("version")
    public Integer getVersion() {
        return version;
    }

    /**
     * 
     * @param version
     *     The version
     */
    @JsonProperty("version")
    public void setVersion(Integer version) {
        this.version = version;
    }

    public Note withVersion(Integer version) {
        this.version = version;
        return this;
    }

    /**
     * The links of a note
     * 
     * @return
     *     The Links
     */
    @JsonProperty("_links")
    public NoteLinks getLinks() {
        return Links;
    }

    /**
     * The links of a note
     * 
     * @param Links
     *     The _links
     */
    @JsonProperty("_links")
    public void setLinks(NoteLinks Links) {
        this.Links = Links;
    }

    public Note withLinks(NoteLinks Links) {
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

    public Note withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}
