// Creación del pipeline job usando el nombre proporcionado
pipelineJob("DIRECTORY_PATH/Publish") {
    description("Pipeline configurado dinámicamente")
    
    // Configurar el SCM para que apunte a tu repositorio
    scm {
		git {
			remote {
				github('https://github.com/HansJP96/practica_kubernetes1.git')
				credentials('ssh-credential-id')
			}
			branch('main')
		}
	}
	
	definition {
        cps {
            script(readFileFromWorkspace("JENKINSFILE_PATH"))
            sandbox()
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
