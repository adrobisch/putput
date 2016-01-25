
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
    "files",
    "_links"
})
public class FileList {

    @JsonProperty("count")
    private Integer count;
    @JsonProperty("pageSize")
    private Integer pageSize;
    @JsonProperty("currentPage")
    private Integer currentPage;
    @JsonProperty("files")
    private List<File> files = new ArrayList<File>();
    /**
     * The links of a file list
     * 
     */
    @JsonProperty("_links")
    private FileListLinks Links;
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

    public FileList withCount(Integer count) {
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

    public FileList withPageSize(Integer pageSize) {
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

    public FileList withCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
        return this;
    }

    /**
     * 
     * @return
     *     The files
     */
    @JsonProperty("files")
    public List<File> getFiles() {
        return files;
    }

    /**
     * 
     * @param files
     *     The files
     */
    @JsonProperty("files")
    public void setFiles(List<File> files) {
        this.files = files;
    }

    public FileList withFiles(List<File> files) {
        this.files = files;
        return this;
    }

    /**
     * The links of a file list
     * 
     * @return
     *     The Links
     */
    @JsonProperty("_links")
    public FileListLinks getLinks() {
        return Links;
    }

    /**
     * The links of a file list
     * 
     * @param Links
     *     The _links
     */
    @JsonProperty("_links")
    public void setLinks(FileListLinks Links) {
        this.Links = Links;
    }

    public FileList withLinks(FileListLinks Links) {
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

    public FileList withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}
