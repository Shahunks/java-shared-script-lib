// myPipeline.groovy
import com.example.pipeline.util.checkOut


def call(Map params) {
    env.repo = params.repo
   env.targetDir = params.targetDir
    pipeline {
        agent any
        
        stages {
        
            stage('Build') {
                steps {
                   script {
                   new checkOut().call(params)
                  
                }
                    echo 'Building...'
                }
            }
            stage('Test') {
                steps {
                    echo 'Testing...'
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
