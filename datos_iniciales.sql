-- Insertar estudiantes de ejemplo
INSERT INTO estudiante (nombre, carnet, correo) VALUES
('Juan Pérez', 20231234, 'juan.perez@uvg.edu.gt'),
('María López', 20229876, 'maria.lopez@uvg.edu.gt');

-- Insertar cursos de ejemplo
INSERT INTO curso (codigo, nombre, descripcion, competencias, tipo) VALUES
(101, 'Introducción a la Programación', 'Fundamentos básicos de programación', 'Pensamiento lógico, sintaxis básica', 'Obligatorio'),
(102, 'Estructuras de Datos', 'Manejo de colecciones y estructuras', 'Optimización y control de flujo', 'Obligatorio'),
(205, 'Programación Web', 'Tecnologías para desarrollo web', 'HTML, CSS, JS, Servlets', 'Electivo');

-- Insertar catedráticos de ejemplo
INSERT INTO catedratico (id, nombre) VALUES
(1, 'Ing. Carlos Gómez'),
(2, 'Lic. Ana Martínez');

-- Relación Catedrático-Curso
INSERT INTO catedratico_curso (catedratico_id, curso_id) VALUES
(1, 101),
(1, 102),
(2, 205);

-- Insertar un upvote de ejemplo
INSERT INTO upvotes (estudiante_id, catedratico_id, fecha) VALUES
(1, 1, CURRENT_DATE);
