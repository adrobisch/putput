
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
    "creator",
    "source",
    "title",
    "content",
    "created"
})
public class StreamItem {

    @JsonProperty("id")
    private String id;
    @JsonProperty("creator")
    private String creator;
    @JsonProperty("source")
    private String source;
    @JsonProperty("title")
    private String title;
    @JsonProperty("content")
    private String content;
    @JsonProperty("created")
    private Double created;
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

    public StreamItem withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * 
     * @return
     *     The creator
     */
    @JsonProperty("creator")
    public String getCreator() {
        return creator;
    }

    /**
     * 
     * @param creator
     *     The creator
     */
    @JsonProperty("creator")
    public void setCreator(String creator) {
        this.creator = creator;
    }

    public StreamItem withCreator(String creator) {
        this.creator = creator;
        return this;
    }

    /**
     * 
     * @return
     *     The source
     */
    @JsonProperty("source")
    public String getSource() {
        return source;
    }

    /**
     * 
     * @param source
     *     The source
     */
    @JsonProperty("source")
    public void setSource(String source) {
        this.source = source;
    }

    public StreamItem withSource(String source) {
        this.source = source;
        return this;
    }

    /**
     * 
     * @return
     *     The title
     */
    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *     The title
     */
    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    public StreamItem withTitle(String title) {
        this.title = title;
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

    public StreamItem withContent(String content) {
        this.content = content;
        return this;
    }

    /**
     * 
     * @return
     *     The created
     */
    @JsonProperty("created")
    public Double getCreated() {
        return created;
    }

    /**
     * 
     * @param created
     *     The created
     */
    @JsonProperty("created")
    public void setCreated(Double created) {
        this.created = created;
    }

    public StreamItem withCreated(Double created) {
        this.created = created;
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

    public StreamItem withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}
