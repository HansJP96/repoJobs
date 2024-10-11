pipelineJob("DIRECTORY_PATH/PullRequest") {
    description("Pipeline configurado dinámicamente")
    
    properties {
		pipelineTriggers {
			triggers {
				genericTrigger {
				   genericVariables {
						genericVariable {
							key("PR_TITLE")
							value("\$.pullrequest.title")
							expressionType("JSONPath") 
							defaultValue("none")
						}
						genericVariable {
							key("PR_CURRENT_STATUS")
							value("\$.action")
							expressionType("JSONPath") 
							defaultValue("none")
						}
					}
					token('secrettoken')
					tokenCredentialId('')
					printContributedVariables(false)
					printPostContent(false)
					silentResponse(false)
					regexpFilterText("\$PR_CURRENT_STATUS")
					regexpFilterExpression("^opened|reopened|synchronize$")
				}
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
		cps {
            script(readFileFromWorkspace(SEED_JOB.lastBuild.checkouts[0].workspace + "/" + "JENKINSFILE_PATH"))
            sandbox()
        }
    }
}
