package com.example.groupId.service.impl;

import com.example.groupId.model.Avatar;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AvatarService {
    void uploadImage(long studentId, MultipartFile multipartFile) throws IOException;

    Avatar getAvatarFromDB(long studentId);

    byte[] getAvatarFromLocal(long studentId);
}
