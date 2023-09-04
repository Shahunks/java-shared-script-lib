// myPipeline.groovy
import org.example

def call(Map params) {
   def pipelineparameters.name = params.name
    pipeline {
        agent any
        
        stages {
            stage('Build') {
                steps {
                     sayHello($pipelineparameters.name)
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
