pipeline {
    agent any

    environment {
        IMAGE_NAME = "spring_app"
        COMPOSE_FILE = "docker-compose.yml"
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'master', url: 'https://github.com/DHRUVILPATEL2303/Spring_Stream_Video.git'
            }
        }

        stage('Build JAR with Gradle') {
            steps {
                echo '📦 Building Spring Boot app with Gradle...'
                sh './gradlew clean build -x test'
            }
        }

        stage('Build Docker Image') {
            steps {
                echo '🐳 Building Docker image...'
                sh 'docker build -t $IMAGE_NAME .'
            }
        }

        stage('Deploy with Docker Compose') {
            steps {
                echo '🚀 Starting containers using docker-compose...'
                sh '''
                    docker compose down || true
                    docker compose up -d --build
                '''
            }
        }
    }

    post {
        success {
            echo "✅ Build & deployment succeeded!"
        }
        failure {
            echo "❌ Build or deployment failed!"
        }
    }
}