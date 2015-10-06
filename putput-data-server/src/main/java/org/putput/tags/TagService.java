package org.putput.tags;

import org.putput.common.UuidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TagService {

    @Autowired
    TagRepository tagRepository;

    @Autowired
    UuidService uuidService;

    public List<TagEntity> getTags(String taggableId) {
        return tagRepository.findByTaggableId(taggableId);
    }

    public TagEntity createTag(String taggableId, String name, String text) {
        return tagRepository.save(new TagEntity()
                .setId(uuidService.uuid())
                .setTaggableId(taggableId)
                .setName(name)
                .setText(text));
    }

    public void deleteTag(String id) {
        tagRepository.delete(id);
    }
}
