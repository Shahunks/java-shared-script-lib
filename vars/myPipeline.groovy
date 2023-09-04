// myPipeline.groovy
import com.example.mypackage.myscript

def call(Map params) {
   
    pipeline {
        agent any
        
        stages {
        def name = params.name
            stage('Build') {
                steps {
                     sayHello($name)
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
