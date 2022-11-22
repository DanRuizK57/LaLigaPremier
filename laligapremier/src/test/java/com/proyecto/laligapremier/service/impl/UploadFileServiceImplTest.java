package com.proyecto.laligapremier.service.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.net.MalformedURLException;
import java.nio.file.InvalidPathException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UploadFileServiceImplTest {


    @Autowired
    private UploadFileServiceImpl uploadFileService;

    @BeforeEach
    void inicioPrueba(TestInfo testInfo) {
        System.out.println("**** Iniciando " + testInfo.getDisplayName() + " ****");
    }

    @Test
    void load()  {
        assertAll(
                () -> assertThrows(InvalidPathException.class, () -> uploadFileService.load("**")),
                () -> assertThrows(RuntimeException.class, () -> uploadFileService.load("")),
                () -> assertThrows(RuntimeException.class, () -> uploadFileService.load(" ")),
                () -> assertThrows(RuntimeException.class, () -> uploadFileService.load("bb52bf8e-92b7-4def-abf8-e32acd31858c_messi.jpeg"))
        );
    }

    @Test
    void delete() {
        assertAll(
                () -> assertFalse(uploadFileService.delete("rutaInventada")),
                () -> assertFalse(uploadFileService.delete("")),
                () -> assertFalse(uploadFileService.delete(" ")),
                () -> assertTrue(uploadFileService.delete("bb52bf8e-92b7-4def-abf8-e32acd31858c_messi"))

        );
    }

    @AfterEach
    void terminoPrueba(TestInfo testInfo) {
        System.out.println("**** " + testInfo.getDisplayName() + " Finalizado. ****");
    }
}