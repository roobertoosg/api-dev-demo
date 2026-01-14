pipeline {
    agent any

    tools {
        maven 'Maven3' 
    }

    environment {
        // ID del archivo en Managed Files con las credenciales
        SETTINGS_XML_ID = 'nexus-settings-xml'
    }

    stages {
        stage('1. Build & Test') {
            steps {
                echo '--- Compilando y ejecutando Tests Unitarios ---'
                // Maven limpia y empaqueta.
                sh 'mvn clean package'
            }
        }

        stage('2. SonarQube Analysis') {
            steps {
                echo '--- Analizando calidad con SonarQube ---'
                withSonarQubeEnv('SonarQube') { 
                    sh 'mvn sonar:sonar'
                }
            }
        }

        stage('3. Deploy to Nexus') {
            steps {
                echo '--- Publicando Release en Nexus ---'
                configFileProvider([configFile(fileId: SETTINGS_XML_ID, variable: 'MAVEN_SETTINGS')]) {
                    // 1. Usamos -s para las credenciales seguras.
                    // 2. Usamos -Drevision para ponerle versión única (1.0.1, 1.0.2...)
                    // 3. deploy sube el archivo a la URL que definimos en el pom.xml
                    sh 'mvn -s $MAVEN_SETTINGS -Drevision=1.0.${BUILD_NUMBER} -DskipTests deploy'
                }
            }
        }
    }
    
    post {
        always {
            cleanWs()
        }
    }
}
