import com.example.pipeline.util.checkOut
import com.example.pipeline.util.dockerBuildAndPush
import com.example.pipeline.util.ecsDeployment

def call(Map params) {
    env.repo = params.repo
    env.targetDir = params.branch
    env.environment = params.environment
    //env.awsAccountId = params.awsAccountId
    env.version = params.version
    pipeline {
        agent any
        
        stages {
        
            stage('checkout') {
                steps {
                    script{
                   new checkOut().call(params)
                }
                }
            }
            // stage('Docker Build and Push') {
            //     steps { 
            //         script{ 
            //         withAWS(credentials: 'AWS'){
            //         println "Building for ${env.environment}"   
            //         new dockerBuildAndPush().call(params)         
            //         }
            //         }
            //         }
            //     }
            stage('Deploy') {
                steps {
                    script {
                    withAWS(credentials: 'AWS'){
                    // Run the AWS CLI command to describe the ECS task definition
                    def awsTaskDefinition = sh(
                        script: """
                            aws ecs describe-task-definition --task-definition my-first-task --region ap-southeast-2
                        """,
                        returnStdout: true
                    ).trim()

                    // Create a new JSON object with the task definition data
                    def taskDefinitionJson = """
                    {
                        "taskDefinition": ${awsTaskDefinition}
                    }
                    """

                    // Write the JSON to a new file
                    writeFile file: 'new-task-definition.json', text: taskDefinitionJson
                    //sh 'aws ecs describe-task-definition --task-definition  my-first-task --region ap-southeast-2 > file.json'
                    new ecsDeployment().call()
                    }
                    }
                }
            }
        }
    }
}
