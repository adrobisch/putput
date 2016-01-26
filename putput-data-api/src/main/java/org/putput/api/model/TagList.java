
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
    "pageSize",
    "currentPage",
    "tags",
    "_links"
})
public class TagList {

    @JsonProperty("count")
    private Integer count;
    @JsonProperty("pageSize")
    private Integer pageSize;
    @JsonProperty("currentPage")
    private Integer currentPage;
    @JsonProperty("tags")
    private List<Tag> tags = new ArrayList<Tag>();
    /**
     * The links of a contact list
     * 
     */
    @JsonProperty("_links")
    private ContactListLinks Links;
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

    public TagList withCount(Integer count) {
        this.count = count;
        return this;
    }

    /**
     * 
     * @return
     *     The pageSize
     */
    @JsonProperty("pageSize")
    public Integer getPageSize() {
        return pageSize;
    }

    /**
     * 
     * @param pageSize
     *     The pageSize
     */
    @JsonProperty("pageSize")
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public TagList withPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    /**
     * 
     * @return
     *     The currentPage
     */
    @JsonProperty("currentPage")
    public Integer getCurrentPage() {
        return currentPage;
    }

    /**
     * 
     * @param currentPage
     *     The currentPage
     */
    @JsonProperty("currentPage")
    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public TagList withCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
        return this;
    }

    /**
     * 
     * @return
     *     The tags
     */
    @JsonProperty("tags")
    public List<Tag> getTags() {
        return tags;
    }

    /**
     * 
     * @param tags
     *     The tags
     */
    @JsonProperty("tags")
    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public TagList withTags(List<Tag> tags) {
        this.tags = tags;
        return this;
    }

    /**
     * The links of a contact list
     * 
     * @return
     *     The Links
     */
    @JsonProperty("_links")
    public ContactListLinks getLinks() {
        return Links;
    }

    /**
     * The links of a contact list
     * 
     * @param Links
     *     The _links
     */
    @JsonProperty("_links")
    public void setLinks(ContactListLinks Links) {
        this.Links = Links;
    }

    public TagList withLinks(ContactListLinks Links) {
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

    public TagList withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}
