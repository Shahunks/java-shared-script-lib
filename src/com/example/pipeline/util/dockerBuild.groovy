package com.example.pipeline.util

def call(Map params) {

                 sh """ 
                    aws ecr get-login-password --region ap-southeast-2 | docker login --username AWS --password-stdin 193566561588.dkr.ecr.ap-southeast-2.amazonaws.com
                    docker build -t test-dx:${params.version} .
                    if [ ${params.version} == 'dev' ] ; then
                    docker tag test-dx:${params.version} 193566561588.dkr.ecr.ap-southeast-2.amazonaws.com/test-dx:${params.version}
                    else if [ ${params.version} == 'uat' ] ; then
                    docker tag test-dx:${params.version} 193566561588.dkr.ecr.ap-southeast-2.amazonaws.com/test-dx:${params.version}
                    else
                    docker tag test-dx:${params.version} 193566561588.dkr.ecr.ap-southeast-2.amazonaws.com/test-dx:${params.version}
                    fi
                    
                    """

}