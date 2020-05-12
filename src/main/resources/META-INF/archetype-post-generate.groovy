import java.nio.file.Path
import java.nio.file.Paths
import java.util.logging.Logger

def logger = Logger.getLogger("Archetype post generate")

Path projectPath = Paths.get(request.outputDirectory, request.artifactId)
def language = request.properties.get('language')
def name = request.properties.get('name')
def installWrapper = Boolean.valueOf(request.properties.get('wrapper'))

if (language == 'groovy') {
    prepareGroovyProject(logger, projectPath, request.properties)
} else if (language == 'kotlin' ) {
    prepareKotlinProject(logger, projectPath, request.properties)
} else if (language == 'java' ) {
    prepareJavaProject(logger, projectPath, request.properties)
} else {
    logger.warning("Language '$language' isn't supported. Only 'java' , 'kotlin' and 'groovy' are supported.")
    prepareJavaProject(logger, projectPath, request.properties)
}


if(installWrapper) {
    installMavenWrapper(logger, projectPath)
}

def installMavenWrapper(Logger logger, Path projectPath) {
	def wrapperCommand = 'mvn -N io.takari:maven:0.7.7:wrapper'
    def cmd = System.properties['os.name'].toLowerCase().contains('windows')
        ? "cmd /c $wrapperCommand"
        : wrapperCommand;
    logger.info("Installing maven wrapper... ($cmd)")
    println cmd.execute(null, projectPath.toFile()).text
}

def prepareKotlinProject(Logger logger, Path projectPath, Map properties) {
    logger.info("Preparing kotlin project...")
    
    deleteJavaSources(projectPath)
    deleteGroovySources(projectPath)
    
    def defaultPom = projectPath.resolve("pom.xml").toFile()
    def kotlinPom = projectPath.resolve("kotlin-pom.xml").toFile()
    kotlinPom.renameTo(defaultPom)
}

def prepareGroovyProject(Logger logger, Path projectPath, Map properties) {
    logger.info("Preparing groovy project...")
    
    deleteJavaSources(projectPath)
    deleteKotlinSources(projectPath)
    
    def defaultPom = projectPath.resolve("pom.xml").toFile()
    def groovyPom = projectPath.resolve("groovy-pom.xml").toFile()
    groovyPom.renameTo(defaultPom)
}
    
def prepareJavaProject(Logger logger, Path projectPath, Map properties) {
    logger.info("Preparing java project...")
    
    deleteGroovySources(projectPath)
    deleteKotlinSources(projectPath)
}

def deleteJavaSources(Path projectPath) {
    def srcJavaDir = projectPath.resolve("src/main/java/").toFile()
    def srcTestJavaDir = projectPath.resolve("src/test/java/").toFile()
    def defaultPom = projectPath.resolve("pom.xml").toFile()
    
    srcJavaDir.deleteDir()
    srcTestJavaDir.deleteDir()
    defaultPom.delete()
}

def deleteGroovySources(Path projectPath) {
    def srcGroovyDir = projectPath.resolve("src/main/groovy/").toFile()
    def srcTestGroovyDir = projectPath.resolve("src/test/groovy/").toFile()
    def groovyPom = projectPath.resolve("groovy-pom.xml").toFile()
    
    srcGroovyDir.deleteDir()
    srcTestGroovyDir.deleteDir()
    groovyPom.delete()
}

def deleteKotlinSources(Path projectPath) {
    def srcKotlinDir = projectPath.resolve("src/main/kotlin/").toFile()
    def srcTestKotlinDir = projectPath.resolve("src/test/kotlin/").toFile()
    def kotlinPom = projectPath.resolve("kotlin-pom.xml").toFile()
    
    srcKotlinDir.deleteDir()
    srcTestKotlinDir.deleteDir()
    kotlinPom.delete()
}