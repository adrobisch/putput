
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
 * A contact
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "id",
    "firstName",
    "lastName",
    "additionalNames",
    "salutation",
    "dateOfBirth",
    "anniversary",
    "organisation",
    "notes",
    "version",
    "contactAddresses",
    "phoneNumbers",
    "emails",
    "internetIdentifiers",
    "_links"
})
public class Contact {

    @JsonProperty("id")
    private String id;
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("additionalNames")
    private String additionalNames;
    @JsonProperty("salutation")
    private String salutation;
    @JsonProperty("dateOfBirth")
    private Double dateOfBirth;
    @JsonProperty("anniversary")
    private Double anniversary;
    @JsonProperty("organisation")
    private String organisation;
    @JsonProperty("notes")
    private String notes;
    @JsonProperty("version")
    private Integer version;
    @JsonProperty("contactAddresses")
    private List<ContactAddress> contactAddresses = new ArrayList<ContactAddress>();
    @JsonProperty("phoneNumbers")
    private List<PhoneNumber> phoneNumbers = new ArrayList<PhoneNumber>();
    @JsonProperty("emails")
    private List<Email> emails = new ArrayList<Email>();
    @JsonProperty("internetIdentifiers")
    private List<InternetIdentifier> internetIdentifiers = new ArrayList<InternetIdentifier>();
    /**
     * The links of a contact
     * 
     */
    @JsonProperty("_links")
    private ContactLinks Links;
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

    public Contact withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * 
     * @return
     *     The firstName
     */
    @JsonProperty("firstName")
    public String getFirstName() {
        return firstName;
    }

    /**
     * 
     * @param firstName
     *     The firstName
     */
    @JsonProperty("firstName")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Contact withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    /**
     * 
     * @return
     *     The lastName
     */
    @JsonProperty("lastName")
    public String getLastName() {
        return lastName;
    }

    /**
     * 
     * @param lastName
     *     The lastName
     */
    @JsonProperty("lastName")
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Contact withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    /**
     * 
     * @return
     *     The additionalNames
     */
    @JsonProperty("additionalNames")
    public String getAdditionalNames() {
        return additionalNames;
    }

    /**
     * 
     * @param additionalNames
     *     The additionalNames
     */
    @JsonProperty("additionalNames")
    public void setAdditionalNames(String additionalNames) {
        this.additionalNames = additionalNames;
    }

    public Contact withAdditionalNames(String additionalNames) {
        this.additionalNames = additionalNames;
        return this;
    }

    /**
     * 
     * @return
     *     The salutation
     */
    @JsonProperty("salutation")
    public String getSalutation() {
        return salutation;
    }

    /**
     * 
     * @param salutation
     *     The salutation
     */
    @JsonProperty("salutation")
    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    public Contact withSalutation(String salutation) {
        this.salutation = salutation;
        return this;
    }

    /**
     * 
     * @return
     *     The dateOfBirth
     */
    @JsonProperty("dateOfBirth")
    public Double getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * 
     * @param dateOfBirth
     *     The dateOfBirth
     */
    @JsonProperty("dateOfBirth")
    public void setDateOfBirth(Double dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Contact withDateOfBirth(Double dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    /**
     * 
     * @return
     *     The anniversary
     */
    @JsonProperty("anniversary")
    public Double getAnniversary() {
        return anniversary;
    }

    /**
     * 
     * @param anniversary
     *     The anniversary
     */
    @JsonProperty("anniversary")
    public void setAnniversary(Double anniversary) {
        this.anniversary = anniversary;
    }

    public Contact withAnniversary(Double anniversary) {
        this.anniversary = anniversary;
        return this;
    }

    /**
     * 
     * @return
     *     The organisation
     */
    @JsonProperty("organisation")
    public String getOrganisation() {
        return organisation;
    }

    /**
     * 
     * @param organisation
     *     The organisation
     */
    @JsonProperty("organisation")
    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    public Contact withOrganisation(String organisation) {
        this.organisation = organisation;
        return this;
    }

    /**
     * 
     * @return
     *     The notes
     */
    @JsonProperty("notes")
    public String getNotes() {
        return notes;
    }

    /**
     * 
     * @param notes
     *     The notes
     */
    @JsonProperty("notes")
    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Contact withNotes(String notes) {
        this.notes = notes;
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

    public Contact withVersion(Integer version) {
        this.version = version;
        return this;
    }

    /**
     * 
     * @return
     *     The contactAddresses
     */
    @JsonProperty("contactAddresses")
    public List<ContactAddress> getContactAddresses() {
        return contactAddresses;
    }

    /**
     * 
     * @param contactAddresses
     *     The contactAddresses
     */
    @JsonProperty("contactAddresses")
    public void setContactAddresses(List<ContactAddress> contactAddresses) {
        this.contactAddresses = contactAddresses;
    }

    public Contact withContactAddresses(List<ContactAddress> contactAddresses) {
        this.contactAddresses = contactAddresses;
        return this;
    }

    /**
     * 
     * @return
     *     The phoneNumbers
     */
    @JsonProperty("phoneNumbers")
    public List<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    /**
     * 
     * @param phoneNumbers
     *     The phoneNumbers
     */
    @JsonProperty("phoneNumbers")
    public void setPhoneNumbers(List<PhoneNumber> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public Contact withPhoneNumbers(List<PhoneNumber> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
        return this;
    }

    /**
     * 
     * @return
     *     The emails
     */
    @JsonProperty("emails")
    public List<Email> getEmails() {
        return emails;
    }

    /**
     * 
     * @param emails
     *     The emails
     */
    @JsonProperty("emails")
    public void setEmails(List<Email> emails) {
        this.emails = emails;
    }

    public Contact withEmails(List<Email> emails) {
        this.emails = emails;
        return this;
    }

    /**
     * 
     * @return
     *     The internetIdentifiers
     */
    @JsonProperty("internetIdentifiers")
    public List<InternetIdentifier> getInternetIdentifiers() {
        return internetIdentifiers;
    }

    /**
     * 
     * @param internetIdentifiers
     *     The internetIdentifiers
     */
    @JsonProperty("internetIdentifiers")
    public void setInternetIdentifiers(List<InternetIdentifier> internetIdentifiers) {
        this.internetIdentifiers = internetIdentifiers;
    }

    public Contact withInternetIdentifiers(List<InternetIdentifier> internetIdentifiers) {
        this.internetIdentifiers = internetIdentifiers;
        return this;
    }

    /**
     * The links of a contact
     * 
     * @return
     *     The Links
     */
    @JsonProperty("_links")
    public ContactLinks getLinks() {
        return Links;
    }

    /**
     * The links of a contact
     * 
     * @param Links
     *     The _links
     */
    @JsonProperty("_links")
    public void setLinks(ContactLinks Links) {
        this.Links = Links;
    }

    public Contact withLinks(ContactLinks Links) {
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

    public Contact withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}
