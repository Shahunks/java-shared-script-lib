package com.example.pipeline.util
import groovy.json.JsonSlurper
import groovy.json.JsonOutput

def call() {
def workspacePath = pwd() 
def jsonFilePath = "${workspacePath}/task-definition.json"
def newImageValue = 'new-image-name:new-tag' 
def jsonContents = new File(jsonFilePath).text
def jsonMap = new JsonSlurper().parseText(jsonContents)

jsonMap.taskDefinition.containerDefinitions[0].image = newImageValue
def updatedJson = JsonOutput.toJson(jsonMap)

new File(jsonFilePath).text = updatedJson
println(updatedJson)
}