node {
    def image = "192.168.56.109:443/local/spring_test_app:v1.${currentBuild.number}"
    def file_name = "test1-0.0.1-SNAPSHOT.jar"
    def master = "192.168.56.106"
    
    try {
        stage('Checkout Project') {
            checkout scm
        }

        stage('Check Environment') {
            sh "mvn -v"
            sh "java -version"
        }

        stage('Run Tests') {
            sh "mvn test"
        }

        stage('Package Application') {
            sh "mvn clean package"
        }

        stage('JUnit Report') {
            step([$class: 'JUnitResultArchiver', testResults: '**/target/surefire-reports/TEST-*.xml'])
        }

        stage('Archive Artifacts') {
            step([$class: 'ArtifactArchiver', artifacts: '**/target/*.jar', fingerprint: true])
        }

        stage('Quality Gate') {
            timeout(time: 10, unit: 'MINUTES') {
                def qg = waitForQualityGate()
                if (qg.status != 'OK') {
                    error "Pipeline aborted due to quality gate failure: ${qg.status}"
                }
            }
        }

    } catch (e) {
        // Log the error and rethrow it
        currentBuild.result = 'FAILURE'
        throw e
    }
}
