package com.proyecto.laligapremier.service.impl;


import com.proyecto.laligapremier.service.IUploadFileService;
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

/**
 * Implementación de la clase de IUploadFileService, donde se dan lógica a sus métodos.
 */

@Service
public class UploadFileServiceImpl implements IUploadFileService {

    /**
     * Constante utilizada para registrar log en consola.
     */
    private final Logger log = LoggerFactory.getLogger(getClass());

    /**
     * Constante utilizada para indicar la carpeta en la que se guardarán las imágenes.
     */
    private final static String UPLOAD_FOLDER = "uploads";

    /**
     * Método que carga una imagen de una ruta según su nombre.
     * @param filename Objeto del tipo String, usado para buscar una imagen por su nombre en una ruta.
     * @return Objeto del tipo Resource.
     */
    @Override
    public Resource load(String filename) throws MalformedURLException {
        Path pathImagen = getPath(filename);
        log.info("pathImagen " + pathImagen);
        Resource recurso;
        recurso = new UrlResource(pathImagen.toUri());
        if(!recurso.exists() && !recurso.isReadable())
            throw new RuntimeException("Error : No se puede cargar la imagen " + pathImagen.toString());

        return recurso;
    }

    /**
     * Método para obtener el nombre de una imagen.
     * @param file Objeto del tipo MultipartFile, usado para buscar una imagen.
     * @return Objeto del tipo String, nombre de la imagen.
     */
    @Override
    public String copy(MultipartFile file) throws IOException {
        String nombreUnico = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path rootPath = getPath(nombreUnico);
        log.info("rootPath: " + rootPath);
        Files.copy(file.getInputStream() , rootPath);
        return nombreUnico;
    }

    /**
     * Método que elimina una imagen de una ruta según su nombre.
     * @param filename Objeto del tipo String, usado para buscar una imagen por su nombre en una ruta.
     * @return Objeto del tipo Boolean.
     */
    @Override
    public boolean delete(String filename) {
        Path rootPath = getPath(filename);
        File archivo = rootPath.toFile();

        if(archivo.exists() && archivo.canRead()){
            if(archivo.delete()) return true;
        }
        return false;
    }

    /**
     * Método que elimina todas las imágenes de una ruta.
     */
    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(Paths.get(UPLOAD_FOLDER).toFile());
    }

    /**
     * Método que crea el directorio con el nombre de la constante.
     */
    @Override
    public void init() throws IOException {
        Files.createDirectory(Paths.get(UPLOAD_FOLDER));

    }

    /**
     * Método que obtiene la ruta de una imagen según su nombre.
     * @param filename Objeto del tipo String, usado para buscar la ruta de la imagen.
     * @return Objeto del tipo Path.
     */
    public Path getPath(String filename){
        return Paths.get(UPLOAD_FOLDER).resolve(filename).toAbsolutePath();
    }
}
