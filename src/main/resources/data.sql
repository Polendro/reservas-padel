-- Pistas de partida para poder probar la app sin tener que insertarlas a mano.
-- INSERT IGNORE + el UNIQUE en `nombre` evita duplicarlas si el backend se reinicia.
INSERT IGNORE INTO pistas (nombre, activa) VALUES
    ('Pista 1', true),
    ('Pista 2', true),
    ('Pista 3', true);
