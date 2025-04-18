from http.server import HTTPServer, BaseHTTPRequestHandler
import os

class FileReceiver(BaseHTTPRequestHandler):
    def do_POST(self):
        content_length = int(self.headers['Content-Length'])
        file_content = self.rfile.read(content_length)

        with open('received_file.json', 'wb') as f:
            f.write(file_content)

        self.send_response(200)
        self.end_headers()

server = HTTPServer(('0.0.0.0', 8000), FileReceiver)
print("Server started at port 8000")
server.serve_forever()