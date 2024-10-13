pipelineJob("DIRECTORY_PATH/PublishDockerHub") {
    description("Pipeline configurado dinámicamente")
    
    properties {
		pipelineTriggers {
			triggers {
				githubPush() // Trigger para GitHub Push (cuando se haga un push en el repositorio)
			}
    	}
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
    
    // Configurar el SCM para que apunte a tu repositorio
    definition {
        cpsScm {
            scm {
                git {
                    remote {
                        url("https://github.com/HansJP96/Sofka-DevOps-Test.git")
                        credentials("GitHub_Autenticacion_SSH")
                    }
                    branch("main") 
                }
            }
        }
		cps {
            script(readFileFromWorkspace(SEED_JOB.lastBuild.checkouts[0].workspace + "/" + "JENKINSFILE_PATH"))
            sandbox()
        }
    }
}
