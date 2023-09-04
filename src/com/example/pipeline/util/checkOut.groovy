package com.example.pipeline.util

def call(Map params) {
    try {
        echo "Cloning repository from ${repoUrl} to ${targetDir}"
        sh "git clone ${params.repoUrl} ${params.targetDir}"
    } catch (Exception e) {
        error "Failed to clone repository from ${repoUrl}. Error: ${e.getMessage()}"
    }
}