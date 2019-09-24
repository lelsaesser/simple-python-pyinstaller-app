pipeline {
    agent none
    options {
        skipStagesAfterUnstable()
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
                    sh 'echo "Build stage finished successfully!"'
                }
                failure {
                    sh 'echo "An error occured in the deliver stage."'
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
                    sh 'echo "Test stage finished successfully!"'
                }
                failure {
                    sh 'echo "An error occured in the deliver stage."'
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
                    sh 'echo "Deliver stage finished successfully!"'
                }
                failure {
                    sh 'echo "An error occured in the deliver stage."'
                }
                always {
                    mail to: 'ydh09475@bcaoo.com', subject: 'Jenkins pipeline run finished', body: "Pipeline finished with ${currentBuild.fullDisplayName}. URL: ${env.BUILD_URL}"
                    archiveArtifacts 'dist/add2vals'
                    deleteDir()
                }
            }
        }
    }
}