package com.proyecto.laligapremier.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Interfaz donde se establecen sus métodos para luego ser implementados en la clase UploadFileServiceImpl.
 */

public interface IUploadFileService {

    Resource load(String filename) throws MalformedURLException;

    String copy(MultipartFile file) throws IOException;

    boolean delete(String filename);

    void deleteAll();

    void init() throws IOException;
}
