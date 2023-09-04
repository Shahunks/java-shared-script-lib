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
                    script{
                   new checkOut().call(params)
                }
                }
            }
            stage('Terraform Init') {
                steps {
                    script{
                    println "Provisioning in ${env.environment}"
                    sh 'cd terraform-aws && ls'
                    sh 'terraform init'
                    }
                }
            }
            stage('Terraform plan') {
                steps {
                    sh 'terraform plan -out=tfplan'
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
                    if (userInput['APPROVE']) {
                        sh 'terraform apply tfplan'
                    } else {
                        error('Terraform apply was not approved by the user.')
                    }
                    }
                }
            }
        }
    }
}
