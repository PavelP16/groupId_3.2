package com.example.groupId.repository;

import com.example.groupId.model.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    List<Faculty> findByColorIgnoreCaseOrNameIgnoreCase(String color, String name);

    @Query("SELECT f FROM Faculty f JOIN f.students s WHERE s.id = :studentId")
    Faculty findByStudentId(long studentId);
}
