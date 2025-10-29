pipeline {
 agent {
    docker {
        image 'openjdk:17-jdk-slim'
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
             sh 'docker compose down'
                 sh 'docker compose up --build'   
            }
        }

        stage('Deploy') {
            steps {
                echo '🚀 Deploying with Docker Compose...'
       
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
