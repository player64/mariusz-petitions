pipeline {
    agent any

    environment {
        APP_NAME = "mariuszspetitions"
        WAR_FILE = "${APP_NAME}.war"
    }

    stages {
        stage('Pull') {
            steps {
                echo 'Checking out code from GitHub...'
                git branch: 'master', url: 'https://github.com/player64/mariusz-petitions'
            }
        }

        stage('Build') {
            steps {
                echo 'Building the project...'
                sh './mvnw clean install'
            }
        }

        stage('Test') {
            steps {
                echo 'Running tests...'
                sh './mvnw test'
            }
        }

        stage('Package') {
            steps {
                echo 'Packaging as WAR...'
                sh "./mvnw package -DskipTests"
                archiveArtifacts artifacts: "target/${WAR_FILE}", allowEmptyArchive: false
            }
        }

        stage('Deploy') {
            steps {
                script {
                    input message: "Do you want to deploy?", ok: "Deploy Now"
                }
                echo 'Building and deploying Docker container...'
                sh 'docker build -t ${APP_NAME}:latest .'
                sh 'docker rm -f "${APP_NAME}_container" || echo "No existing container to remove."'
                sh 'docker run --name "${APP_NAME}_container" -p 9090:8080 --detach ${APP_NAME}:latest'
            }
        }
    }

    post {
        success {
            echo 'Pipeline completed successfully.'
        }
        failure {
            echo 'Pipeline failed.'
        }
    }
}