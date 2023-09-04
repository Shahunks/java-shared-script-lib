package com.example.pipeline.util

def call(Map params) {
   
    def scm = checkout([$class: 'GitSCM', branches: [[name: "${params.branch}"]], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[url: "${params.repo}"]]])
    // Additional logic can be added here if needed
}