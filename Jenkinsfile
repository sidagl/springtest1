node {
 def image = "192.168.56.109:443/local/spring_test_app:v1.${currentBuild.number}"
 def file_name="test1-0.0.1-SNAPSHOT.jar"
 def master="192.168.56.106"
  try{
    stage 'checkout project'
    checkout scm

    stage 'check env'
    sh "mvn -v"
    sh "java -version"

    stage 'test'
    sh "mvn test"

    stage 'package'
    sh "mvn clean package"

    stage 'report'
    step([$class: 'JUnitResultArchiver', testResults: '**/target/surefire-reports/TEST-*.xml'])

    stage 'Artifact'
    step([$class: 'ArtifactArchiver', artifacts: '**/target/*.jar', fingerprint: true])

    stage("SonarQube analysis") {
              withSonarQubeEnv('sonarLocal') {
                 sh 'mvn clean package sonar:sonar'
              }
    }

    stage("Quality Gate"){
          timeout(time: 10, unit: 'MINUTES') {
              def qg = waitForQualityGate()
              if (qg.status != 'OK') {
                  error "Pipeline aborted due to quality gate failure: ${qg.status}"
              }
          }
    }
    
    }

  }catch(e){
    throw e;
  }
}
