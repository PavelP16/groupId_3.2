package com.example.groupId.service.impl;

import com.example.groupId.repository.AvatarRepository;
import com.example.groupId.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

import static org.apache.commons.lang3.RandomUtils.nextLong;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;


class AvatarServiceImplTest {


    @Test
    void uploadImage() {

    }

    @Test
    void getAvatarFromDB() {
    }

    @Test
    void getAvatarFromLocal() {
    }
}


