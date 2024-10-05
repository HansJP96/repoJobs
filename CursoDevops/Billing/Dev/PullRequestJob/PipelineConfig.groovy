import jenkins.model.*

def jobName = binding.variables['JOB_NAME']
def jenkinsfilePath = binding.variables['JENKINSFILE_PATH'] ?: "Jenkinsfile"


pipelineJob(jobName) {
    description("Pipeline configurado dinámicamente usando Groovy para: ${jobName}")
    
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
            scriptPath(jenkinsfilePath) // Ruta al Jenkinsfile dentro del repositorio
        }
    }
    
  
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
