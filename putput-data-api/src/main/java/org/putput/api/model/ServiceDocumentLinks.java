
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
    "self",
    "access-tokens",
    "access-token",
    "notes",
    "note",
    "events",
    "event",
    "messages",
    "message",
    "files",
    "file",
    "contacts",
    "contact",
    "users",
    "user",
    "user-info",
    "user-settings",
    "user-feeds",
    "user-feed",
    "profile",
    "login",
    "logout",
    "password-reset",
    "password-reset-confirmation"
})
public class ServiceDocumentLinks {

    /**
     * A HAL link
     * 
     */
    @JsonProperty("self")
    private HalLink self;
    /**
     * A HAL link
     * 
     */
    @JsonProperty("access-tokens")
    private HalLink accessTokens;
    /**
     * A HAL link
     * 
     */
    @JsonProperty("access-token")
    private HalLink accessToken;
    /**
     * A HAL link
     * 
     */
    @JsonProperty("notes")
    private HalLink notes;
    /**
     * A HAL link
     * 
     */
    @JsonProperty("note")
    private HalLink note;
    /**
     * A HAL link
     * 
     */
    @JsonProperty("events")
    private HalLink events;
    /**
     * A HAL link
     * 
     */
    @JsonProperty("event")
    private HalLink event;
    /**
     * A HAL link
     * 
     */
    @JsonProperty("messages")
    private HalLink messages;
    /**
     * A HAL link
     * 
     */
    @JsonProperty("message")
    private HalLink message;
    /**
     * A HAL link
     * 
     */
    @JsonProperty("files")
    private HalLink files;
    /**
     * A HAL link
     * 
     */
    @JsonProperty("file")
    private HalLink file;
    /**
     * A HAL link
     * 
     */
    @JsonProperty("contacts")
    private HalLink contacts;
    /**
     * A HAL link
     * 
     */
    @JsonProperty("contact")
    private HalLink contact;
    /**
     * A HAL link
     * 
     */
    @JsonProperty("users")
    private HalLink users;
    /**
     * A HAL link
     * 
     */
    @JsonProperty("user")
    private HalLink user;
    /**
     * A HAL link
     * 
     */
    @JsonProperty("user-info")
    private HalLink userInfo;
    /**
     * A HAL link
     * 
     */
    @JsonProperty("user-settings")
    private HalLink userSettings;
    /**
     * A HAL link
     * 
     */
    @JsonProperty("user-feeds")
    private HalLink userFeeds;
    /**
     * A HAL link
     * 
     */
    @JsonProperty("user-feed")
    private HalLink userFeed;
    /**
     * A HAL link
     * 
     */
    @JsonProperty("profile")
    private HalLink profile;
    /**
     * A HAL link
     * 
     */
    @JsonProperty("login")
    private HalLink login;
    /**
     * A HAL link
     * 
     */
    @JsonProperty("logout")
    private HalLink logout;
    /**
     * A HAL link
     * 
     */
    @JsonProperty("password-reset")
    private HalLink passwordReset;
    /**
     * A HAL link
     * 
     */
    @JsonProperty("password-reset-confirmation")
    private HalLink passwordResetConfirmation;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * A HAL link
     * 
     * @return
     *     The self
     */
    @JsonProperty("self")
    public HalLink getSelf() {
        return self;
    }

    /**
     * A HAL link
     * 
     * @param self
     *     The self
     */
    @JsonProperty("self")
    public void setSelf(HalLink self) {
        this.self = self;
    }

    public ServiceDocumentLinks withSelf(HalLink self) {
        this.self = self;
        return this;
    }

    /**
     * A HAL link
     * 
     * @return
     *     The accessTokens
     */
    @JsonProperty("access-tokens")
    public HalLink getAccessTokens() {
        return accessTokens;
    }

    /**
     * A HAL link
     * 
     * @param accessTokens
     *     The access-tokens
     */
    @JsonProperty("access-tokens")
    public void setAccessTokens(HalLink accessTokens) {
        this.accessTokens = accessTokens;
    }

    public ServiceDocumentLinks withAccessTokens(HalLink accessTokens) {
        this.accessTokens = accessTokens;
        return this;
    }

    /**
     * A HAL link
     * 
     * @return
     *     The accessToken
     */
    @JsonProperty("access-token")
    public HalLink getAccessToken() {
        return accessToken;
    }

    /**
     * A HAL link
     * 
     * @param accessToken
     *     The access-token
     */
    @JsonProperty("access-token")
    public void setAccessToken(HalLink accessToken) {
        this.accessToken = accessToken;
    }

    public ServiceDocumentLinks withAccessToken(HalLink accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    /**
     * A HAL link
     * 
     * @return
     *     The notes
     */
    @JsonProperty("notes")
    public HalLink getNotes() {
        return notes;
    }

    /**
     * A HAL link
     * 
     * @param notes
     *     The notes
     */
    @JsonProperty("notes")
    public void setNotes(HalLink notes) {
        this.notes = notes;
    }

    public ServiceDocumentLinks withNotes(HalLink notes) {
        this.notes = notes;
        return this;
    }

    /**
     * A HAL link
     * 
     * @return
     *     The note
     */
    @JsonProperty("note")
    public HalLink getNote() {
        return note;
    }

    /**
     * A HAL link
     * 
     * @param note
     *     The note
     */
    @JsonProperty("note")
    public void setNote(HalLink note) {
        this.note = note;
    }

    public ServiceDocumentLinks withNote(HalLink note) {
        this.note = note;
        return this;
    }

    /**
     * A HAL link
     * 
     * @return
     *     The events
     */
    @JsonProperty("events")
    public HalLink getEvents() {
        return events;
    }

    /**
     * A HAL link
     * 
     * @param events
     *     The events
     */
    @JsonProperty("events")
    public void setEvents(HalLink events) {
        this.events = events;
    }

    public ServiceDocumentLinks withEvents(HalLink events) {
        this.events = events;
        return this;
    }

    /**
     * A HAL link
     * 
     * @return
     *     The event
     */
    @JsonProperty("event")
    public HalLink getEvent() {
        return event;
    }

    /**
     * A HAL link
     * 
     * @param event
     *     The event
     */
    @JsonProperty("event")
    public void setEvent(HalLink event) {
        this.event = event;
    }

    public ServiceDocumentLinks withEvent(HalLink event) {
        this.event = event;
        return this;
    }

    /**
     * A HAL link
     * 
     * @return
     *     The messages
     */
    @JsonProperty("messages")
    public HalLink getMessages() {
        return messages;
    }

    /**
     * A HAL link
     * 
     * @param messages
     *     The messages
     */
    @JsonProperty("messages")
    public void setMessages(HalLink messages) {
        this.messages = messages;
    }

    public ServiceDocumentLinks withMessages(HalLink messages) {
        this.messages = messages;
        return this;
    }

    /**
     * A HAL link
     * 
     * @return
     *     The message
     */
    @JsonProperty("message")
    public HalLink getMessage() {
        return message;
    }

    /**
     * A HAL link
     * 
     * @param message
     *     The message
     */
    @JsonProperty("message")
    public void setMessage(HalLink message) {
        this.message = message;
    }

    public ServiceDocumentLinks withMessage(HalLink message) {
        this.message = message;
        return this;
    }

    /**
     * A HAL link
     * 
     * @return
     *     The files
     */
    @JsonProperty("files")
    public HalLink getFiles() {
        return files;
    }

    /**
     * A HAL link
     * 
     * @param files
     *     The files
     */
    @JsonProperty("files")
    public void setFiles(HalLink files) {
        this.files = files;
    }

    public ServiceDocumentLinks withFiles(HalLink files) {
        this.files = files;
        return this;
    }

    /**
     * A HAL link
     * 
     * @return
     *     The file
     */
    @JsonProperty("file")
    public HalLink getFile() {
        return file;
    }

    /**
     * A HAL link
     * 
     * @param file
     *     The file
     */
    @JsonProperty("file")
    public void setFile(HalLink file) {
        this.file = file;
    }

    public ServiceDocumentLinks withFile(HalLink file) {
        this.file = file;
        return this;
    }

    /**
     * A HAL link
     * 
     * @return
     *     The contacts
     */
    @JsonProperty("contacts")
    public HalLink getContacts() {
        return contacts;
    }

    /**
     * A HAL link
     * 
     * @param contacts
     *     The contacts
     */
    @JsonProperty("contacts")
    public void setContacts(HalLink contacts) {
        this.contacts = contacts;
    }

    public ServiceDocumentLinks withContacts(HalLink contacts) {
        this.contacts = contacts;
        return this;
    }

    /**
     * A HAL link
     * 
     * @return
     *     The contact
     */
    @JsonProperty("contact")
    public HalLink getContact() {
        return contact;
    }

    /**
     * A HAL link
     * 
     * @param contact
     *     The contact
     */
    @JsonProperty("contact")
    public void setContact(HalLink contact) {
        this.contact = contact;
    }

    public ServiceDocumentLinks withContact(HalLink contact) {
        this.contact = contact;
        return this;
    }

    /**
     * A HAL link
     * 
     * @return
     *     The users
     */
    @JsonProperty("users")
    public HalLink getUsers() {
        return users;
    }

    /**
     * A HAL link
     * 
     * @param users
     *     The users
     */
    @JsonProperty("users")
    public void setUsers(HalLink users) {
        this.users = users;
    }

    public ServiceDocumentLinks withUsers(HalLink users) {
        this.users = users;
        return this;
    }

    /**
     * A HAL link
     * 
     * @return
     *     The user
     */
    @JsonProperty("user")
    public HalLink getUser() {
        return user;
    }

    /**
     * A HAL link
     * 
     * @param user
     *     The user
     */
    @JsonProperty("user")
    public void setUser(HalLink user) {
        this.user = user;
    }

    public ServiceDocumentLinks withUser(HalLink user) {
        this.user = user;
        return this;
    }

    /**
     * A HAL link
     * 
     * @return
     *     The userInfo
     */
    @JsonProperty("user-info")
    public HalLink getUserInfo() {
        return userInfo;
    }

    /**
     * A HAL link
     * 
     * @param userInfo
     *     The user-info
     */
    @JsonProperty("user-info")
    public void setUserInfo(HalLink userInfo) {
        this.userInfo = userInfo;
    }

    public ServiceDocumentLinks withUserInfo(HalLink userInfo) {
        this.userInfo = userInfo;
        return this;
    }

    /**
     * A HAL link
     * 
     * @return
     *     The userSettings
     */
    @JsonProperty("user-settings")
    public HalLink getUserSettings() {
        return userSettings;
    }

    /**
     * A HAL link
     * 
     * @param userSettings
     *     The user-settings
     */
    @JsonProperty("user-settings")
    public void setUserSettings(HalLink userSettings) {
        this.userSettings = userSettings;
    }

    public ServiceDocumentLinks withUserSettings(HalLink userSettings) {
        this.userSettings = userSettings;
        return this;
    }

    /**
     * A HAL link
     * 
     * @return
     *     The userFeeds
     */
    @JsonProperty("user-feeds")
    public HalLink getUserFeeds() {
        return userFeeds;
    }

    /**
     * A HAL link
     * 
     * @param userFeeds
     *     The user-feeds
     */
    @JsonProperty("user-feeds")
    public void setUserFeeds(HalLink userFeeds) {
        this.userFeeds = userFeeds;
    }

    public ServiceDocumentLinks withUserFeeds(HalLink userFeeds) {
        this.userFeeds = userFeeds;
        return this;
    }

    /**
     * A HAL link
     * 
     * @return
     *     The userFeed
     */
    @JsonProperty("user-feed")
    public HalLink getUserFeed() {
        return userFeed;
    }

    /**
     * A HAL link
     * 
     * @param userFeed
     *     The user-feed
     */
    @JsonProperty("user-feed")
    public void setUserFeed(HalLink userFeed) {
        this.userFeed = userFeed;
    }

    public ServiceDocumentLinks withUserFeed(HalLink userFeed) {
        this.userFeed = userFeed;
        return this;
    }

    /**
     * A HAL link
     * 
     * @return
     *     The profile
     */
    @JsonProperty("profile")
    public HalLink getProfile() {
        return profile;
    }

    /**
     * A HAL link
     * 
     * @param profile
     *     The profile
     */
    @JsonProperty("profile")
    public void setProfile(HalLink profile) {
        this.profile = profile;
    }

    public ServiceDocumentLinks withProfile(HalLink profile) {
        this.profile = profile;
        return this;
    }

    /**
     * A HAL link
     * 
     * @return
     *     The login
     */
    @JsonProperty("login")
    public HalLink getLogin() {
        return login;
    }

    /**
     * A HAL link
     * 
     * @param login
     *     The login
     */
    @JsonProperty("login")
    public void setLogin(HalLink login) {
        this.login = login;
    }

    public ServiceDocumentLinks withLogin(HalLink login) {
        this.login = login;
        return this;
    }

    /**
     * A HAL link
     * 
     * @return
     *     The logout
     */
    @JsonProperty("logout")
    public HalLink getLogout() {
        return logout;
    }

    /**
     * A HAL link
     * 
     * @param logout
     *     The logout
     */
    @JsonProperty("logout")
    public void setLogout(HalLink logout) {
        this.logout = logout;
    }

    public ServiceDocumentLinks withLogout(HalLink logout) {
        this.logout = logout;
        return this;
    }

    /**
     * A HAL link
     * 
     * @return
     *     The passwordReset
     */
    @JsonProperty("password-reset")
    public HalLink getPasswordReset() {
        return passwordReset;
    }

    /**
     * A HAL link
     * 
     * @param passwordReset
     *     The password-reset
     */
    @JsonProperty("password-reset")
    public void setPasswordReset(HalLink passwordReset) {
        this.passwordReset = passwordReset;
    }

    public ServiceDocumentLinks withPasswordReset(HalLink passwordReset) {
        this.passwordReset = passwordReset;
        return this;
    }

    /**
     * A HAL link
     * 
     * @return
     *     The passwordResetConfirmation
     */
    @JsonProperty("password-reset-confirmation")
    public HalLink getPasswordResetConfirmation() {
        return passwordResetConfirmation;
    }

    /**
     * A HAL link
     * 
     * @param passwordResetConfirmation
     *     The password-reset-confirmation
     */
    @JsonProperty("password-reset-confirmation")
    public void setPasswordResetConfirmation(HalLink passwordResetConfirmation) {
        this.passwordResetConfirmation = passwordResetConfirmation;
    }

    public ServiceDocumentLinks withPasswordResetConfirmation(HalLink passwordResetConfirmation) {
        this.passwordResetConfirmation = passwordResetConfirmation;
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

    public ServiceDocumentLinks withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}
