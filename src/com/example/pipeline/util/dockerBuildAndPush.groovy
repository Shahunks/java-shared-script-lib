package com.example.pipeline.util

// def call(Map params) {

//                  sh """ 
//                     aws ecr get-login-password --region ap-southeast-2 | docker login --username AWS --password-stdin 193566561588.dkr.ecr.ap-southeast-2.amazonaws.com
//                     docker build -t test-dx:${params.version} .
//                     if [ "${params.environment}" = "dev" ]; then
//                     docker tag test-dx:${params.version} 193566561588.dkr.ecr.ap-southeast-2.amazonaws.com/test-dx:${params.version}
//                     docker push 193566561588.dkr.ecr.ap-southeast-2.amazonaws.com/test-dx:${env.version}
//                     else 
//                     docker tag test-dx:${params.version} uataccno.dkr.ecr.ap-southeast-2.amazonaws.com/test-dx:${params.version}
//                     docker push uataccnu.dkr.ecr.ap-southeast-2.amazonaws.com/test-dx:${env.version}
//                     fi 
//                     """

// }
def call(Map params) {

env.version = params.version

               sh "echo ${env.version}"
               //   sh """ 
               //      aws ecr get-login-password --region ap-southeast-2 | docker login --username AWS --password-stdin 193566561588.dkr.ecr.ap-southeast-2.amazonaws.com
                    
               //      #docker buildx create --use 
               //      #docker buildx build --platform linux/amd64 --push -t 193566561588.dkr.ecr.ap-southeast-2.amazonaws.com/test-dx:${params.version} .
               //      """

}