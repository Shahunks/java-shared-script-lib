package com.example.pipeline.util

def call(Map params) {
    try {
        echo "Cloning repository from ${repo} to ${branch}"
        sh "git clone ${params.repo} ${params.branch}"
    } catch (Exception e) {
        error "Failed to clone repository from ${repo}. Error: ${e.getMessage()}"
    }
}