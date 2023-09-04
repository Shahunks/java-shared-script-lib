package com.example.pipeline.util

def call(Map params) {
    try {
        echo "Cloning repository from ${repo} to ${target}"
        sh "git clone ${params.repo} ${params.target}"
    } catch (Exception e) {
        error "Failed to clone repository from ${repo}. Error: ${e.getMessage()}"
    }
}