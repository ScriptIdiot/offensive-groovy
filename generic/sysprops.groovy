def systemProperties = System.getProperties()

systemProperties.each { key, value ->
    println "${key}: ${value}"
}
