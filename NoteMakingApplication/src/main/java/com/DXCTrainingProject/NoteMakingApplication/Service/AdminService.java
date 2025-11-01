package com.DXCTrainingProject.NoteMakingApplication.Service;

import com.DXCTrainingProject.NoteMakingApplication.DTO.AdminNoteResponseDTO;
import com.DXCTrainingProject.NoteMakingApplication.DTO.UserResponseDTO;
import com.DXCTrainingProject.NoteMakingApplication.Entity.User;

import java.util.List;

public interface AdminService {

    List<UserResponseDTO> getAllUsers();
    List<AdminNoteResponseDTO> getAllNotes();
    String deleteUser(Long id);
    String deleteNote(Long id);
}
