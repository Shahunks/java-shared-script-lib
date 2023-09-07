import com.example.pipeline.util.checkOut
import com.example.pipeline.util.dockerBuildAndPush
import com.example.pipeline.util.ecsDeployment

def call(Map params) {
    env.repo = params.repo
    env.targetDir = params.branch
    env.environment = params.environment
    env.family = params.family
    env.cluster = params.cluster
    env.service = params.service
    env.taskdefinition = params.taskdefinition
    env.version = params.version
    pipeline {
        agent any
        stages {
            stage('checkout') {
                steps {
                    script{
                        println(params.version)
                   new checkOut().call(params)
                }
                }
            }
            stage('Docker Build and Push') {
                steps { 
                    script{ 
                    withAWS(credentials: 'AWS'){
                    println "Building for ${env.environment}"   
                    new dockerBuildAndPush().call(params)         
                    }
                    }
                    }
                }
            stage('Deploy') {
                steps {
                    script {
                    withAWS(credentials: 'AWS'){
                    new ecsDeployment().call(params)
                    sh """aws ecs register-task-definition --family ${env.family} --cli-input-json file:///var/jenkins_home/workspace/ECS/ECS-fargate-deployment/task-definition.json --region ap-southeast-2
                    aws ecs update-service --cluster  ${env.cluster} --service ${env.service} --task-definition  ${env.taskdefinition} --region  ap-southeast-2 > /dev/null
                    """
                    }
                    }
                }
            }
        }
    }
}
