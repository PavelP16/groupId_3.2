package com.example.groupId.service.impl;

import com.example.groupId.model.Faculty;

import java.util.List;

public interface FacultyService {
    Faculty addFaculty(Faculty faculty);

    Faculty removeFaculty(long id);

    Faculty findFaculty (long id);

    void updateFaculty(long id, Faculty facultyForUpdate);

    List<Faculty> getAllByColor(String color);
}
