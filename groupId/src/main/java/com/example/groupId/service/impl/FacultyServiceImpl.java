package com.example.groupId.service.impl;


import com.example.groupId.exception.FacultyNotFoundException;
import com.example.groupId.model.Faculty;

import com.example.groupId.model.Student;
import com.example.groupId.repository.FacultyRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Faculty addFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty removeFaculty(long id) {
        Faculty faculty = facultyRepository.findById(id).orElseThrow(()-> new FacultyNotFoundException(id));
        facultyRepository.delete(faculty);
        return faculty;
    }

    @Override
    public Faculty findFaculty(long id) {
        return facultyRepository.findById(id).orElseThrow(()-> new FacultyNotFoundException(id));
    }

    @Override
    public void updateFaculty(long id, Faculty facultyForUpdate) {
        if (!facultyRepository.existsById(id)) {
            throw new RuntimeException();
        }
        facultyForUpdate.setId(id);
        facultyRepository.save(facultyForUpdate);

    }

    @Override
    public List<Faculty> getAllByColor(String color) {
        return facultyRepository.findAll().stream()
                .filter(faculty -> faculty.getColor().equals(color))
                .toList();
    }

    @Override
    public List<Faculty> getFacultyByColorOrName(String color,String name){
        return facultyRepository.findByColorIgnoreCaseOrNameIgnoreCase(color,name);
    }



}
