
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
    "parentId",
    "storageId",
    "isDirectory",
    "name",
    "mimeType",
    "created",
    "updated",
    "version",
    "_links"
})
public class File {

    @JsonProperty("id")
    private String id;
    @JsonProperty("parentId")
    private String parentId;
    @JsonProperty("storageId")
    private String storageId;
    @JsonProperty("isDirectory")
    private Boolean isDirectory;
    @JsonProperty("name")
    private String name;
    @JsonProperty("mimeType")
    private String mimeType;
    @JsonProperty("created")
    private Double created;
    @JsonProperty("updated")
    private Double updated;
    @JsonProperty("version")
    private Integer version;
    /**
     * The links of a file
     * 
     */
    @JsonProperty("_links")
    private FileLinks Links;
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

    public File withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * 
     * @return
     *     The parentId
     */
    @JsonProperty("parentId")
    public String getParentId() {
        return parentId;
    }

    /**
     * 
     * @param parentId
     *     The parentId
     */
    @JsonProperty("parentId")
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public File withParentId(String parentId) {
        this.parentId = parentId;
        return this;
    }

    /**
     * 
     * @return
     *     The storageId
     */
    @JsonProperty("storageId")
    public String getStorageId() {
        return storageId;
    }

    /**
     * 
     * @param storageId
     *     The storageId
     */
    @JsonProperty("storageId")
    public void setStorageId(String storageId) {
        this.storageId = storageId;
    }

    public File withStorageId(String storageId) {
        this.storageId = storageId;
        return this;
    }

    /**
     * 
     * @return
     *     The isDirectory
     */
    @JsonProperty("isDirectory")
    public Boolean getIsDirectory() {
        return isDirectory;
    }

    /**
     * 
     * @param isDirectory
     *     The isDirectory
     */
    @JsonProperty("isDirectory")
    public void setIsDirectory(Boolean isDirectory) {
        this.isDirectory = isDirectory;
    }

    public File withIsDirectory(Boolean isDirectory) {
        this.isDirectory = isDirectory;
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

    public File withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * 
     * @return
     *     The mimeType
     */
    @JsonProperty("mimeType")
    public String getMimeType() {
        return mimeType;
    }

    /**
     * 
     * @param mimeType
     *     The mimeType
     */
    @JsonProperty("mimeType")
    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public File withMimeType(String mimeType) {
        this.mimeType = mimeType;
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

    public File withCreated(Double created) {
        this.created = created;
        return this;
    }

    /**
     * 
     * @return
     *     The updated
     */
    @JsonProperty("updated")
    public Double getUpdated() {
        return updated;
    }

    /**
     * 
     * @param updated
     *     The updated
     */
    @JsonProperty("updated")
    public void setUpdated(Double updated) {
        this.updated = updated;
    }

    public File withUpdated(Double updated) {
        this.updated = updated;
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

    public File withVersion(Integer version) {
        this.version = version;
        return this;
    }

    /**
     * The links of a file
     * 
     * @return
     *     The Links
     */
    @JsonProperty("_links")
    public FileLinks getLinks() {
        return Links;
    }

    /**
     * The links of a file
     * 
     * @param Links
     *     The _links
     */
    @JsonProperty("_links")
    public void setLinks(FileLinks Links) {
        this.Links = Links;
    }

    public File withLinks(FileLinks Links) {
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

    public File withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}
