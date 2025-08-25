-- Datos de prueba para la aplicación SportWin

-- Insertar deportes
INSERT INTO deporte (nombre_deporte, descripcion) VALUES 
('Fútbol', 'Deporte de equipo con 11 jugadores por lado'),
('Baloncesto', 'Deporte de equipo con 5 jugadores por lado'),
('Tenis', 'Deporte individual o de parejas');

-- Insertar equipos
INSERT INTO equipo (id_deporte, nombre, tipo) VALUES 
(1, 'Real Madrid', 'equipo'),
(1, 'Barcelona', 'equipo'),
(1, 'Manchester United', 'equipo'),
(1, 'Liverpool', 'equipo');

-- Insertar competiciones
INSERT INTO competicion (id_deporte, nombre_competicion, lugar, temporada_actual, estado, fecha_inicio, fecha_fin, descripcion) VALUES 
(1, 'La Liga', 'España', '2024-2025', 'activa', '2024-08-15', '2025-05-25', 'Liga española de fútbol'),
(1, 'Premier League', 'Inglaterra', '2024-2025', 'activa', '2024-08-10', '2025-05-20', 'Liga inglesa de fútbol');

-- Insertar eventos deportivos
INSERT INTO evento_deportivo (id_competicion, fecha_evento, equipo_local, equipo_visitante, estado, hora_evento) VALUES 
(1, '2024-12-25', 1, 2, 'pendiente', '20:00:00'),
(1, '2024-12-26', 3, 4, 'pendiente', '21:00:00');

-- Insertar usuarios
INSERT INTO usuario (nombre, apellido, fecha_nacimiento, cedula, correo, username, contrasena, estado, direccion, telefono, saldo, fecha_registro) VALUES 
('Juan', 'Pérez', '1990-05-15', 12345678, 'juan.perez@email.com', 'juanperez', 'password123', 'Activo', 'Calle 123 #45-67', '3001234567', 1000000.00, '2024-01-15'),
('María', 'García', '1985-08-22', 87654321, 'maria.garcia@email.com', 'mariagarcia', 'password456', 'Activo', 'Carrera 78 #90-12', '3009876543', 500000.00, '2024-02-20');

-- Insertar cuotas
INSERT INTO cuota (id_evento, descripcion_cuota, valor_cuota, estado_cuota, fecha_apertura, fecha_cierre, fecha_actualizacion) VALUES 
(1, 'Victoria del equipo local', 2.50, 'activa', '2024-12-20 10:00:00', '2024-12-25 19:30:00', '2024-12-20 10:00:00'),
(1, 'Victoria del equipo visitante', 3.20, 'activa', '2024-12-20 10:00:00', '2024-12-25 19:30:00', '2024-12-20 10:00:00'),
(1, 'Empate', 3.50, 'activa', '2024-12-20 10:00:00', '2024-12-25 19:30:00', '2024-12-20 10:00:00');
