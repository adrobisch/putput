
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
    "accessTokens",
    "_links"
})
public class AccessTokenList {

    @JsonProperty("count")
    private Integer count;
    @JsonProperty("pageSize")
    private Integer pageSize;
    @JsonProperty("currentPage")
    private Integer currentPage;
    @JsonProperty("accessTokens")
    private List<AccessToken> accessTokens = new ArrayList<AccessToken>();
    /**
     * The links of a access token
     * 
     */
    @JsonProperty("_links")
    private AccessTokenLinks Links;
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

    public AccessTokenList withCount(Integer count) {
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

    public AccessTokenList withPageSize(Integer pageSize) {
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

    public AccessTokenList withCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
        return this;
    }

    /**
     * 
     * @return
     *     The accessTokens
     */
    @JsonProperty("accessTokens")
    public List<AccessToken> getAccessTokens() {
        return accessTokens;
    }

    /**
     * 
     * @param accessTokens
     *     The accessTokens
     */
    @JsonProperty("accessTokens")
    public void setAccessTokens(List<AccessToken> accessTokens) {
        this.accessTokens = accessTokens;
    }

    public AccessTokenList withAccessTokens(List<AccessToken> accessTokens) {
        this.accessTokens = accessTokens;
        return this;
    }

    /**
     * The links of a access token
     * 
     * @return
     *     The Links
     */
    @JsonProperty("_links")
    public AccessTokenLinks getLinks() {
        return Links;
    }

    /**
     * The links of a access token
     * 
     * @param Links
     *     The _links
     */
    @JsonProperty("_links")
    public void setLinks(AccessTokenLinks Links) {
        this.Links = Links;
    }

    public AccessTokenList withLinks(AccessTokenLinks Links) {
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

    public AccessTokenList withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}
