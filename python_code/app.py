import os
import json
from flask import Flask, request
from flask import make_response
import flask

from PIL import Image
import io

app = Flask(__name__)

config = {
    'filedir': os.path.join(os.curdir, 'files')
}

@app.route('/form')
def form_html():
    return app.send_static_file('form.html')

# Statyczna informacja o aplikacji
@app.route('/')
def root():
    return "Sample REST API flask application!"


#Uzyskanie zawartości katalogu (w formie text/json)
@app.route('/files')
def files():

    files = os.listdir(config['filedir'])

    return json.dumps(files)

#Uzyskanie zawartości pliku (w formie text/plain)
@app.route('/files/<filename>')
def file_get(filename):
    # Kompozycja ścieżki do wskazanego pliku.
    filepath = os.path.join(config['filedir'], filename)
    inp = open(filepath, 'rt')
    with inp:
        # Odczytany plik jest reprezentowany przez listę wierszy zakończonych znakami \n
        # Należy go zamienić na jeden ciąg znaków.
        response = make_response("".join(inp.readlines()))
        # Odpowiedź zwracana jest w formie czystego tekstu
        response.mimetype = 'text/plain'
        return response
        
# Zmiana zawartości pliku
@app.route('/files/<filename>', methods = ['POST'])
def file_post(filename):
    filebody = request.get_data(as_text=True)
    filepath = os.path.join(config['filedir'], filename)
    out = open(filepath, 'wt')
    with out:
        out.writelines(filebody)

        return {"Status": "OK"}


@app.route('/rotate/<angle>', methods = ['POST'])
def img_rotate(angle):
    #buf = request.get_data(as_text=False)
    file = request.files.get('image')
    format = request.mimetype.split("/")[1]
    img = Image.open(file)
    #img = Image.frombytes(mode="L", size=(1500, 500), data=buf, )
 
 
    memfile = io.BytesIO()
    img.rotate(angle=int(angle), expand=True).save(memfile, format="png")
    memfile.seek(0, io.SEEK_SET)
    return flask.send_file(memfile, mimetype="image/png")