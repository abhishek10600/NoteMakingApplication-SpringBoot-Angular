package com.DXCTrainingProject.NoteMakingApplication.Service;

import com.DXCTrainingProject.NoteMakingApplication.DTO.NoteDTO;
import com.DXCTrainingProject.NoteMakingApplication.DTO.NoteResponseDTO;

import java.util.List;

public interface NoteService {

    NoteResponseDTO createNote(NoteDTO noteDTO);
    List<NoteResponseDTO> getAllNotes();
    NoteResponseDTO updateNote(Long noteId, NoteDTO noteDTO);
    NoteResponseDTO updateNoteStatus(Long noteId);
    String deleteNote(Long noteId);
    List<NoteResponseDTO> searchNotes(String keyword);
    List<NoteResponseDTO> getAllNotesOfUser();
}
