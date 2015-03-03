package org.putput.notes;

import java.util.List;

public class ListOfNotes {
  List<NoteEntity> notes;

  public ListOfNotes(List<NoteEntity> notes) {
    this.notes = notes;
  }

  public List<NoteEntity> getNotes() {
    return notes;
  }

  public void setNotes(List<NoteEntity> notes) {
    this.notes = notes;
  }
}
