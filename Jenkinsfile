pipeline {
    agent any

    options {

         timeout(time: 1,unit: 'HOURS')
         buildDiscarder(logRotator(numToKeepStr: '10'))
         timestamps()
          }
    tools {
        maven 'Maven3' 
    }

    environment {
        // ID del archivo en Managed Files con las credenciales
        SETTINGS_XML_ID = 'nexus-settings'
    }

    stages {
        stage('1. Build & Test') {
            steps {
                echo '--- Compilando y ejecutando Tests ---'
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

        stage('3. Quality Gate') {
            steps {
              timeout(time: 5, unit: 'MINUTES'){
		echo '--- Esperando veredicto de calidad ---'
                waitForQualityGate abortPipeline: true
                          }
		}
	}

	stage('4. Deploy to Nexus') {

	   options {
		     retry(3)
		}
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
	    echo 'Workspace Limpio.'
	}
	success {
	    echo 'Artefacto desplegado, ha cumplido con los estandares de calidad'
	}
	failure {
	    echo 'ERROR: el pipeline fallo'
	    echo 'Fallo en la etapa: ${env.STAGE_NAME}'
	}
	unstable{
	    echo 'INESTABLE: Paso, pero hay test fallando o advertencias'
	}
      }
   }
