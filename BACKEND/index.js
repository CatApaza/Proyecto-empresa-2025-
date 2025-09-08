const express = require('express');
const multer = require('multer');
const path = require('path');
const fs = require('fs');
const { validarEmpleado } = require('./face');

const app = express();
app.use(express.json());

// 📂 Configuración de multer (guardar archivos temporales en /uploads)
const upload = multer({ dest: 'uploads/' });

// 📌 Ruta para registrar foto de referencia de un empleado
app.post('/api/empleados/registro/:idEmpleado', upload.single('foto'), (req, res) => {
  const empleadoId = req.params.idEmpleado;

  if (!req.file) {
    return res.status(400).json({ error: 'No se recibió ninguna foto' });
  }

  // 📂 Carpeta destino donde se guardará la foto de referencia
  const dir = path.join(__dirname, 'models', empleadoId);

  // Crear carpeta si no existe
  if (!fs.existsSync(dir)) {
    fs.mkdirSync(dir, { recursive: true });
  }

  // Guardar la foto con nombre único
  const filePath = path.join(dir, Date.now() + path.extname(req.file.originalname));
  fs.renameSync(req.file.path, filePath);

  res.json({ mensaje: `✅ Foto registrada para empleado ${empleadoId}`, ruta: filePath });
});

// 📌 Ruta para validar foto contra la de referencia
app.post('/api/empleados/validar/:idEmpleado', upload.single('foto'), async (req, res) => {
  const empleadoId = req.params.idEmpleado;

  if (!req.file) {
    return res.status(400).json({ error: 'No se recibió ninguna foto' });
  }

  const filePath = req.file.path; // foto temporal en /uploads/

  try {
    const esValido = await validarEmpleado(empleadoId, filePath);

    // Borrar archivo temporal (no se necesita después de validar)
    fs.unlinkSync(filePath);

    if (esValido) {
      return res.json({ exito: true, mensaje: `✅ Empleado ${empleadoId} validado correctamente` });
    } else {
      return res.status(401).json({ exito: false, mensaje: '❌ Validación fallida, rostro no coincide' });
    }
  } catch (error) {
    console.error('❌ Error en validación:', error);
    return res.status(500).json({ error: 'Error en validación', detalle: error.message });
  }
});

// 🚀 Iniciar servidor
const PORT = 3000;
app.listen(PORT, () => {
  console.log(`Servidor corriendo en http://localhost:${PORT}`);
});
