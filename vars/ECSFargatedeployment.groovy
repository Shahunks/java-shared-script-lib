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
                    sh "aws ecs describe-task-definition --task-definition  my-first-task --region ap-southeast-2  --output json > file.json"
                    //sh 'ls && pwd'
                    new ecsDeployment().call()
                    sh 'aws ecs register-task-definition --cli-input-json file://file.json --region ap-southeast-2 > /dev/null'

                    sh 'aws ecs update-service --service my-first-service --task-definition my-first-task --region ap-southeast-2 > /dev/null'
                    }
                    }
                }
            }
        }
    }
}
