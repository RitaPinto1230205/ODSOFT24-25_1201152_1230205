
pipeline {
    agent any

    triggers {
    githubPush()
    }

    stages {
         stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Static Code Analysis') {
            steps {
                sh 'mvn sonar:sonar'
            }
        }


    }
        
}



/*node {

    // Define versions of Maven, Gradle, and Node.js
    env.MAVEN_VERSION = '3.8.1'
    env.GRADLE_VERSION = '7.0'
    env.NODE_VERSION = '14.17.0'
    env.PROJECT_DIR = 'psoft-project-2024-g1'

    stage('Install Git') {
        if (isUnix()) {
            // For macOS
            sh '''
            if ! git --version; then
                brew install git
            fi
            '''
        } else {
            // For Windows
            bat '''
            git --version || (
                choco install git -y
            )
            '''
        }
    }

    stage('Clone Repository') {
        // Clone the repository from Git
        git url: 'https://github.com/1201152/ODSOFT-2024-2025-1201152-1230205.git', branch: 'main'
    }

    stage('Install Maven') {
        if (isUnix()) {
            // For macOS
            sh '''
            if ! mvn -v; then
                wget https://downloads.apache.org/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.tar.gz
                tar xzvf apache-maven-${MAVEN_VERSION}-bin.tar.gz
                sudo mv apache-maven-${MAVEN_VERSION} /opt/maven
                sudo ln -s /opt/maven/bin/mvn /usr/bin/mvn
            fi
            '''
        } else {
            // For Windows
            bat '''
            mvn -v || (
                choco install maven -y
            )
            '''
        }
    }

    stage('Install Gradle') {
        if (isUnix()) {
            // For macOS
            sh '''
            if ! gradle -v; then
                wget https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip
                unzip gradle-${GRADLE_VERSION}-bin.zip
                sudo mv gradle-${GRADLE_VERSION} /opt/gradle
                sudo ln -s /opt/gradle/bin/gradle /usr/bin/gradle
            fi
            '''
        } else {
            // For Windows
            bat '''
            gradle -v || (
                choco install gradle -y
            )
            '''
        }
    }

    stage('Check Current Directory') {

                if (isUnix()) {
                    sh 'pwd' // Print current directory for Unix-based systems
                } else {
                    bat 'cd' // Print current directory for Windows
                }
    }

    stage('Check for psoft-project-2024-g1 Directory') {

                if (fileExists(env.PROJECT_DIR)) {
                    echo "'${env.PROJECT_DIR}' directory found."
                } else {
                    error "'${env.PROJECT_DIR}' directory not found. Aborting build."
                }

    }

    stage('Change to Project Directory') {

                // Change to the project directory
                if (isUnix()) {
                    dir(env.PROJECT_DIR) {
                        sh 'pwd' // Confirm the change in directory
                    }
                } else {
                    bat "cd ${env.PROJECT_DIR} && cd" // Confirm the change in directory for Windows
                }

    }

  stage('List Files') {
        dir(env.PROJECT_DIR){
                if (isUnix()) {
                    dir(env.PROJECT_DIR)
                    sh 'ls -la' // List files for Unix-based systems
                } else {
                    bat 'dir' // List files for Windows
                }
        }

    }

    stage('Check for pom.xml') {
                 dir(env.PROJECT_DIR){

                if (fileExists('pom.xml')) {
                    echo 'pom.xml found in the current directory.'
                } else {
                    error 'pom.xml not found in the current directory. Aborting build.'
                }
                 }
    }

    stage('Clean Project') {
        dir(env.PROJECT_DIR){
        if (fileExists('pom.xml')) {
            if (isUnix()) {
                sh 'mvn clean'
            } else {
                bat 'mvn clean'
            }
        } else {
            echo 'pom.xml not found, skipping Maven clean step.'
        }
        }
    }

    stage('Package Project') {
        dir(env.PROJECT_DIR){
        if (fileExists('pom.xml')) {
            if (isUnix()) {
                sh 'mvn package'
            } else {
                bat 'mvn package'
            }
        } else {
            echo 'pom.xml not found, skipping Maven package step.'
        }
        }
    }

    stage('Build with Maven') {
        dir(env.PROJECT_DIR){
        if (fileExists('pom.xml')) {
            if (isUnix()) {
                sh 'mvn clean install'
            } else {
                bat 'mvn clean install'
            }
        } else {
            echo 'pom.xml not found, skipping Maven build step.'
        }
        }
    }

}*/
