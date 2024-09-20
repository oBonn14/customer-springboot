pipeline {
    agent any
    tools {
        maven 'jenkins-maven'
    }

    stages {
        stage('Git Checkout') {
            steps {
                checkout scmGit(branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/oBonn14/customer-springboot']])
                bat 'mvn clean install'
                echo 'Git Checkout Completed'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    bat 'mvn clean package'
                    bat '''mvn clean verify sonar:sonar -Dsonar.projectKey=api-customer-springboot -Dsonar.projectName=api-customer-springboot'''
                    echo 'SonarQube Analysis Completed'
                }
            }
        }

        stage("Quality Gate") {
            steps {
                waitForQuality abortPipeline: true
                echo 'Quality Gate Completed'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    bat 'docker build -t Obon/api-customer-springboot .'
                    echo 'Build Docker Image Completed'
                }
            }
        }

        stage('Docker Push') {
            steps {
                script {
                    withCredentials([string(credentialsId: 'dockerhub-pwd', variable: 'dockerhub-password')]) {
                        bat 'echo %dockerhub-password% | docker login -u septianreza --password-stdin'
                    }
                    bat 'docker push Obon/api-customer-springboot'
                }
            }
        }

        stage('Docker Run') {
            steps {
                script {
                    bat 'docker run -d --name rnd-springboot-3.0 -p 8099:8080 Obon/api-customer-springboot'
                    echo 'Docker Run Completed'
                }
            }
        }
    }
    post {
        always {
            bat 'docker logout'
        }
    }
}
