import http.client

conn = http.client.HTTPConnection("localhost", port=5000)
conn.request("GET", "/files")
r1 = conn.getresponse()
print(r1.status, r1.reason)
data1 = r1.read()
print(data1)
conn.close()