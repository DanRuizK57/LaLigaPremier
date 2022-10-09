package com.proyecto.laligapremier.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class UploadFileServiceImpl implements IUploadFileService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final static String UPLOAD_FOLDER = "uploads";

    @Override
    public Resource load(String filename) throws MalformedURLException {
        Path pathImagen = getPath(filename);
        log.info("pathImagen " + pathImagen);
        Resource recurso = null;
        recurso = new UrlResource(pathImagen.toUri());
        if(!recurso.exists() && !recurso.isReadable())
            throw new RuntimeException("Error : No se puede cargar la imagen " + pathImagen.toString());

        return recurso;
    }

    @Override
    public String copy(MultipartFile file) throws IOException {
        String nombreUnico = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path rootPath = getPath(nombreUnico);
        log.info("rootPath: " + rootPath);
        Files.copy(file.getInputStream() , rootPath);
        return nombreUnico;
    }

    @Override
    public boolean delete(String filename) {
        Path rootPath = getPath(filename);
        File archivo = rootPath.toFile();

        if(archivo.exists() && archivo.canRead()){
            if(archivo.delete()) return true;
        }
        return false;
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(Paths.get(UPLOAD_FOLDER).toFile());
    }

    @Override
    public void init() throws IOException {
        Files.createDirectory(Paths.get(UPLOAD_FOLDER));

    }

    public Path getPath(String filename){
        return Paths.get(UPLOAD_FOLDER).resolve(filename).toAbsolutePath();
    }
}
