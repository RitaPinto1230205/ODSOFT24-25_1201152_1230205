pipeline {  

        agent any
        
        environment{
        MAVEN_VERSION = "3.8.1"
        GRADLE_VERSION = "7.0"
        NODE_VERSION = "14.17.0"
        PROJECT_DIR = "psoft-project-2024-g1"
        }
    
    // Define versions of Maven, Gradle, and Node.js
    stage('Check Out') {
        echo 'Starting Check Out stage...'
        git url: 'https://github.com/1201152/ODSOFT-2024-2025-1201152-1230205.git', branch: 'main'
    }

    stage('Install Dependencies') {
        parallel(
            "Install Git": {
                if (isUnix()) {
                    sh '''
                    if ! git --version; then
                        if ! command -v brew >/dev/null 2>&1; then
                            echo "Homebrew not found. Install it from https://brew.sh"
                            exit 1
                        fi
                        brew install git
                    fi
                    '''
                } else {
                    bat '''
                    git --version || (
                        choco install git -y
                    )
                    '''
                }
            },
            "Install Maven": {
                if (isUnix()) {
                    sh '''
                    if ! mvn -v; then
                        curl -O https://downloads.apache.org/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.tar.gz
                        tar xzvf apache-maven-${MAVEN_VERSION}-bin.tar.gz
                        sudo mv apache-maven-${MAVEN_VERSION} /opt/maven
                        sudo ln -s /opt/maven/bin/mvn /usr/local/bin/mvn
                    fi
                    '''
                } else {
                    bat '''
                    mvn -v || (
                        choco install maven -y
                    )
                    '''
                }
            },
            "Install Gradle": {
                if (isUnix()) {
                    sh '''
                    if ! gradle -v; then
                        curl -O https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip
                        unzip gradle-${GRADLE_VERSION}-bin.zip
                        sudo mv gradle-${GRADLE_VERSION} /opt/gradle
                        sudo ln -s /opt/gradle/bin/gradle /usr/local/bin/gradle
                    fi
                    '''
                } else {
                    bat '''
                    gradle -v || (
                        choco install gradle -y
                    )
                    '''
                }
            }
        )
    }

    stage('Unattested - Unit Tests') {
            dir(env.PROJECT_DIR) {
                if (fileExists('pom.xml')) {
                    echo 'Running unit tests (files ending in Teste)...'
                    if (isUnix()) {
                        sh 'mvn -Dtest=*Teste test'
                    } else {
                        bat 'mvn -Dtest=*Teste test'
                    }
                } else {
                    error 'pom.xml not found. Aborting.'
                }
            }
        }

 stage('Integration Tests') {
        dir(env.PROJECT_DIR) {
            if (fileExists('pom.xml')) {
                echo 'Running integration tests (files ending in IT or IntegracionTest)...'
                if (isUnix()) {
                    sh 'mvn -Dtest=*IT,*IntegracionTest verify'
                } else {
                    bat 'mvn -Dtest=*IT,*IntegracionTest verify'
                }
            } else {
                error 'pom.xml not found. Aborting.'
            }
        }
    }

    stage('Build and Package') {
        dir(env.PROJECT_DIR) {
            if (fileExists('pom.xml')) {
                echo 'Building and packaging project...'
                if (isUnix()) {
                    sh 'mvn clean package'
                } else {
                    bat 'mvn clean package'
                }
            } else {
                error 'pom.xml not found. Aborting.'
            }
        }
    }

    stage('Deploy') {
        dir(env.PROJECT_DIR) {
            if (fileExists('target/psoft-g1-0.0.1-SNAPSHOT.jar')) {
                echo 'Deploying application...'
                if (isUnix()) {
                    sh 'java -jar target/psoft-g1-0.0.1-SNAPSHOT.jar &'
                } else {
                    bat 'start java -jar target\\psoft-g1-0.0.1-SNAPSHOT.jar'
                }
            } else {
                error 'JAR file not found. Aborting deployment.'
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

     stage('levanta a app no servidor == packaging n é preciso usar dockers ') {
        dir(env.PROJECT_DIR){
        if (fileExists('pom.xml')) {
            if (isUnix()) {
               java -jar psoft-g1-0.0.1-SNAPSHOT.jar 

            } else {
              java -jar psoft-g1-0.0.1-SNAPSHOT.jar 

            }
        } else {
            echo 'pom.xml not found, skipping Maven build step.'
        }
        }
    }
}*/
