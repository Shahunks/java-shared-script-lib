package com.example.pipeline.util
import groovy.json.JsonSlurper
import groovy.json.JsonOutput

def call(Map params) {
def workspacePath = pwd() 
def jsonFilePath = "${workspacePath}/task-definition.json"
def newImageValue = "193566561588.dkr.ecr.ap-southeast-2.amazonaws.com/test-dx:${params.version}"
def jsonContents = new File(jsonFilePath).text
def jsonMap = new JsonSlurper().parseText(jsonContents)

jsonMap.containerDefinitions[0].image = newImageValue
def updatedJson = JsonOutput.toJson(jsonMap)

new File(jsonFilePath).text = updatedJson
println(workspacePath)

}