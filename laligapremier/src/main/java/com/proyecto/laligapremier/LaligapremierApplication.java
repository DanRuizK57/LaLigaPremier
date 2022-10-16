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

@SpringBootApplication
public class LaligapremierApplication implements CommandLineRunner {
	@Autowired
	IUploadFileService uploadFileService;

	public static void main(String[] args) {
		SpringApplication.run(LaligapremierApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		uploadFileService.deleteAll();
		uploadFileService.init();
	}

	// Permite ejecutar código con interfaz funcional
	// Se ejecuta después de que se haya creado el contexto de app y antes de que se ejecute la app principal
	// Es como un import.sql
	@Bean
	CommandLineRunner commandLineRunner(IUsuarioDao usuarios, PasswordEncoder encoder) {
		return args -> {

			usuarios.save(new Usuario("admin","admin@admin.cl", encoder.encode("password"), "ROLE_USER,ROLE_ADMIN"));

		};
	}
}
