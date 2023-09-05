// myPipeline.groovy
import com.example.pipeline.util.checkOut


def call(Map params) {
    env.repo = params.repo
    env.targetDir = params.branch
    env.environment = params.environment
    env.dirChange = "terraform-aws/$env.environment"
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
                    withCredentials([[
                         $class: 'AmazonWebServicesCredentialsBinding',
                        accessKeyVariable: 'AWS_ACCESS_KEY_ID',
                        secretKeyVariable: 'AWS_SECRET_ACCESS_KEY',
                        credentialsId: 'AWS'
                    ]]) {
                    println "Provisioning in ${env.environment}"
                    
                    sh '''
                    cd "${env.dirChange}" && terraform init 
                    '''
                    }
                    }
                    }
                }
            stage('Terraform plan') {
                steps {
                    script {
                    sh 'cd $dirchange && terraform plan'
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
                    if (userInput['APPROVE']) {
                        sh 'cd $dirchange && terraform apply'
                    } else {
                        error('Terraform apply was not approved by the user.')
                    }
                    }
                }
            }
        }
    }
}
