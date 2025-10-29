pipeline {
    agent {
        docker {
            image 'eclipse-temurin:17-jdk-alpine'
            args '-v /var/run/docker.sock:/var/run/docker.sock'
        }
    }

    stages {
        stage('Build JAR with Gradle') {
            steps {
                echo '📦 Building Spring Boot app with Gradle...'
                sh './gradlew clean build -x test'
            }
        }

        stage('Build Docker Image') {
            steps {
                echo '🐳 Building Docker Image...'
                sh 'docker build -t spring-stream-video .'
            }
        }

        stage('Deploy') {
            steps {
                echo '🚀 Deploying with Docker Compose...'
                sh 'docker-compose up -d'
            }
        }
    }

    post {
        failure {
            echo '❌ Build or deployment failed!'
        }
        success {
            echo '✅ Deployment successful!'
        }
    }
}