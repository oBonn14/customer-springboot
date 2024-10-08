pipeline {
    agent any
//     tools {
//         maven 'jenkins-maven'
//     }
    environment {
            BUILD_NUMBER_ENV = "${env.BUILD_NUMBER}"
            TEXT_SUCCESS_BUILD = "[#${env.BUILD_NUMBER}] Project Name : ${JOB_NAME} is Success"
            TEXT_FAILURE_BUILD = "[#${env.BUILD_NUMBER}] Project Name : ${JOB_NAME} is Failure"
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
                    bat 'mvn clean verify sonar:sonar'
                    echo 'SonarQube Analysis Completed'
                }
            }
        }

//         stage("Quality Gate") {
//             steps {
//                 waitForQualityGate abortPipeline: true
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

//         stage('Docker Push') {
//             steps {
//                 script {
//                     withCredentials([string(credentialsId: 'docker-pwd', variable: 'pwd-docker')]) {
//                         bat 'echo %dockerhub-password% | docker login -u bonbon153 --password-stdin'
//                     }
//                     bat 'docker push obon/api-customer-springboot'
//                 }
//             }
//         }


        stage('Docker Run') {
            steps {
                script {
                    bat 'docker run -d --name api-customer-springboot -p 8080:8080 obon/api-customer-springboot'
                    echo 'Docker Run Completed'
                }
            }
        }
    }
    post {
        always {
            bat 'docker logout'
        }
        success {
                script{
                     withCredentials([string(credentialsId: 'telegram-token', variable: 'token-telegram'), string(credentialsId: 'telegram-chat-id', variable: 'chat-id-tellegram')]) {
                        bat ' curl -s -X POST https://api.telegram.org/bot"%token-telegram%"/sendMessage -d chat_id="%chat-id-tellegram%" -d text="%TEXT_SUCCESS_BUILD%" '
                     }
                }
            }
            failure {
                script{
                    withCredentials([string(credentialsId: 'telegram-token', variable: 'token-telegram'), string(credentialsId: 'telegram-chat-id', variable: 'chat-id-tellegram')]) {
                        bat ' curl -s -X POST https://api.telegram.org/bot"%token-telegram%"/sendMessage -d chat_id="%chat-id-tellegram%" -d text="%TEXT_FAILURE_BUILD%" '
                    }
                }
            }
    }
}
