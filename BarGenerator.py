import barcode
from barcode.writer import ImageWriter
import os

def generate_barcode(color, size, product_id, output_dir="barcodes"):
    """
    Genera un código de barras con los datos proporcionados y lo guarda como imagen.
    
    :param color: Color del producto (str)
    :param size: Talla del producto (str)
    :param product_id: ID único del producto (str)
    :param output_dir: Directorio de salida para guardar las imágenes (str)
    :return: Ruta completa del archivo generado (str)
    """
    # Combina los datos en un formato legible
    data = f"{product_id}-{color}-{size}"
    
    # Usa EAN13 como formato para el código de barras
    ean = barcode.get_barcode_class('code128')  # Code128 permite cadenas alfanuméricas
    barcode_instance = ean(data, writer=ImageWriter())
    
    # Crea el directorio de salida si no existe
    if not os.path.exists(output_dir):
        os.makedirs(output_dir)
    
    # Define el nombre del archivo de salida
    output_path = os.path.join(output_dir, f"{data}.png")
    
    # Guarda el código de barras como imagen
    barcode_instance.save(output_path)
    print(f"Código de barras guardado en: {output_path}")
    return output_path

# Datos de prueba
products = [
    {"color": "Red", "size": "M", "id": "123456"},
    {"color": "Blue", "size": "L", "id": "123457"},
    {"color": "Green", "size": "S", "id": "123458"}
]

# Genera los códigos de barras para los productos
for product in products:
    generate_barcode(product["color"], product["size"], product["id"])
