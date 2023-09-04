// myPipeline.groovy
import com.example.pipeline.util.checkOut


def call(Map params) {
    env.repo = params.repo
    env.targetDir = params.branch
    env.environment = params.environment
    pipeline {
        agent any
        
        stages {
        
            stage('checkout') {
                steps {
                   new checkOut().call(params)
                }
            }
            stage('Test') {
                steps {
                    echo 'Provisioning in $env.environment'
                }
            }
            stage('Deploy') {
                steps {
                    echo 'Deploying...'
                }
            }
        }
    }
}
