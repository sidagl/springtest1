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
    withCredentials([usernamePassword(credentialsId:'master-creds', passwordVariable: 'Password', usernameVariable: 'Username')]) {
        stage("Modify Configuration"){
            sh " sed -i 's|REPLACE|${image}|g' docker/spring_test_app.yaml"
            sh """ssh ${Username}@${master} \
                 " kubectl cp ${hostname}:'/var/jenkins_home/workspace/spring test1/docker/spring_test_app.yaml' /opt/local-apps/spring_test_app.yaml"
              """
        }

        stage("Docker Build & Push")
         {
            sh """ssh ${Username}@${master} \
             " kubectl cp ${hostname}:'/var/jenkins_home/workspace/spring test1/target/${file_name}' /opt/local-apps/docker/${file_name} \
             && kubectl cp ${hostname}:'/var/jenkins_home/workspace/spring test1/docker/Dockerfile' /opt/local-apps/docker/Dockerfile \
             && cd /opt/local-apps/docker/ \
             && docker build --build-arg JAR_FILE=${file_name} -t ${image} . \
             && docker push ${image}"
             """
         }

        stage("Deploy"){
            sh """ssh ${Username}@${master} \
             " kubectl apply -f /opt/local-apps/spring_test_app.yaml "
             """
        }
    }

  }catch(e){
    throw e;
  }
}