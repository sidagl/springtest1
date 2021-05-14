node {
 def image = "192.168.56.109:443/local/spring_test_app:v1.${currentBuild.number}"
  try{
    stage 'checkout project'
    checkout scm

    stage 'check env'
    sh "mvn -v"
    sh "java -version"

    stage 'test'
    sh "mvn test"

    stage 'package'
    sh "mvn package"

    stage 'report'
    step([$class: 'JUnitResultArchiver', testResults: '**/target/surefire-reports/TEST-*.xml'])

    stage 'Artifact'
    step([$class: 'ArtifactArchiver', artifacts: '**/target/*.jar', fingerprint: true])

    stage("build & SonarQube analysis") {
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
    stage("Modify Configuration"){
        sh " sed -i 's/REPLACE/${image}/g' **/docker/spring_test_app.yaml"
    }

     stage("Docker Build & Push")
     {
       withCredentials([usernamePassword(credentialsId:'master-creds', passwordVariable: 'Password', usernameVariable: 'Username')]) {
            sh "scp build/libs/\\*.jar ${Username}:${Password}@192.168.56.106 /opt/local-apps"
            sh "scp **/docker/spring_test_app.yml ${Username}:${Password}@192.168.56.106 /opt/local-apps"
            sh "ssh ${Username}:${Password}@192.168.56.106"
            sh "docker build --build-arg JAR_FILE=build/libs/\\*.jar -t ${image} ."
            sh "docker push ${image}"
        }
     }

     stage('Deploy'){
       withCredentials([usernamePassword(credentialsId:'master-creds', passwordVariable: 'Password', usernameVariable: 'Username')]) {
          sh "ssh ${Username}:${Password}@192.168.56.106"
          sh "kubectl apply -f /opt/local-apps/spring_test_app.yaml"
        }
     }

  }catch(e){
    throw e;
  }
}