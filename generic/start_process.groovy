def startBackgroundProcess(String command) {
    def processBuilder = new ProcessBuilder(command.split(' '))
    def process = processBuilder.start()
    return process
}

// Example usage
def process = startBackgroundProcess('calc.exe')
