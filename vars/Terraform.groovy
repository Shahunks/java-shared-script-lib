// myPipeline.groovy
import com.example.pipeline.util.checkOut

def call(Map params) {
    env.repo = params.repo
    env.targetDir = params.branch
    env.environment = params.environment
    def dirChange = "terraform-aws/$env.environment"
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
            stage('Terraform Init') {
                steps {
                    
                    script{
                    withAWS(credentials: 'AWS'){
                    println "Provisioning in ${env.environment}"                
                    sh """cd ${dirChange} && terraform init"""
                    }
                    }
                    }
                }
            stage('Terraform plan') {
                steps {
                    script {
                   withAWS(credentials: 'AWS'){
                    sh """cd ${dirChange} && terraform plan"""
                   }
                    }
                }
            }
            stage('Terraform apply') {
                steps {
                    script{
                     def userInput = input(
                        id: 'terraform-apply',
                        message: 'Do you want to apply the Terraform plan?',
                        parameters: [booleanParam(defaultValue: false, description: 'Yes or No', name: 'APPROVE')]
                    )
                    if (userInput) {
                    if (userInput['APPROVE'] == true) {
                        sh """cd ${dirChange} && terraform apply"""
                    } else {
                        error('Terraform apply was not approved by the user.')
                    }
                    }
                    }
                }
            }
        }
    }
}
