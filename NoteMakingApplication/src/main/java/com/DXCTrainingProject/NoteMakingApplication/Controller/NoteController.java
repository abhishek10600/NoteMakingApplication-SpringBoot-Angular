package com.DXCTrainingProject.NoteMakingApplication.Controller;

import com.DXCTrainingProject.NoteMakingApplication.DTO.NoteDTO;
import com.DXCTrainingProject.NoteMakingApplication.DTO.NoteResponseDTO;
import com.DXCTrainingProject.NoteMakingApplication.Service.NoteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/note")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @PostMapping()
    public ResponseEntity<NoteResponseDTO> createNote(@Valid @RequestBody NoteDTO noteDTO){
        NoteResponseDTO newNoteResponseDTO = noteService.createNote(noteDTO);
        return new ResponseEntity<>(newNoteResponseDTO, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<NoteResponseDTO>> getAllNotes(){
        List<NoteResponseDTO> notesResponseDTO = noteService.getAllNotes();
        return new ResponseEntity<>(notesResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<NoteResponseDTO>> searchNotes(@RequestParam String keyword){
        List<NoteResponseDTO> searchResults = noteService.searchNotes(keyword);
        return new ResponseEntity<>(searchResults, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoteResponseDTO> updateNote(@PathVariable Long id, @Valid @RequestBody NoteDTO noteDTO){
        NoteResponseDTO updateNoteResponseDTO = noteService.updateNote(id, noteDTO);
        return new ResponseEntity<>(updateNoteResponseDTO, HttpStatus.OK);
    }

    @PutMapping("/{id}/update-status")
    public ResponseEntity<NoteResponseDTO> updateNoteStatus(@PathVariable Long id){
        NoteResponseDTO updateNoteResponseDTO = noteService.updateNoteStatus(id);
        return new ResponseEntity<>(updateNoteResponseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNote(@PathVariable Long id){
        String deletedNoteMessage = noteService.deleteNote(id);
        return new ResponseEntity<>(deletedNoteMessage, HttpStatus.NO_CONTENT);
    }

}
