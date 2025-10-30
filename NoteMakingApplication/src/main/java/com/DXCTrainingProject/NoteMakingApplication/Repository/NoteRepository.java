package com.DXCTrainingProject.NoteMakingApplication.Repository;

import com.DXCTrainingProject.NoteMakingApplication.Entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    @Query("SELECT n FROM Note n WHERE LOWER(n.noteTitle) LIKE LOWER(CONCAT('%', :keyword, '%')) " + "OR LOWER(n.noteDescription) LIKE LOWER(CONCAT('%', :keyword, '%')) " + "OR LOWER(n.tag) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Note> searchNotes(@Param("keyword") String keyword);
}
