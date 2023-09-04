package com.example.pipeline.util

def call(Map params) {
   
    def scm = checkout scmGit(branches: [[name: "${params.branch}"]], extensions: [], userRemoteConfigs: [[credentialsId: 'my-key', url: "${params.repo}"]])

    //checkout([$class: 'GitSCM', branches: [[name: "${params.branch}"]], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[url: "${params.repo}"]]])
    //checkout scmGit(branches: [[name: "${params.branch}"]], extensions: [], userRemoteConfigs: [[credentialsId: 'my-key', url: ""${params.repo}"]])
    // Additional logic can be added here if needed
}