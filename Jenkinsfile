pipeline {
    agent any

    stages {
        stage('Git') {
            steps {
                echo "Getting Project from Git";
                git branch : 'iheb',
                url: 'https://github.com/Mousser007/AcademicDevopsProject.git'
            }
        }

        stage('MVN CLEAN') {
            steps {
                sh 'mvn clean'
            }
        }

        stage('MVN COMPILE') {
            steps {
                sh 'mvn compile'
            }
        }

        stage('TEST_MOCKITO') {
            steps {

            // Assurez-vous que vos dépendances Mockito sont correctement définies dans le fichier pom.xml
            // Exécutez les tests unitaires en utilisant Maven avec l'inclusion de la phase de test
            sh 'mvn test'

            }
        }

        stage('MVN SONARQUBE') {
            steps {
                sh 'mvn sonar:sonar -Dsonar.login=admin -Dsonar.password=sonar'
            }
        }


        stage('Publish to Nexus') {
            steps {
                script {
                     sh "mvn deploy --global-settings /usr/share/maven/conf/settings.xml -DaltDeploymentRepository=deploymentRepo::default::http://192.168.1.13:8081/repository/maven-releases/ -DskipTests"
                }
            }
        }
        stage('Build Docker Imagebackend') {
            steps {

                    sh 'docker build -t arfaouiiheb/backendimage .'
            }
       }

       stage('Push Docker Backend Image to Docker Hub') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'DockerhubConfig', usernameVariable: 'DOCKER_HUB_USERNAME', passwordVariable: 'DOCKER_HUB_PASSWORD')]) {
                    script {
                // Log in to Docker Hub using Docker Hub credentials
                    sh "docker login -u $DOCKER_HUB_USERNAME -p $DOCKER_HUB_PASSWORD"


                // Push the Docker image to Docker Hub
                    sh "docker push arfaouiiheb/backendimage"
                    }
                }
            }
        }

        stage('Docker Compose') {
            steps {
                script {

                    sh 'docker compose up -d'

                    }
                }
            }
    }
}