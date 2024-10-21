@Grab(group='net.java.dev.jna', module='jna', version='5.12.1')
import com.sun.jna.Native
import com.sun.jna.Pointer
import com.sun.jna.ptr.IntByReference
import com.sun.jna.Library

class Constants {
    static final int SC_MANAGER_CREATE_SERVICE = 0x0002
    static final int SERVICE_WIN32_OWN_PROCESS = 0x00000010
    static final int SERVICE_DEMAND_START = 0x00000003
    static final int SERVICE_ERROR_NORMAL = 0x00000001
    static final long STANDARD_RIGHTS_REQUIRED = 0x000F0000L
    static final long SERVICE_ALL_ACCESS = STANDARD_RIGHTS_REQUIRED | 0x0001 | 0x0002 | 0x0004 | 0x0008 | 0x0010 | 0x0020 | 0x0040 | 0x0080 | 0x0100
}

interface Advapi32 extends Library {
    Advapi32 INSTANCE = Native.load("Advapi32", Advapi32.class)

    Pointer OpenSCManagerA(
            String lpMachineName,
            String lpDatabaseName,
            int dwDesiredAccess
        )

    Pointer CreateServiceA(
            Pointer hSCManager,
            String lpServiceName,
            String lpDisplayName,
            int dwDesiredAccess,
            int dwServiceType,
            int dwStartType,
            int dwErrorControl,
            String lpBinaryPathName,
            String lpLoadOrderGroup,
            IntByReference lpdwTagId,
            String lpDependencies,
            String lpServiceStartName,
            String lpPassword
        )

    boolean StartServiceW(Pointer hService, int dwNumServiceArgs, Pointer lpServiceArgVectors)
    boolean CloseServiceHandle(Pointer hSCObject)
}

    interface Kernel32 extends Library {
        Kernel32 INSTANCE = Native.load("Kernel32", Kernel32.class)
        int GetLastError()
    }

Pointer openSCManager() {
    Pointer hSCManager = Advapi32.INSTANCE.OpenSCManagerA(null, null, Constants.SC_MANAGER_CREATE_SERVICE)
    if (hSCManager == null) {
        throw new RuntimeException("Failed to open SCManager. Error: " + Kernel32.INSTANCE.GetLastError())
    }
    return hSCManager
}

Pointer createService(Pointer hSCManager, String serviceName, String displayName, String binaryPath) {
    Pointer hService = Advapi32.INSTANCE.CreateServiceA(
        hSCManager,
        serviceName,
        displayName,
        (int) Constants.SERVICE_ALL_ACCESS,
        Constants.SERVICE_WIN32_OWN_PROCESS,
        Constants.SERVICE_DEMAND_START,
        Constants.SERVICE_ERROR_NORMAL,
        binaryPath,
        null,
        null,
        null,
        null,
        null
    )

    if (hService == null) {
        throw new RuntimeException("Failed to create service. Error: " + Kernel32.INSTANCE.GetLastError())
    }
    return hService
}

boolean startService(Pointer hService) {
    if (!Advapi32.INSTANCE.StartServiceW(hService, 0, null)) {
        throw new RuntimeException("Failed to start service. Error: " + Kernel32.INSTANCE.GetLastError())
    }
    return true
}

void closeHandle(Pointer handle) {
    if (!Advapi32.INSTANCE.CloseServiceHandle(handle)) {
        throw new RuntimeException("Failed to close handle. Error: " + Kernel32.INSTANCE.GetLastError())
    }
}

try {
    Pointer hSCManager = openSCManager()
    Pointer hService = createService(hSCManager, "JenkinsSvc_a29a772", "Jenkins Background Service", "|ARG_0|")

    if (hService != null) {
        println "Service created successfully!"
        startService(hService)
        println "Service started successfully!"
        closeHandle(hService)
    }

    closeHandle(hSCManager)
} catch (Exception e) {
    println "Error: ${e.message}"
}
