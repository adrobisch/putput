package org.putput.notes;

import ma.glasnost.orika.MapperFacade;
import org.putput.api.model.NoteLinks;
import org.putput.api.model.NoteList;
import org.putput.api.model.NoteListLinks;
import org.putput.api.resource.Note;
import org.putput.api.resource.Notes;
import org.putput.common.web.BaseResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class NotesResource extends BaseResource implements Notes {
  @Autowired
  NoteService noteService;

  @Autowired
  MapperFacade mapper;

  @Override
  public GetNotesResponse getNotes(BigDecimal page) throws Exception {
    NoteList noteList = mapper.map(noteService.getByUserName(user().getUsername()), NoteList.class);
    noteList.getNotes().forEach(note -> note.withLinks(new NoteLinks().withSelf(link(Note.class, note.getId()))));

    return GetNotesResponse.withHaljsonOK(noteList.withLinks(new NoteListLinks().withSelf(link(Notes.class))));
  }
}
