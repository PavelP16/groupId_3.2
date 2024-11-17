package com.example.groupId.model;

import jakarta.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Entity
public class Avatar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String filePath;
    private long fileSize;
    private String mediaType;
    private byte[] data;

    @OneToOne
    private Student student;

    public Avatar(String filePath, long fileSize, String mediaType, byte[] data, Student student) {
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.mediaType = mediaType;
        this.data = data;
        this.student = student;
    }

    public Avatar() {

    }

    // Getters and Setters
    public Long getId() { return id; }
    public String getFilePath() { return filePath; }
    public long getFileSize() { return fileSize; }
    public String getMediaType() { return mediaType; }
    public byte[] getData() { return data; }
    public Student getStudent() { return student; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Avatar avatar)) return false;

        return fileSize == avatar.fileSize &&
                Objects.equals(id, avatar.id) &&
                Objects.equals(filePath, avatar.filePath) &&
                Objects.equals(mediaType, avatar.mediaType) &&
                Arrays.equals(data, avatar.data) &&
                Objects.equals(student, avatar.student);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, filePath, fileSize, mediaType, student);
        result = 31 * result + Arrays.hashCode(data);
        return result;
    }
}