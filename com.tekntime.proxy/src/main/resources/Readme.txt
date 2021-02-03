1. This app cannot be deployed in external tomcat.
2. It should run as java service in vm/server/docker/k8s
3. change application properties

### id : this should be unique id of any route.
### uri: the uri where our microservice is running. Please pay attention here that we don\u2019t need to give full context root. This should be just the hostname
### predicates :This is the condition that we want to check before routing to this uri/route
### example in application.yml