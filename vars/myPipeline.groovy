// myPipeline.groovy
import com.example.mypackage.myscript

def call(Map params) {
   def repo = params.repo
   def targetDir = params.targetDir
    pipeline {
        agent any
        
        stages {
        
            stage('Build') {
                steps {
                   script {
                   // myscript.checkout("$repo","$targetDir")
                   println("$repo")
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
