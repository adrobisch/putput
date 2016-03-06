
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
    "token",
    "secret",
    "created",
    "updated",
    "version",
    "_links"
})
public class AccessToken {

    @JsonProperty("id")
    private String id;
    @JsonProperty("token")
    private String token;
    @JsonProperty("secret")
    private String secret;
    @JsonProperty("created")
    private Double created;
    @JsonProperty("updated")
    private Double updated;
    @JsonProperty("version")
    private Integer version;
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

    public AccessToken withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * 
     * @return
     *     The token
     */
    @JsonProperty("token")
    public String getToken() {
        return token;
    }

    /**
     * 
     * @param token
     *     The token
     */
    @JsonProperty("token")
    public void setToken(String token) {
        this.token = token;
    }

    public AccessToken withToken(String token) {
        this.token = token;
        return this;
    }

    /**
     * 
     * @return
     *     The secret
     */
    @JsonProperty("secret")
    public String getSecret() {
        return secret;
    }

    /**
     * 
     * @param secret
     *     The secret
     */
    @JsonProperty("secret")
    public void setSecret(String secret) {
        this.secret = secret;
    }

    public AccessToken withSecret(String secret) {
        this.secret = secret;
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

    public AccessToken withCreated(Double created) {
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

    public AccessToken withUpdated(Double updated) {
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

    public AccessToken withVersion(Integer version) {
        this.version = version;
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

    public AccessToken withLinks(AccessTokenLinks Links) {
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

    public AccessToken withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}
