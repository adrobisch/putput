package org.putput.notes;

import org.putput.api.model.Note;
import org.putput.common.UuidService;
import org.putput.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class NoteService {
  @Autowired
  NoteRepository noteRepository;

  @Autowired
  UserRepository userRepository;

  @Autowired
  UuidService uuidService;

  public void detete(String id) {
    noteRepository.delete(id);
  }

  public NoteEntity newNote(String username, String shortText, String content) {
    NoteEntity newNote = new NoteEntity();
    newNote.setId(uuidService.uuid());
    newNote.setShortText(shortText);
    newNote.setContent(content);
    newNote.setUser(userRepository.findByUsername(username));

    noteRepository.save(newNote);

    return newNote;
  }

  public NoteEntity getNote(String id) {
    return noteRepository.findOne(id);
  }

  public void update(Note note) {
    NoteEntity noteInRepository = noteRepository.findOne(note.getId());
    noteInRepository.setContent(note.getContent());
    noteInRepository.setShortText(note.getShortText());
    noteRepository.save(noteInRepository);
  }

  public ListOfNotes getByUserName(String username) {
    return new ListOfNotes(noteRepository.findByUserName(username));
  }
}
