Jenkins.instance.computers.each { computer ->
    println "${computer.name}: ${computer.numExecutors} executors"
}
