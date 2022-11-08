package com.proyecto.laligapremier;

import com.proyecto.laligapremier.models.dao.IUsuarioDao;
import com.proyecto.laligapremier.models.entity.Usuario;
import com.proyecto.laligapremier.service.IUploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Clase principal de la aplicación
 */

@SpringBootApplication
public class LaligapremierApplication implements CommandLineRunner {

	/**
	 * Inyección de la clase IUploadFileService para acceder a sus métodos.
	 */
	@Autowired
	private IUploadFileService uploadFileService;

	/**
	 * Método main que corre la aplicación.
	 */
	public static void main(String[] args) {
		SpringApplication.run(LaligapremierApplication.class, args);
	}

	/**
	 * Método que elimina las imágenes agregadas en la sesión anterior y vuelve a crear el directorio.
	 */
	@Override
	public void run(String... args) throws Exception {
		uploadFileService.deleteAll();
		uploadFileService.init();
	}

	/**
	 * Método que registra el usuario Administrador con sus respectivos parámetros,
	 * se ejecuta después de que se haya creado el contexto de app y antes de que se ejecute la app principal.
	 * @param usuarios Objeto del tipo IUsuarioDao, usado para acceder a la base de datos y guardar el usuario admin.
	 * @param encoder Objeto del tipo PasswordEncoder, usado para encriptar la contraseña.
	 * @return Argumentos para ejecutar en la app.
	 */
	@Bean
	CommandLineRunner commandLineRunner(IUsuarioDao usuarios, PasswordEncoder encoder) {
		return args -> {

			usuarios.save(new Usuario("admin","admin@admin.cl", encoder.encode("password"), "ROLE_USER,ROLE_ADMIN"));

		};
	}
}
