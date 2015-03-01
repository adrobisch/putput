package org.putput.notes;

import org.putput.common.UuidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoteService {
  @Autowired
  NoteRepository noteRepository;

  @Autowired
  UuidService uuidService;

  public void detete(String id) {
    noteRepository.delete(id);
  }

  public NoteEntity newNote(String shortText, String content) {
    NoteEntity newNote = new NoteEntity();
    newNote.setId(uuidService.uuid());
    newNote.setShortText(shortText);
    newNote.setContent(content);

    noteRepository.save(newNote);

    return newNote;
  }

  public NoteEntity getNote(String id) {
    return noteRepository.findOne(id);
  }

  public void update(NoteEntity note) {
    noteRepository.save(note);
  }

  public ListOfNotes getByUserName(String username) {
    return new ListOfNotes(noteRepository.findByUserName(username));
  }
}
