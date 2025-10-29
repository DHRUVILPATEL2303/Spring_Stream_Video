pipeline {
    agent {
        docker {
            image 'openjdk:17-jdk-slim'
            args '-v /var/run/docker.sock:/var/run/docker.sock'
        }
    }

    stages {
        stage('Install Docker CLI') {
            steps {
                echo '🔧 Installing Docker CLI...'
                sh '''
                apt-get update -y
                apt-get install -y curl ca-certificates gnupg lsb-release
                mkdir -p /etc/apt/keyrings
                curl -fsSL https://download.docker.com/linux/debian/gpg | gpg --dearmor -o /etc/apt/keyrings/docker.gpg
                echo \
                  "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] \
                  https://download.docker.com/linux/debian \
                  $(lsb_release -cs) stable" > /etc/apt/sources.list.d/docker.list
                apt-get update -y
                apt-get install -y docker-ce-cli docker-compose-plugin
                docker --version
                docker compose version
                '''
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
                echo '🐳 Building Docker Image...'
                sh 'docker compose down || true'
                sh 'docker compose up --build -d'
            }
        }

        stage('Deploy') {
            steps {
                echo '🚀 Deploy successful!'
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
