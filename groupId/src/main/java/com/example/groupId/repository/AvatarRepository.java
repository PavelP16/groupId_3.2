package com.example.groupId.repository;

import com.example.groupId.model.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {
    Optional<Avatar> getByStudentId(long studentId);


}