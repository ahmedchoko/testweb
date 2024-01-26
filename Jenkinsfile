pipeline {
    agent any
    tools {
        maven 'M2_HOME'
    }
    environment {
        WLS_HOME = '/home/vagrant/oracle/wlserver'
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

        stage("Deploy to WebLogic") {
            steps {
                script {
                    // Use weblogic.Deployer to deploy the WAR file
                    sh """
                        \${WLS_HOME}/common/bin/java weblogic.Deployer -adminurl t3://localhost:7001 \
                        -username weblogic -password Wevioo@2023++++ -deploy \
                        -targets Deploiements /var/lib/jenkins/workspace/lol/pi-api/target/pi-api.war
                    """
                }
            }
        }
    }
}
