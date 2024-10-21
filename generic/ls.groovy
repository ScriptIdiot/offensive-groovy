def directoryPath = "c:\\"
def directory = new File(directoryPath)

if (directory.exists() && directory.isDirectory()) {
    directory.eachFile { file ->
        println "${file.name.padRight(50)} ${file.isDirectory() ? 'Directory' : 'File'} ${file.length()} bytes"
    }
} else {
    println "The specified directory does not exist or is not accessible."
}