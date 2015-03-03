package org.putput.notes;

import ma.glasnost.orika.MapperFacade;
import org.putput.api.model.NoteLinks;
import org.putput.api.resource.Note;
import org.putput.common.web.BaseResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoteResource extends BaseResource implements Note {

  @Autowired
  NoteService noteService;

  @Autowired
  MapperFacade mapper;

  @Override
  public PostNoteResponse postNote(org.putput.api.model.Note note) throws Exception {
    NoteEntity newNote = noteService.newNote(user().getUsername(), note.getShortText(), note.getContent());
    return PostNoteResponse.withCreated(link(Note.class, newNote.getId()).getHref());
  }

  @Override
  public GetNoteByIdResponse getNoteById(String id) throws Exception {
    return GetNoteByIdResponse.withHaljsonOK(
      mapper.map(noteService.getNote(id), org.putput.api.model.Note.class)
      .withLinks(new NoteLinks().withSelf(link(Note.class, id)))
    );
  }

  @Override
  public PutNoteByIdResponse putNoteById(String id, org.putput.api.model.Note note) throws Exception {
    noteService.update(note);
    return PutNoteByIdResponse.withOK();
  }

  @Override
  public DeleteNoteByIdResponse deleteNoteById(String id) throws Exception {
    noteService.detete(id);
    return DeleteNoteByIdResponse.withOK();
  }
}
