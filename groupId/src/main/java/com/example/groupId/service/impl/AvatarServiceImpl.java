package com.example.groupId.service.impl;

import com.example.groupId.exception.AvatarNotFoundException;
import com.example.groupId.exception.StudentNotFoundException;
import com.example.groupId.model.Avatar;
import com.example.groupId.model.Student;
import com.example.groupId.repository.AvatarRepository;
import com.example.groupId.repository.StudentRepository;
import org.springdoc.core.converters.models.Pageable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.Buffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
public class AvatarServiceImpl  implements AvatarService {

    @Value("${path.dir}")

    private String pathDir;

    private final StudentRepository studentRepository;

    private final AvatarRepository avatarRepository;

    public AvatarServiceImpl(StudentRepository studentRepository, AvatarRepository avatarRepository) {
        this.studentRepository = studentRepository;
        this.avatarRepository = avatarRepository;
    }

    @Override
    public void uploadImage(long studentId, MultipartFile multipartFile) throws IOException {
        System.out.println(pathDir);

        createDirectory();

        Path filePath = Path.of(pathDir, UUID.randomUUID() + "." + getExtension(multipartFile.getOriginalFilename()));

        createAvatar(studentId, multipartFile, filePath.toString());


        multipartFile.transferTo(filePath);

    }

    @Service
    public class AvatarServiceImpl implements AvatarService {
        private final AvatarRepository avatarRepository;

        public AvatarServiceImpl(AvatarRepository avatarRepository) {
            this.avatarRepository = avatarRepository;
        }

        @Override
        public Page<Avatar> getAvatars(Pageable pageable) {
            return avatarRepository.findAll(pageable);
        }

    @Override
    public Avatar getAvatarFromDB(long studentId) {
        boolean studenExist = studentRepository.existsById(studentId);
        if (!studenExist) {
            throw new StudentNotFoundException(studentId);
        }

        return avatarRepository.getByStudentId(studentId)
                .orElseThrow(AvatarNotFoundException::new);
    }

    @Override
    public byte[] getAvatarFromLocal(long studentId) {
        boolean studenExist = studentRepository.existsById(studentId);
        if (!studenExist) {
            throw new StudentNotFoundException(studentId);
        }
        Avatar avatar = avatarRepository.getByStudentId(studentId)
                .orElseThrow(AvatarNotFoundException::new);
        String filePath = avatar.getFilePath();
        try (BufferedInputStream bufferedOutputStream = new BufferedInputStream(new FileInputStream(filePath))) {
            return bufferedOutputStream.readAllBytes();
        } catch (IOException e) {
            throw new IllegalArgumentException("Чтение картинки не удалось" + e.getMessage());
        }
    }


    private void createAvatar(long studentId, MultipartFile multipartFile, String filePath) throws IOException {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException(studentId));
        avatarRepository.save(new Avatar(
                filePath,
                multipartFile.getSize(),
                multipartFile.getContentType(),
                multipartFile.getBytes(),
                student
        ));

    }

    private String getExtension(String originalPath) {
        return originalPath.substring(originalPath.lastIndexOf(".") + 1);
    }

    private void createDirectory() throws IOException {
        Path path = Path.of(pathDir);
        if (Files.notExists(path)) {
            Files.createDirectories(path);
        }
    }

}
