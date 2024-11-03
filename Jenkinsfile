pipeline {
    agent any

    environment {
        DOCKER_IMAGE_NAME_PREFIX="vladzinko/"
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'task12_kubernetes', url: 'https://github.com/VladZinko11/book_project.git'
            }
        }
        stage('Build config') {
            steps {
                script {
                    def version = getVersionNumber()
                    sh "docker build ${DOCKER_IMAGE_NAME_PREFIX}config:${version} ./config"
                    withCredentials([usernamePassword(credentialsId: 'dockerHub', passwordVariable: 'dockerHubPassword', usernameVariable: 'dockerHubUser')])
                    sh "docker login -u ${env.dockerHubUser} -p ${env.dockerHubPassword}"
                    sh "docker push ${DOCKER_IMAGE_NAME_PREFIX}config:${version}"
                }
            }
        }
        stage('Build gateway') {
            steps {
                script {
                    def version = getVersionNumber()
                    sh "docker build ${DOCKER_IMAGE_NAME_PREFIX}gateway:${version} ./gateway"
                    withCredentials([usernamePassword(credentialsId: 'dockerHub', passwordVariable: 'dockerHubPassword', usernameVariable: 'dockerHubUser')])
                    sh "docker login -u ${env.dockerHubUser} -p ${env.dockerHubPassword}"
                    sh "docker push ${DOCKER_IMAGE_NAME_PREFIX}gateway:${version}"
                }
            }
        }
        stage('Build service-user') {
            steps {
                script {
                    def version = getVersionNumber()
                    sh "docker build ${DOCKER_IMAGE_NAME_PREFIX}service-user:${version} ./service-user"
                    withCredentials([usernamePassword(credentialsId: 'dockerHub', passwordVariable: 'dockerHubPassword', usernameVariable: 'dockerHubUser')])
                    sh "docker login -u ${env.dockerHubUser} -p ${env.dockerHubPassword}"
                    sh "docker push ${DOCKER_IMAGE_NAME_PREFIX}service-user:${version}"
                }
            }
        }
        stage('Build service-book') {
            steps {
                script {
                    def version = getVersionNumber()
                    sh "docker build ${DOCKER_IMAGE_NAME_PREFIX}service-book:${version} ./service-book"
                    withCredentials([usernamePassword(credentialsId: 'dockerHub', passwordVariable: 'dockerHubPassword', usernameVariable: 'dockerHubUser')])
                    sh "docker login -u ${env.dockerHubUser} -p ${env.dockerHubPassword}"
                    sh "docker push ${DOCKER_IMAGE_NAME_PREFIX}service-book:${version}"
                }
            }
        }
        stage('Build service-media') {
            steps {
                script {
                    def version = getVersionNumber()
                    sh "docker build ${DOCKER_IMAGE_NAME_PREFIX}service-media:${version} ./service-media"
                    withCredentials([usernamePassword(credentialsId: 'dockerHub', passwordVariable: 'dockerHubPassword', usernameVariable: 'dockerHubUser')])
                    sh "docker login -u ${env.dockerHubUser} -p ${env.dockerHubPassword}"
                    sh "docker push ${DOCKER_IMAGE_NAME_PREFIX}service-media:${version}"
                }
            }
        }
        stage('Build service-statistic') {
            steps {
                script {
                    def version = getVersionNumber()
                    sh "docker build ${DOCKER_IMAGE_NAME_PREFIX}service-statistic:${version} ./service-statistic"
                    withCredentials([usernamePassword(credentialsId: 'dockerHub', passwordVariable: 'dockerHubPassword', usernameVariable: 'dockerHubUser')])
                    sh "docker login -u ${env.dockerHubUser} -p ${env.dockerHubPassword}"
                    sh "docker push ${DOCKER_IMAGE_NAME_PREFIX}service-statistic:${version}"
                }
            }
        }
        stage('Deploy to Minikube') {
            steps {
                script {
                    sh 'kubectl apply -f kubernetes/postgres.yaml'
                    sh 'kubectl apply -f kubernetes/mongo.yaml'
                    sh 'kubectl apply -f kubernetes/zookeeper.yaml'
                    sh 'kubectl apply -f kubernetes/broker.yaml'
                    sh 'kubectl apply -f kubernetes/consul.yaml'
                    sh 'kubectl apply -f kubernetes/config.yaml'
                    sh 'kubectl apply -f kubernetes/gateway.yaml'
                    sh 'kubectl apply -f kubernetes/service-user.yaml'
                    sh 'kubectl apply -f kubernetes/service-book.yaml'
                    sh 'kubectl apply -f kubernetes/service-media.yaml'
                    sh 'kubectl apply -f kubernetes/service-statistic.yaml'
                }
            }
        }
        stage('Install Prometheus and Grafana') {
            steps {
                script {
                    sh 'helm repo add prometheus-community https://prometheus-community.github.io/helm-charts'
                    sh 'helm repo update'
                    sh 'helm repo add grafana https://grafana.github.io/helm-charts'
                    sh 'helm repo update'
                    sh 'helm install prometheus prometheus-community/prometheus -f values.yaml'
                    sh 'helm install grafana grafana/grafana'
                }
            }
        }
    }
}
def getVersionNumber() {
    def commitCount = sh(script: "git rev-list HEAD --count", returnStdout: true).trim()
    def commitHash = sh(script: "git rev-parse --short HEAD", returnStdout: true).trim()
    def branchName = sh(script: "git rev-parse --abbrev-ref HEAD", returnStdout: true).trim()

    String appVersion = "${commitCount}-${commitHash}-${branchName}"
    return appVersion.toLowerCase()
}