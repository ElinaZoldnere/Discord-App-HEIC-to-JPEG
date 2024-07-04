import functions_framework
import pyheif
from PIL import Image
import io
from flask import send_file, request, abort

import os
SECRET_TOKEN = os.getenv('SECRET_TOKEN')

@functions_framework.http
def convert_heic_to_jpg(request):
    if request.headers.get('Authorization') != f"Bearer {SECRET_TOKEN}":
        abort(403)

    if 'file' not in request.files:
        return 'No file part', 400

    file = request.files['file']

    if file.filename == '':
        return 'No selected file', 400

    try:
        heif_file = pyheif.read_heif(io.BytesIO(file.read()))
        image = Image.frombytes(
            heif_file.mode,
            heif_file.size,
            heif_file.data,
            "raw",
            heif_file.mode,
            heif_file.stride,
        )

        output = io.BytesIO()
        image.save(output, format="JPEG")  # Use 'JPEG' instead of 'JPG'
        output.seek(0)

        return send_file(output, mimetype='image/jpeg', as_attachment=True, download_name='converted.jpg')

    except Exception as e:
        return str(e), 500