package com.DXCTrainingProject.NoteMakingApplication.Controller;

import com.DXCTrainingProject.NoteMakingApplication.DTO.AdminNoteResponseDTO;
import com.DXCTrainingProject.NoteMakingApplication.DTO.UserResponseDTO;
import com.DXCTrainingProject.NoteMakingApplication.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers(){
        List<UserResponseDTO> usersDTOs = adminService.getAllUsers();
        return new ResponseEntity<>(usersDTOs, HttpStatus.OK);
    }

    @GetMapping("/notes")
    public ResponseEntity<List<AdminNoteResponseDTO>> getAllNotes(){
        List<AdminNoteResponseDTO> notesDTOs = adminService.getAllNotes();
        return new ResponseEntity<>(notesDTOs, HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        String deleteUserMessage = adminService.deleteUser(id);
        return new ResponseEntity<>(deleteUserMessage, HttpStatus.OK);
    }

    @DeleteMapping("/notes/{id}")
    public ResponseEntity<String> deleteNote(@PathVariable Long id){
        String deleteNoteMessage = adminService.deleteNote(id);
        return new ResponseEntity<>(deleteNoteMessage, HttpStatus.OK);
    }
}
