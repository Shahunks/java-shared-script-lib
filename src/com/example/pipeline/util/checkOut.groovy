package com.example.pipeline.util

def call(Map params) {
   
    def scm = checkout scmGit(branches: [[name: "${params.branch}"]], extensions: [], userRemoteConfigs: [[credentialsId: 'just-key', url: "${params.repo}"]])

}