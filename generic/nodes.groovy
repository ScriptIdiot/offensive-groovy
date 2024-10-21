import jenkins.model.Jenkins

def jenkins = Jenkins.instance
def nodes = jenkins.nodes

nodes.each { node ->
    println "Node Name: ${node.displayName}"
}
