@Grab(group='net.java.dev.jna', module='jna', version='5.12.1')
import com.sun.jna.Native
import com.sun.jna.Library
import com.sun.jna.Pointer

interface CustomLibrary extends Library {
    CustomLibrary INSTANCE = Native.load("|ARG_0|", CustomLibrary.class)

    void |ARG_1|()
}

try {
    CustomLibrary.INSTANCE.|ARG_1|()
} catch (Exception e) {
    println "Error: ${e.message}"
}
