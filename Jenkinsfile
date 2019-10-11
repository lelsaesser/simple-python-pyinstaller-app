#!/usr/bin/env groovy

def WORKER_PATH_MAP = [
    'dox-api': 'api/',
    'dox-extractor': 'extractor/',
    'dox-ml-seq2mask': 'ml/seq2mask/',
    'dox-ml-taxid': 'ml/taxid/',
    'dox-ml-employeename': 'ml/employee_name',
    'dox-ml-chargrid-invoice': 'ml/chargrid_invoice',
    'dox-ml-chargrid-pa': 'ml/chargrid_pa',
    'dox-magic': 'magic/',
    'dox-matcher': 'matcher/',
    'dox-rl-currencycode': 'rule/currency_code/',
    'dox-auditor': "auditor/",
    'dox-data-persistence': "data_persistence/",
    'dox-language': 'language/',
]
def WORKER_UNITTEST_EXITCODES = [:]
def WORKER_LIST = ""
WORKER_PATH_MAP.each { key, value ->
    WORKER_LIST += key
    WORKER_LIST += ","
}
WORKER_LIST = WORKER_LIST[0..-2]

pipeline {
    agent none
    options {
        skipStagesAfterUnstable()
    }
    environment {
        APP_NAME = "SimplePythonApp"
        WORKERS = "${WORKER_LIST}"
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
                always {
                    sh 'echo "starting script...."'
                    sh 'echo "WORKER_LIST: ${WORKERS}"'
                    script {
                        sh 'echo "still here..: ${WORKERS}"'
                        for(worker_name in WORKERS.split(',')) {
                            sh 'echo $worker_name'
                        }
                    }
                    //archiveArtifacts artifacts: '**/deployment*_logs_*.txt'
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
                    chmod +r ./scripts
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