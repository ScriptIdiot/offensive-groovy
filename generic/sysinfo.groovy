def getSystemInfo() {
    def arch = System.getProperty('os.arch')
    def os = System.getProperty('os.name')
    def version = System.getProperty('os.version')

    return "OS: ${os}, Version: ${version}, Architecture: ${arch}"
}

println getSystemInfo()
