// myPipeline.groovy
import com.example.pipeline.util.checkOut


def call(Map params) {
    env.repo = params.repo
    env.targetDir = params.branch
    env.environment = params.environment
    pipeline {
        agent any
        environment {
        WORKSPACE_DIR = "terraform-aws/${environment}"
    }
        
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
                    withCredentials([[
                         $class: 'AmazonWebServicesCredentialsBinding',
                        accessKeyVariable: 'AWS_ACCESS_KEY_ID',
                        secretKeyVariable: 'AWS_SECRET_ACCESS_KEY',
                        credentialsId: 'AWS'
                    ]]) {
                    println "Provisioning in ${env.environment}"
                    dir(WORKSPACE_DIR) {
                    sh '''
                    cd terraform-aws/"$environment"
                    terraform init 
                    '''
                    }
                    }
                    }
                    }
                }
            stage('Terraform plan') {
                steps {
                    sh 'terraform plan'
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
                        sh 'terraform apply'
                    } else {
                        error('Terraform apply was not approved by the user.')
                    }
                    }
                }
            }
        }
    }
}
