// myPipeline.groovy
import com.example.pipeline.util.checkOut
import com.example.pipeline.util.dockerBuild

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
            stage('Docker build') {
                steps { 
                    script{ 
                    withAWS(credentials: 'AWS'){
                    println "Building for ${env.environment}"   
                    new dockerBuild().call(params)         
                    // sh """ 
                    // aws ecr get-login-password --region ap-southeast-2 | docker login --username AWS --password-stdin 193566561588.dkr.ecr.ap-southeast-2.amazonaws.com
                    // docker build -t test-dx:${env.version} .
                    // if [ ${env.version} == 'dev' ] ; then
                    // docker tag test-dx:${env.version} 193566561588.dkr.ecr.ap-southeast-2.amazonaws.com/test-dx:${env.version}
                    // else if [ ${env.version} == 'uat' ] ; then
                    // docker tag test-dx:${env.version} 193566561588.dkr.ecr.ap-southeast-2.amazonaws.com/test-dx:${env.version}
                    // else
                    // docker tag test-dx:${env.version} 193566561588.dkr.ecr.ap-southeast-2.amazonaws.com/test-dx:${env.version}

                    
                    // """
                    }
                    }
                    }
                }
            stage('Docker push') {
                steps {
                    script {
                    sh """docker push 193566561588.dkr.ecr.ap-southeast-2.amazonaws.com/test-dx:${env.version}"""
                    }
                }
            }
        }
    }
}
