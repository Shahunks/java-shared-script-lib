package com.example.mypackage

def myscript(String repoUrl, String targetDir) {
    try {
        echo "Cloning repository from ${repoUrl} to ${targetDir}"
        sh "git clone ${repoUrl} ${targetDir}"
    } catch (Exception e) {
        error "Failed to clone repository from ${repoUrl}. Error: ${e.getMessage()}"
    }
}