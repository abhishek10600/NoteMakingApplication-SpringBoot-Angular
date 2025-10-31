package com.DXCTrainingProject.NoteMakingApplication.Service;

import ExHandler.NoteNotFoundException;
import com.DXCTrainingProject.NoteMakingApplication.DTO.NoteDTO;
import com.DXCTrainingProject.NoteMakingApplication.DTO.NoteResponseDTO;
import com.DXCTrainingProject.NoteMakingApplication.Entity.Note;
import com.DXCTrainingProject.NoteMakingApplication.Entity.User;
import com.DXCTrainingProject.NoteMakingApplication.Repository.NoteRepository;
import com.DXCTrainingProject.NoteMakingApplication.Repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private UserRepository userRepository;

    private User getCurrentuser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if(principal instanceof UserDetails){
            username = ((UserDetails) principal).getUsername();
        }else{
            username = principal.toString();
        }
        return userRepository.findByUsername(username).orElseThrow(()-> new RuntimeException("Authenticated user not found"));
    }

    @Override
    public NoteResponseDTO createNote(NoteDTO noteDTO) {
        User user = getCurrentuser();
        Note note = modelMapper.map(noteDTO, Note.class);
        note.setUser(user);
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
        User user = getCurrentuser();
        Note noteFromDb = noteRepository.findById(noteId).orElseThrow(() -> new NoteNotFoundException("Note not found"));

        if(!noteFromDb.getUser().getId().equals(user.getId())){
            throw new NoteNotFoundException("Note not found for this user");
        }

        noteFromDb.setNoteTitle(noteDTO.getNoteTitle());
        noteFromDb.setNoteDescription(noteDTO.getNoteDescription());
        noteFromDb.setTag(noteDTO.getTag());

        Note updatedNote = noteRepository.save(noteFromDb);

        NoteResponseDTO updateNoteResponseDTO = modelMapper.map(updatedNote, NoteResponseDTO.class);

        return updateNoteResponseDTO;
    }

    @Override
    public NoteResponseDTO updateNoteStatus(Long noteId) {
        User user = getCurrentuser();

        Note noteFromDb = noteRepository.findById(noteId).orElseThrow(() -> new NoteNotFoundException("Note not found"));

        if(!noteFromDb.getUser().getId().equals(user.getId())){
            throw new NoteNotFoundException("Note not found for this user");
        }

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
        User user = getCurrentuser();
        Note note = noteRepository.findById(noteId).orElseThrow(() -> new NoteNotFoundException("Note with this id not found."));

        if (!note.getUser().getId().equals(user.getId())) {
            throw new NoteNotFoundException("Note not found for this user");
        }

        noteRepository.delete(note);
        return "Note delete successfully.";
    }

    @Override
    public List<NoteResponseDTO> searchNotes(String keyword) {
        User user = getCurrentuser();
        List<Note> notes = noteRepository.searchNotesByUser(user, keyword);
        List<NoteResponseDTO> notesResponseDTOS = notes.stream().map(note -> modelMapper.map(note, NoteResponseDTO.class)).toList();
        return notesResponseDTOS;
    }

    @Override
    public List<NoteResponseDTO> getAllNotesOfUser() {

        User user = getCurrentuser();
        List<Note> notes = noteRepository.findByUser(user);
        return notes.stream().map(note -> modelMapper.map(note, NoteResponseDTO.class)).toList();
    }


}
