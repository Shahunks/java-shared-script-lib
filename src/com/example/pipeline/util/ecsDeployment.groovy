package com.example.pipeline.util
import groovy.json.JsonSlurper
import groovy.json.JsonOutput

def call() {
  
sh 'aws ecs describe-task-definition --task-definition  my-first-task  > file.json'
def jsonFilePath = 'file.json' 
def newImageValue = 'new-image-name:new-tag' 

def jsonContents = new File(jsonFilePath).text
def jsonMap = new JsonSlurper().parseText(jsonContents)

jsonMap.taskDefinition.containerDefinitions[0].image = newImageValue
def updatedJson = JsonOutput.toJson(jsonMap)

new File(jsonFilePath).text = updatedJson

sh 'aws ecs update-service --service my-first-service --task-definition my-first-task --cli-input-json file:file.json'

}