import tkinter as tk
from tkinter import filedialog, messagebox
import barcode
from barcode.writer import ImageWriter
import os

def generate_barcode(color, size, product_id, output_dir="barcodes"):
    try:
        data = f"{product_id}-{color}-{size}"
        ean = barcode.get_barcode_class('code128')
        barcode_instance = ean(data, writer=ImageWriter())
        
        if not os.path.exists(output_dir):
            os.makedirs(output_dir)
        
        output_path = os.path.join(output_dir, f"{data}.png")
        barcode_instance.save(output_path)
        return output_path
    except Exception as e:
        return str(e)

def on_generate_click():
    color = entry_color.get()
    size = entry_size.get()
    product_id = entry_id.get()

    if not color or not size or not product_id:
        messagebox.showerror("Error", "Todos los campos son obligatorios.")
        return

    output_dir = filedialog.askdirectory(title="Selecciona el directorio de salida")
    if not output_dir:
        return

    result = generate_barcode(color, size, product_id, output_dir)
    if os.path.exists(result):
        messagebox.showinfo("Éxito", f"Código de barras generado en:\n{result}")
    else:
        messagebox.showerror("Error", f"Fallo al generar el código de barras:\n{result}")

# Crear la ventana principal
root = tk.Tk()
root.title("Generador de Códigos de Barras")

# Crear etiquetas y entradas
tk.Label(root, text="Color del producto:").grid(row=0, column=0, padx=10, pady=5)
entry_color = tk.Entry(root)
entry_color.grid(row=0, column=1, padx=10, pady=5)

tk.Label(root, text="Talla del producto:").grid(row=1, column=0, padx=10, pady=5)
entry_size = tk.Entry(root)
entry_size.grid(row=1, column=1, padx=10, pady=5)

tk.Label(root, text="ID del producto:").grid(row=2, column=0, padx=10, pady=5)
entry_id = tk.Entry(root)
entry_id.grid(row=2, column=1, padx=10, pady=5)

# Botón para generar
generate_button = tk.Button(root, text="Generar Código de Barras", command=on_generate_click)
generate_button.grid(row=3, column=0, columnspan=2, pady=20)

# Ejecutar la aplicación
root.mainloop()
