import java.net.InetAddress

def hostname = InetAddress.localHost.hostName
println "Host: $hostname"
