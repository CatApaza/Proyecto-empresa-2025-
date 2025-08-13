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

// Registro de usuario
app.post('/api/registro', async (req, res) => {
  const { nombre, correo, contrasena, rol } = req.body;

  if (!nombre || !correo || !contrasena || !rol) {
    return res.status(400).json({ error: 'Todos los campos son obligatorios' });
  }

  try {
    const result = await db.query(
      'INSERT INTO usuarioss (nombre, correo, contrasena, rol) VALUES ($1, $2, $3, $4) RETURNING *',
      [nombre, correo, contrasena, rol]
    );

    res.status(201).json({
      mensaje: 'Usuario registrado',
      usuario: result.rows[0]
    });

  } catch (error) {
    console.error('❌ Error al registrar usuario:', error);

    // Error de correo duplicado
    if (error.code === '23505') {
      return res.status(400).json({
        error: 'El correo ya está registrado',
        detalle: error.detail || error.message
      });
    }

    // Otros errores
    res.status(500).json({
      error: 'Error al registrar usuario',
      detalle: error.message
    });
  }
});

// Iniciar servidor en todas las interfaces (para acceso desde el emulador Android)
app.listen(PORT, '0.0.0.0', () => {
  console.log(`Servidor corriendo en http://0.0.0.0:${PORT}`);
});
