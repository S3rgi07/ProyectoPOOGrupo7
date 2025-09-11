CREATE DATABASE IF NOT EXISTS uvrate_db CHARACTER SET = 'utf8mb4' COLLATE = 'utf8mb4_general_ci';
USE uvrate_db;

CREATE TABLE usuario (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(100) NOT NULL,
  correo VARCHAR(150) NOT NULL UNIQUE,
  contraseña VARCHAR(255) NOT NULL,
  rol VARCHAR(20) NOT NULL DEFAULT 'estudiante',
  creado_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

CREATE TABLE curso (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(150) NOT NULL,
  descripcion TEXT,
  creado_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

CREATE TABLE catedratico (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(150) NOT NULL,
  curso_id INT,
  FOREIGN KEY (curso_id) REFERENCES curso(id) ON DELETE SET NULL
) ENGINE=InnoDB;

CREATE TABLE calificacion (
  id INT AUTO_INCREMENT PRIMARY KEY,
  usuario_id INT NOT NULL,
  catedratico_id INT NOT NULL,
  curso_id INT NOT NULL,
  puntaje TINYINT NOT NULL CHECK (puntaje BETWEEN 1 AND 5),
  comentario TEXT,
  fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE CASCADE,
  FOREIGN KEY (catedratico_id) REFERENCES catedratico(id) ON DELETE CASCADE,
  FOREIGN KEY (curso_id) REFERENCES curso(id) ON DELETE CASCADE,
  UNIQUE (usuario_id, catedratico_id, curso_id)
) ENGINE=InnoDB;

CREATE TABLE upvote (
  id INT AUTO_INCREMENT PRIMARY KEY,
  usuario_id INT NOT NULL,
  catedratico_id INT NOT NULL,
  fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE CASCADE,
  FOREIGN KEY (catedratico_id) REFERENCES catedratico(id) ON DELETE CASCADE,
  UNIQUE (usuario_id, catedratico_id)
) ENGINE=InnoDB;
