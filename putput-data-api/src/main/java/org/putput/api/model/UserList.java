
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
    "users",
    "_links"
})
public class UserList {

    @JsonProperty("count")
    private Integer count;
    @JsonProperty("users")
    private List<UserInfo> users = new ArrayList<UserInfo>();
    /**
     * The links of a user list
     * 
     */
    @JsonProperty("_links")
    private UserListLinks Links;
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

    public UserList withCount(Integer count) {
        this.count = count;
        return this;
    }

    /**
     * 
     * @return
     *     The users
     */
    @JsonProperty("users")
    public List<UserInfo> getUsers() {
        return users;
    }

    /**
     * 
     * @param users
     *     The users
     */
    @JsonProperty("users")
    public void setUsers(List<UserInfo> users) {
        this.users = users;
    }

    public UserList withUsers(List<UserInfo> users) {
        this.users = users;
        return this;
    }

    /**
     * The links of a user list
     * 
     * @return
     *     The Links
     */
    @JsonProperty("_links")
    public UserListLinks getLinks() {
        return Links;
    }

    /**
     * The links of a user list
     * 
     * @param Links
     *     The _links
     */
    @JsonProperty("_links")
    public void setLinks(UserListLinks Links) {
        this.Links = Links;
    }

    public UserList withLinks(UserListLinks Links) {
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

    public UserList withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}
