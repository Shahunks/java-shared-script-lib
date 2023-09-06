// myPipeline.groovy
import com.example.pipeline.util.checkOut
import com.example.pipeline.util.dockerBuildAndPush

def call(Map params) {
    env.repo = params.repo
    env.targetDir = params.branch
    env.environment = params.environment
    def dirChange = "terraform-aws/$env.environment"
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
                    println "Deploying"
                    }
                }
            }
        }
    }
}
