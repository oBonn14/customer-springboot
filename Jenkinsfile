pipeline {
    agent any
//     tools {
//         maven 'jenkins-maven'
//     }

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
                    bat 'mvn clean verify sonar:sonar'
                    echo 'SonarQube Analysis Completed'
                }
            }
        }

//         stage("Quality Gate") {
//             steps {
//                 waitForQuality abortPipeline: true
//                 echo 'Quality Gate Completed'
//             }
//         }

        stage('Build Docker Image') {
            steps {
                script {
                    bat 'docker build -t obon/api-customer-springboot .'
                    echo 'Build Docker Image Completed'
                }
            }
        }

        stage('Docker Push') {
            steps {
                script {
                    withCredentials([string(credentialsId: 'dockerhub-pwd', variable: 'dockerhub-password')]) {
                        bat 'docker login -u bonbon153 -p echo %dockerhub-password%'
                    }
                    bat 'docker push obon/api-customer-springboot'
                }
            }
        }

        stage('Docker Run') {
            steps {
                script {
                    bat 'docker run -d --name api-customer-springboot -p 8099:8080 obon/api-customer-springboot'
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
