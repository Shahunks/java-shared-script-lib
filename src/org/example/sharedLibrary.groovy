package org.example

def sayHello(name) {
    def pipelineparameters.name = params.name
    return "Hello, ${name}!"
}