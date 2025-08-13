// index.js
const express = require('express');
const cors = require('cors');
const dotenv = require('dotenv');
const db = require('./db');

dotenv.config();

const app = express();
const PORT = process.env.PORT || 3000;

// Middleware
app.use(cors());
app.use(express.json());

// Ruta de prueba
app.get('/', (req, res) => {
  res.send('Servidor de asistencia funcionando ✅');
});

// 📌 Registro de usuario
app.post('/api/registro', async (req, res) => {
  const { nombre, correo, contrasena, rol } = req.body;

  if (!nombre || !correo || !contrasena || !rol) {
    return res.status(400).json({ error: 'Todos los campos son obligatorios' });
  }

  try {
    const result = await db.query(
      'INSERT INTO usuarioss (nombre, correo, contrasena, rol) VALUES ($1, $2, $3, $4) RETURNING *',
      [nombre.trim(), correo.trim().toLowerCase(), contrasena.trim(), rol]
    );

    res.status(201).json({
      mensaje: 'Usuario registrado',
      usuario: result.rows[0]
    });

  } catch (error) {
    console.error('❌ Error al registrar usuario:', error);

    if (error.code === '23505') {
      return res.status(400).json({
        error: 'El correo ya está registrado',
        detalle: error.detail || error.message
      });
    }

    res.status(500).json({
      error: 'Error al registrar usuario',
      detalle: error.message
    });
  }
});

// 📌 Inicio de sesión (Login)
app.post('/api/login', async (req, res) => {
  let { correo, contrasena } = req.body;

  // Limpiar datos de entrada
  correo = (correo || '').trim().toLowerCase();
  contrasena = (contrasena || '').trim();

  console.log("📥 Datos recibidos en login:", correo, contrasena);

  if (!correo || !contrasena) {
    return res.status(400).json({ error: 'Correo y contraseña son obligatorios' });
  }

  try {
    // Buscar usuario por correo y contraseña
    const result = await db.query(
      'SELECT * FROM usuarioss WHERE LOWER(correo) = $1 AND contrasena = $2',
      [correo, contrasena]
    );

    console.log("📤 Resultado query:", result.rows);

    if (result.rows.length === 0) {
      return res.status(401).json({ error: 'Credenciales incorrectas' });
    }

    res.status(200).json({
      mensaje: 'Inicio de sesión exitoso',
      usuario: result.rows[0]
    });

  } catch (error) {
    console.error('❌ Error en inicio de sesión:', error);
    res.status(500).json({
      error: 'Error al iniciar sesión',
      detalle: error.message
    });
  }
});

// Iniciar servidor
app.listen(PORT, '0.0.0.0', () => {
  console.log(`Servidor corriendo en http://0.0.0.0:${PORT}`);
});
