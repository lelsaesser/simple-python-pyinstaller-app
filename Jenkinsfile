#!/usr/bin/env groovy
pipeline {
    agent none
    options {
        skipStagesAfterUnstable()
    }
    environment {
        APP_NAME = "SimplePythonApp"
    }
    stages {
        stage('Build') {
            agent {
                docker {
                    image 'python:2-alpine'
                }
            }
            steps {
                sh 'echo "starting build stage..."'
                sh 'python -m py_compile sources/add2vals.py sources/calc.py'
            }
            post {
                success {
                    sh 'echo "Build stage finished successfully for ${APP_NAME}!"'
                }
                failure {
                    sh 'echo "An error occured in the deliver stage. App: ${APP_NAME}"'
                }
            }
        }
        stage('Test') {
            agent {
                docker {
                    image 'qnib/pytest'
                }
            }
            steps {
                sh 'echo "starting test stage..."'
                sh 'py.test --verbose --junit-xml test-reports/results.xml sources/test_calc.py'
            }
            post {
                success {
                    sh 'echo "Test stage finished successfully for ${APP_NAME}!"'
                }
                failure {
                    sh 'echo "An error occured in the deliver stage. App: ${APP_NAME}"'
                }
                always {
                    junit 'test-reports/results.xml'
                }
            }
        }
        stage('Deliver') {
            agent {
                docker {
                    image 'cdrx/pyinstaller-linux:python2'
                }
            }
            steps {
                sh 'echo "starting deliver stage..."'
                sh 'pyinstaller --onefile sources/add2vals.py'
            }
            post {
                success {
                    sh 'echo "Deliver stage finished successfully for ${APP_NAME}!"'
                }
                failure {
                    sh 'echo "An error occured in the deliver stage. App: ${APP_NAME}"'
                }
                always {
                    sh '''
                    set +x
                    ./scripts/message_manager.sh > ./logs_message_manager.txt 2>&1 &
                    echo "PID: $!"
                    '''

                    archiveArtifacts 'dist/add2vals'
                    archiveArtifacts '**/logs_*.txt'
                    deleteDir()
                }
            }
        }
    }
}