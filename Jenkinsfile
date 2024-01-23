pipeline {
    agent any
    tools {
        maven 'M2_HOME'
    }
    stages {
        stage("Build") {
            steps {
                sh "mvn clean install"
            }
        }
        stage("Unit tests") {
            steps {
                sh "mvn test"
            }
        }

stage("Debugging") {
    steps {
        script {
            // Run a simple command to check permissions
            sh "ls -l /home/vagrant/oracle/wlserver/common/bin/wlst.sh"
        }
    }
}

stage("Deploy to WebLogic") {
    steps {
        script {
            // Use weblogic.Deployer to deploy the JAR
            sh """
                \${WLS_HOME}/common/bin/wlst.sh -i -Dweblogic.management.username=weblogic -Dweblogic.management.password=Wevioo@2023++++ <<-EOF
                connect('weblogic', 'Wevioo@2023++++', 't3://localhost:7001')
                deploy('pi-api', '/var/lib/jenkins/workspace/lol/pi-api/target/pi-api.war', targets='Déploiements', upload='true')
                startApplication('pi-api')
                exit()
                EOF
            """
        }
    }
}

}
