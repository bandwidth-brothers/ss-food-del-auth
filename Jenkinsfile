pipeline{
	agent any
	environment{
		AWS_ID = credentials('aws-id')
		AWS_REGION = credentials('aws-region')
	}
	stages{
		stage('Checkout'){
			steps{
				checkout scm
				sh "chmod +x ./mvnw"
			}
		}
		/**
		stage('Analysis'){
			environment{
				SONARQUBE_TOKEN = credentials('sonarqube')
			}
			steps{
				sh "./mvnw clean verify sonar:sonar \\\n" +
					"  -Dsonar.host.url=http://sonarqube:9000 \\\n" +
					"  -Dsonar.login=${SONARQUBE_TOKEN}"
			}
		}*/
		stage('Build'){
			steps{
				sh './mvnw clean package -DskipTests'
			}
		}
		stage('Publish'){
			steps{
				withAWS(region: 'us-east-2', credentials: 'aws-creds'){
					s3Upload(bucket: 'ss-scrumptious-artifacts', file: 'target/ss-scrumptious-auth-0.0.1-SNAPSHOT.jar', path: 'restaurant-auth.jar')
				}
			}
		}
		stage('Deploy'){
			steps{
				sh "commit=$(git rev-parse --short HEAD)"
				sh "docker build -t ss-${SERVICE_NAME}:$commit ."
				script{
					docker.withRegistry("https://${AWS_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/","ecr:${AWS_REGION}:aws-creds"){
						docker.image("ss-scrumptious-repo:restaurant-auth").push()
					}
				}
				sh "docker system prune -fa"
			}
		}
	}
}
