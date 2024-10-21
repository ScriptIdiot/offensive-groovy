import javax.script.ScriptEngineManager

def manager = new ScriptEngineManager()
def engines = manager.getEngineFactories()

engines.each { engine ->
    println "Engine name: ${engine.getEngineName()}, Language: ${engine.getLanguageName()}"
}
