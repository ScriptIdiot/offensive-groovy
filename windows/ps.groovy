@Grab(group='net.java.dev.jna', module='jna', version='5.12.1')
import com.sun.jna.Native
import com.sun.jna.Pointer
import com.sun.jna.ptr.IntByReference
import com.sun.jna.Library


interface Psapi extends Library {
    Psapi INSTANCE = Native.load("Psapi", Psapi.class)
    boolean EnumProcesses(int[] lpidProcess, int cb, IntByReference lpcbNeeded)
    int GetModuleFileNameExW(Pointer hProcess, Pointer hModule, char[] lpFilename, int nSize)
}

interface Kernel32 extends Library {
    Kernel32 INSTANCE = Native.load("Kernel32", Kernel32.class)
    Pointer OpenProcess(int dwDesiredAccess, boolean bInheritHandle, int dwProcessId)
    boolean CloseHandle(Pointer hObject)
}


List<Integer> getProcessIds() {
    final int PROCESS_ID_ARRAY_SIZE = 1024
    int[] processIds = new int[PROCESS_ID_ARRAY_SIZE]
    IntByReference pcbNeeded = new IntByReference()

    // Call EnumProcesses to get the process IDs
    boolean success = Psapi.INSTANCE.EnumProcesses(processIds, processIds.size() * Integer.BYTES, pcbNeeded)

    if (!success) {
        throw new RuntimeException("Failed to enumerate processes")
    }

    // Calculate the number of process IDs returned
    int count = pcbNeeded.getValue() / Integer.BYTES
    return processIds[0..<count].toList()
}

String getProcessName(int pid) {
    Pointer hProcess = Kernel32.INSTANCE.OpenProcess(0x0400 | 0x0010, false, pid)
    if (hProcess == null) {
        return "Unknown"
    }
    try {
        char[] filename = new char[1024]
        int length = Psapi.INSTANCE.GetModuleFileNameExW(hProcess, null, filename, filename.size())
        String processName = length > 0 ? new String(filename, 0, length) : "Unknown"
        return processName
    } finally {
        Kernel32.INSTANCE.CloseHandle(hProcess)
    }
}

def processIds = getProcessIds()
println String.format("%-10s %s", "PID", "Process Name")
println "-------------------------------------------------------------------------"

processIds.each { pid ->
    def processName = getProcessName(pid)
    println String.format("%-10d %s", pid, processName)
}
