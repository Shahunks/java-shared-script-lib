import com.example.pipeline.util.checkOut
import com.example.pipeline.util.dockerBuildAndPush
import com.example.pipeline.util.ecsDeployment

def call(Map params) {
    env.repo = params.repo
    env.targetDir = params.branch
    env.environment = params.environmen
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
                    new ecsDeployment().call(params)
                    //sh "aws ecs update-service --service my-first-service --task-definition my-first-task --cli-input-json file:///var/jenkins_home/workspace/ECS/ECS-fargate-deployment/task-definition.json --region ap-southeast-2 > /dev/null"
                    sh 'aws ecs register-task-definition --family my-first-task --cli-input-json file:///var/jenkins_home/workspace/ECS/ECS-fargate-deployment/task-definition.json --region ap-southeast-2' 
                    sh 'aws ecs update-service --service my-first-service  --task-definition --task-definition my-first-task --region $awsRegion  > /dev/null'
                    }
                    }
                }
            }
        }
    }
}
