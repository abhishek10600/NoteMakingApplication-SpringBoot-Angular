package com.DXCTrainingProject.NoteMakingApplication.Service;

import ExHandler.NoteNotFoundException;
import com.DXCTrainingProject.NoteMakingApplication.DTO.NoteDTO;
import com.DXCTrainingProject.NoteMakingApplication.DTO.NoteResponseDTO;
import com.DXCTrainingProject.NoteMakingApplication.Entity.Note;
import com.DXCTrainingProject.NoteMakingApplication.Repository.NoteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private NoteRepository noteRepository;

    @Override
    public NoteResponseDTO createNote(NoteDTO noteDTO) {
        Note note = modelMapper.map(noteDTO, Note.class);
        Note savedNote = noteRepository.save(note);
        return modelMapper.map(savedNote, NoteResponseDTO.class);
    }

    @Override
    public List<NoteResponseDTO> getAllNotes() {
        List<Note> notes = noteRepository.findAll();
        List<NoteResponseDTO> noteResponseDTOS = notes.stream().map(note -> modelMapper.map(note, NoteResponseDTO.class)).toList();
        return noteResponseDTOS;
    }

    @Override
    public NoteResponseDTO updateNote(Long noteId, NoteDTO noteDTO) {
        Note noteFromDb = noteRepository.findById(noteId).orElseThrow(() -> new NoteNotFoundException("Note not found"));
        noteFromDb.setNoteTitle(noteDTO.getNoteTitle());
        noteFromDb.setNoteDescription(noteDTO.getNoteDescription());
        noteFromDb.setTag(noteDTO.getTag());

        Note updatedNote = noteRepository.save(noteFromDb);

        NoteResponseDTO updateNoteResponseDTO = modelMapper.map(updatedNote, NoteResponseDTO.class);

        return updateNoteResponseDTO;
    }

    @Override
    public NoteResponseDTO updateNoteStatus(Long noteId) {
        Note noteFromDb = noteRepository.findById(noteId).orElseThrow(() -> new NoteNotFoundException("Note not found"));
        if (noteFromDb.getIsCompleted() == true) {
            noteFromDb.setIsCompleted(false);
        } else {
            noteFromDb.setIsCompleted(true);
        }

        Note updateNote = noteRepository.save(noteFromDb);

        NoteResponseDTO updateNoteResponse = modelMapper.map(updateNote, NoteResponseDTO.class);

        return updateNoteResponse;
    }

    @Override
    public String deleteNote(Long noteId) {
        Note note = noteRepository.findById(noteId).orElseThrow(() -> new NoteNotFoundException("Note with this id not found."));
        noteRepository.delete(note);
        return "Note delete successfully.";
    }

    @Override
    public List<NoteResponseDTO> searchNotes(String keyword) {
        List<Note> notes = noteRepository.searchNotes(keyword);
        List<NoteResponseDTO> notesResponseDTOS = notes.stream().map(note -> modelMapper.map(note, NoteResponseDTO.class)).toList();
        return notesResponseDTOS;
    }
}
