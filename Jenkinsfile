pipeline {
    agent any
    environment {
        MAVEN_VERSION = "3.8.1"
        GRADLE_VERSION = "7.0"
        NODE_VERSION = "14.17.0"
        PROJECT_DIR = "ODSOFT24-25"
        JACOCO_REPORT_PATH = 'target/site/jacoco/index.html'
    }

    stages {
        stage('Check Out') {
            steps {
                script {
                    echo 'Starting Check Out stage...'
                    git url: 'https://github.com/RitaPinto1230205/ODSOFT24-25_1201152_1230205.git', branch: 'main'
                }
            }
        }

        stage('Install Dependencies') {
            steps {
                script {
                    parallel(
                        "Install Git": {
                            echo 'Installing Git...'
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
                            echo 'Installing Maven...'
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
                        }
                    )
                }
            }
        }

        stage('Run Unit Tests and Coverage') {
            steps {
                script {
                    if (fileExists('pom.xml')) {
                        echo 'Running unit tests and generating coverage report...'
                        if (isUnix()) {

                            sh 'mvn clean test -Dtest=*Teste jacoco:report'
                        } else {
                            bat 'mvn clean test -Dtest=*Teste jacoco:report'
                        }
                    } else {
                        error 'pom.xml not found. Aborting.'
                    }
                }
            }
        }

        stage('Present Unit Test Coverage') {
            steps {
                script {
                    echo 'Presenting Unit Test Coverage...'
                    if (fileExists(JACOCO_REPORT_PATH)) {
                        echo 'Coverage report for unit tests generated:'
                        echo "See JaCoCo coverage report: ${JACOCO_REPORT_PATH}"
                    } else {
                        error 'JaCoCo coverage report not found. Aborting.'
                    }
                }
            }
        }

        stage('Run Integration Tests') {
            steps {
                script {
                    if (fileExists('pom.xml')) {
                        echo 'Running integration tests...'
                        if (isUnix()) {
                            sh 'mvn clean verify -Dtest=*IT,*IntegracionTest'
                        } else {
                            bat 'mvn clean verify -Dtest=*IT,*IntegracionTest'
                        }
                    } else {
                        error 'pom.xml not found. Aborting.'
                    }
                }
            }
        }

        stage('Run Mutation Tests') {
            steps {
                script {
                    echo 'Running mutation tests...'
                    if (isUnix()) {
                        sh 'mvn org.pitest:pitest-maven:mutationCoverage'
                    } else {
                        bat 'mvn org.pitest:pitest-maven:mutationCoverage'
                    }
                }
            }
        }

        stage('Build and Package') {
            steps {
                script {
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
        }

        stage('Deploy') {
            steps {
                script {
                    def jarFile = 'target/psoft-g1-0.0.1-SNAPSHOT.jar'
                    if (fileExists(jarFile)) {
                        echo 'Deploying application...'
                        if (isUnix()) {
                            sh "nohup java -jar ${jarFile} &"
                        } else {
                            bat "start java -jar ${jarFile}"
                        }
                    } else {
                        error 'JAR file not found. Aborting deployment.'
                    }
                }
            }
        }
    }

    post {
        always {
            echo 'Pipeline execution completed.'
        }
        success {
            echo 'Build completed successfully!'
        }
        failure {
            echo 'Build failed. Please check the logs.'
        }
    }
}
