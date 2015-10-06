package org.putput.tags;

import org.putput.common.persistence.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "PP_TAG")
public class TagEntity extends BaseEntity<TagEntity> {

    @Column(name = "_TAGGABLE_ID")
    String taggableId;

    @Column(name = "_NAME")
    String name;

    @Column(name = "_TEXT")
    String text;

    public String getTaggableId() {
        return taggableId;
    }

    public TagEntity setTaggableId(String taggableId) {
        this.taggableId = taggableId;
        return this;
    }

    public String getName() {
        return name;
    }

    public TagEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getText() {
        return text;
    }

    public TagEntity setText(String text) {
        this.text = text;
        return this;
    }
}
