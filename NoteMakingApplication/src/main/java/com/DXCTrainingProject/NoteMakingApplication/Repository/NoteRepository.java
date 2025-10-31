package com.DXCTrainingProject.NoteMakingApplication.Repository;

import com.DXCTrainingProject.NoteMakingApplication.Entity.Note;
import com.DXCTrainingProject.NoteMakingApplication.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    @Query("SELECT n FROM Note n WHERE n.user = :user AND (LOWER(n.noteTitle) LIKE LOWER(CONCAT('%', :keyword, '%')) " + "OR LOWER(n.noteDescription) LIKE LOWER (CONCAT('%', :keyword, '%')) " + "OR LOWER(n.tag) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<Note> searchNotesByUser(@Param("user") User user, @Param("keyword") String keyword);

    List<Note> findByUser(User user);
}
