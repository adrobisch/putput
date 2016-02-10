package org.putput.calendar;

import org.putput.common.persistence.BaseEntity;
import org.putput.users.UserEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Optional;

@Entity
@Table(name = "PP_EVENT")
public class EventEntity extends BaseEntity<EventEntity> {
    
    enum Type {
        DEFAULT,
        ALLDAY
    }

    @ManyToOne
    @JoinColumn(name = "_OWNER_ID")
    UserEntity owner;

    @Column(name = "_START")
    Long start;

    @Column(name = "_END")
    Long end;
    
    @Column(name = "_TYPE")
    String type;

    @Column(name = "_RECURRENCE")
    String recurrence;
    
    @Column(name = "_RECURRENCE_END")
    Long recurrenceEnd;

    @Column(name = "_TIMEZONE")
    String timezone;
    
    @Column(name ="_TITLE")
    String title;
    
    @Column(name = "_DESCRIPTION")
    @Lob
    @org.hibernate.annotations.Type(type="org.hibernate.type.StringClobType")
    String description;

    public Long getStart() {
        return start;
    }

    public EventEntity setStart(Long start) {
        this.start = start;
        return this;
    }

    public Long getEnd() {
        return end;
    }

    public EventEntity setEnd(Long end) {
        this.end = end;
        return this;
    }

    public EventEntity setType(Type type) {
        this.type = type.name();
        return this;
    }

    public Type getType() {
        return Type.valueOf(type);
    }

    public String getRecurrence() {
        return recurrence;
    }

    public EventEntity setRecurrence(String recurrence) {
        this.recurrence = recurrence;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public EventEntity setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public EventEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public UserEntity getOwner() {
        return owner;
    }

    public EventEntity setOwner(UserEntity owner) {
        this.owner = owner;
        return this;
    }

    public Optional<Long> getRecurrenceEnd() {
        return Optional.ofNullable(recurrenceEnd);
    }

    public EventEntity setRecurrenceEnd(Long recurrenceEnd) {
        this.recurrenceEnd = recurrenceEnd;
        return this;
    }

    public String getTimezone() {
        return timezone;
    }

    public EventEntity setTimezone(String timezone) {
        this.timezone = timezone;
        return this;
    }
}
