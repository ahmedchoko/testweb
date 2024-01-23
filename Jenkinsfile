pipeline {

agent any
 tools {
        maven 'M2_HOME'
    }
stages {
stage("Build") {
steps {
sh " mvn clean install "
}
}
stage("Unit tests") {
steps {
sh " mvn test"

}}
 stage("Deploy to WebLogic") {
            steps {
                script {
                    // Set your WebLogic Server details
                    def weblogicHost = 'localhost'
                    def weblogicPort = '7001'
                    def weblogicUser = 'weblogic'
                    def weblogicPassword = 'Wevioo@2023++++'
                    
                    // Set the path to your deployed JAR
                    def jarPath = "/var/lib/jenkins/workspace/lol/pi-api/target/pi-api.war"
                    
                    // Use weblogic.Deployer to deploy the JAR
                    sh """
                        \${WLS_HOME}/common/bin/wlst.sh -i -Dweblogic.management.username=\${weblogicUser} -Dweblogic.management.password=\${weblogicPassword} <<EOF
                        connect('weblogic', 'weblogic123', 't3://\${weblogicHost}:\${weblogicPort}')
                        deploy('pi-api', '\${jarPath}', targets='Déploiements', upload='true')
                        startApplication('pi-api')
                        exit()
                        EOF
                    """
                }
            }
        }

}
}