// Creación del pipeline job usando el nombre proporcionado
pipelineJob("${dir_path}") {
    description("Pipeline configurado dinámicamente")
    
    // Configurar el SCM para que apunte a tu repositorio
    definition {
        cpsScm {
            scm {
                git {
                    remote {
                        url("https://github.com/HansJP96/practica_kubernetes1.git") // URL del repositorio Git
                        credentials("GitHub_Autenticacion_SSH") // ID de credenciales para acceso (debe existir en Jenkins)
                    }
                    branch("main") // Rama que deseas escuchar
                }
            }
            scriptPath(jenkinsfile_path) // Ruta al Jenkinsfile dentro del repositorio
        }
    }
    
    // Triggers que activarán el pipeline
    triggers {
        githubPush() // Trigger para GitHub Push (cuando se haga un push en el repositorio)
    }
	
	properties {
        buildDiscarder {
            strategy {
                logRotator {
                    numToKeepStr('10')   
                    daysToKeepStr('7')  
                    artifactNumToKeepStr('7') 
                    artifactDaysToKeepStr('7') 
                }
            }
        }
    }
}
