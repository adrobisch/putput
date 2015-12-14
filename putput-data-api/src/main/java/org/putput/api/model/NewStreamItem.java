
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
    "source",
    "title",
    "content"
})
public class NewStreamItem {

    @JsonProperty("source")
    private String source;
    @JsonProperty("title")
    private String title;
    @JsonProperty("content")
    private String content;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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

    public NewStreamItem withSource(String source) {
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

    public NewStreamItem withTitle(String title) {
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

    public NewStreamItem withContent(String content) {
        this.content = content;
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

    public NewStreamItem withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}
