pipeline {
    agent any
    tools {
        maven 'jenkins-maven'
    }

    stages {
        stage('Git Checkout') {
            steps {
                checkout scmGit(branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/oBonn14/customer-springboot']])
                sh 'mvn clean install'
                echo 'Git Checkout Completed'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh 'mvn clean package'
                    sh 'mvn clean verify sonar:sonar'
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
                    sh 'docker build -t bonbon153/api-customer-springboot .'
                    echo 'Build Docker Image Completed'
                }
            }
        }

        stage('Docker Push') {
            steps {
                script {
                    withCredentials([string(credentialsId: 'dockerhub-pwd', variable: 'dockerhub-password')]) {
                        sh 'echo %dockerhub-password% | docker login -u bonbon153 --password-stdin'
                    }
                    sh 'docker push bonbon153/api-customer-springboot'
                }
            }
        }

        stage('Docker Run') {
            steps {
                script {
                    sh 'docker run -d --name rnd-springboot-3.0 -p 8099:8080 bonbon153/api-customer-springboot'
                    echo 'Docker Run Completed'
                }
            }
        }
    }
    post {
        always {
            sh 'docker logout'
        }
    }
}
