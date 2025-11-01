package com.DXCTrainingProject.NoteMakingApplication.Service;

import ExHandler.NoteNotFoundException;
import com.DXCTrainingProject.NoteMakingApplication.DTO.AdminNoteResponseDTO;
import com.DXCTrainingProject.NoteMakingApplication.DTO.UserResponseDTO;
import com.DXCTrainingProject.NoteMakingApplication.Entity.Note;
import com.DXCTrainingProject.NoteMakingApplication.Entity.User;
import com.DXCTrainingProject.NoteMakingApplication.Repository.NoteRepository;
import com.DXCTrainingProject.NoteMakingApplication.Repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<UserResponseDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(user -> modelMapper.map(user, UserResponseDTO.class)).toList();
    }

    @Override
    public List<AdminNoteResponseDTO> getAllNotes() {

        // Inline ModelMapper mapping for nested user
        modelMapper.typeMap(Note.class, AdminNoteResponseDTO.class).addMappings(mapper -> {
            mapper.map(src -> src.getUser().getId(), (dest, v) -> dest.getUser().setId((Long) v));
            mapper.map(src -> src.getUser().getUsername(), (dest, v) -> dest.getUser().setUsername((String) v));
            mapper.map(src -> src.getUser().getRoles(), (dest, v) -> dest.getUser().setRoles((String) v));
        });

        List<AdminNoteResponseDTO> notes = noteRepository.findAll()
                .stream()
                .map(note -> {
                    AdminNoteResponseDTO dto = modelMapper.map(note, AdminNoteResponseDTO.class);
                    // safely handle nulls
                    if (note.getUser() == null) {
                        dto.setUser(null);
                    }
                    return dto;
                })
                .toList();

        return notes;
    }

    @Override
    public String deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(()-> new RuntimeException("User not found"));
        userRepository.delete(user);
        return "User deleted successfully";
    }

    @Override
    public String deleteNote(Long id) {
        Note note = noteRepository.findById(id).orElseThrow(()-> new NoteNotFoundException("Note not found"));
        noteRepository.delete(note);
        return "Note deleted successfully";
    }
}
