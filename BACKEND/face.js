const faceapi = require('@vladmandic/face-api');
const canvas = require('canvas');
const fs = require('fs');
const path = require('path');

// Patch para usar canvas en Node.js
const { Canvas, Image, ImageData } = canvas;
faceapi.env.monkeyPatch({ Canvas, Image, ImageData });

// 📂 Carpeta con fotos registradas de empleados
const EMPLEADOS_PATH = path.join(__dirname, 'models');

// Cargar modelos de face-api
async function loadModels() {
  await faceapi.nets.ssdMobilenetv1.loadFromDisk('./models');
  await faceapi.nets.faceRecognitionNet.loadFromDisk('./models');
  await faceapi.nets.faceLandmark68Net.loadFromDisk('./models');
}

// Cargar descriptores de empleados registrados
async function cargarEmpleados() {
  const labels = fs.readdirSync(EMPLEADOS_PATH); // cada carpeta = empleado
  return Promise.all(labels.map(async (label) => {
    const descriptions = [];
    const files = fs.readdirSync(path.join(EMPLEADOS_PATH, label));
    for (let file of files) {
      const img = await canvas.loadImage(path.join(EMPLEADOS_PATH, label, file));
      const detections = await faceapi.detectSingleFace(img).withFaceLandmarks().withFaceDescriptor();
      if (detections) {
        descriptions.push(detections.descriptor);
      }
    }
    return new faceapi.LabeledFaceDescriptors(label, descriptions);
  }));
}

// Validar si la foto coincide con empleadoId
async function validarEmpleado(empleadoId, filePath) {
  await loadModels();
  const empleados = await cargarEmpleados();
  const faceMatcher = new faceapi.FaceMatcher(empleados, 0.6);

  const img = await canvas.loadImage(filePath);
  const detections = await faceapi.detectSingleFace(img).withFaceLandmarks().withFaceDescriptor();

  if (!detections) return false;

  const bestMatch = faceMatcher.findBestMatch(detections.descriptor);

  return bestMatch.label === empleadoId; // el folder del empleado debe tener el mismo nombre que su ID
}

module.exports = { validarEmpleado };
