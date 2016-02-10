
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
    "owner",
    "start",
    "end",
    "type",
    "recurrence",
    "recurrenceEnd",
    "timezone",
    "title",
    "description",
    "created",
    "_links"
})
public class Event {

    @JsonProperty("id")
    private String id;
    @JsonProperty("owner")
    private String owner;
    @JsonProperty("start")
    private Double start;
    @JsonProperty("end")
    private Double end;
    @JsonProperty("type")
    private String type;
    @JsonProperty("recurrence")
    private String recurrence;
    @JsonProperty("recurrenceEnd")
    private Double recurrenceEnd;
    @JsonProperty("timezone")
    private String timezone;
    @JsonProperty("title")
    private String title;
    @JsonProperty("description")
    private String description;
    @JsonProperty("created")
    private Double created;
    /**
     * The links of a event
     * 
     */
    @JsonProperty("_links")
    private EventLinks Links;
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

    public Event withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * 
     * @return
     *     The owner
     */
    @JsonProperty("owner")
    public String getOwner() {
        return owner;
    }

    /**
     * 
     * @param owner
     *     The owner
     */
    @JsonProperty("owner")
    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Event withOwner(String owner) {
        this.owner = owner;
        return this;
    }

    /**
     * 
     * @return
     *     The start
     */
    @JsonProperty("start")
    public Double getStart() {
        return start;
    }

    /**
     * 
     * @param start
     *     The start
     */
    @JsonProperty("start")
    public void setStart(Double start) {
        this.start = start;
    }

    public Event withStart(Double start) {
        this.start = start;
        return this;
    }

    /**
     * 
     * @return
     *     The end
     */
    @JsonProperty("end")
    public Double getEnd() {
        return end;
    }

    /**
     * 
     * @param end
     *     The end
     */
    @JsonProperty("end")
    public void setEnd(Double end) {
        this.end = end;
    }

    public Event withEnd(Double end) {
        this.end = end;
        return this;
    }

    /**
     * 
     * @return
     *     The type
     */
    @JsonProperty("type")
    public String getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    public Event withType(String type) {
        this.type = type;
        return this;
    }

    /**
     * 
     * @return
     *     The recurrence
     */
    @JsonProperty("recurrence")
    public String getRecurrence() {
        return recurrence;
    }

    /**
     * 
     * @param recurrence
     *     The recurrence
     */
    @JsonProperty("recurrence")
    public void setRecurrence(String recurrence) {
        this.recurrence = recurrence;
    }

    public Event withRecurrence(String recurrence) {
        this.recurrence = recurrence;
        return this;
    }

    /**
     * 
     * @return
     *     The recurrenceEnd
     */
    @JsonProperty("recurrenceEnd")
    public Double getRecurrenceEnd() {
        return recurrenceEnd;
    }

    /**
     * 
     * @param recurrenceEnd
     *     The recurrenceEnd
     */
    @JsonProperty("recurrenceEnd")
    public void setRecurrenceEnd(Double recurrenceEnd) {
        this.recurrenceEnd = recurrenceEnd;
    }

    public Event withRecurrenceEnd(Double recurrenceEnd) {
        this.recurrenceEnd = recurrenceEnd;
        return this;
    }

    /**
     * 
     * @return
     *     The timezone
     */
    @JsonProperty("timezone")
    public String getTimezone() {
        return timezone;
    }

    /**
     * 
     * @param timezone
     *     The timezone
     */
    @JsonProperty("timezone")
    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public Event withTimezone(String timezone) {
        this.timezone = timezone;
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

    public Event withTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * 
     * @return
     *     The description
     */
    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    /**
     * 
     * @param description
     *     The description
     */
    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    public Event withDescription(String description) {
        this.description = description;
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

    public Event withCreated(Double created) {
        this.created = created;
        return this;
    }

    /**
     * The links of a event
     * 
     * @return
     *     The Links
     */
    @JsonProperty("_links")
    public EventLinks getLinks() {
        return Links;
    }

    /**
     * The links of a event
     * 
     * @param Links
     *     The _links
     */
    @JsonProperty("_links")
    public void setLinks(EventLinks Links) {
        this.Links = Links;
    }

    public Event withLinks(EventLinks Links) {
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

    public Event withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}
