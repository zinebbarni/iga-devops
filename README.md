## 1- Importer le projet
Fork le projet  
Cloner le projet sur la machine locale  
Exécuter ./mvnw spring-boot:run  
tester l'url : http://localhost:8080/swagger-ui.html

## 2- Création de pipline
Ajouter une action sur github:

    name: App pipeline  

    on:
    push:
        branches:
        - main
    pull_request:
        branches:
        - main

    jobs:
    build-and-test:
        runs-on: ubuntu-latest

        steps:
        - uses: actions/checkout@v3

        - name: Set up JDK 17
            uses: actions/setup-java@v2
            with:
            java-version: '17'
            distribution: 'adopt'

        - name: Build
            run: mvn clean install --no-transfer-progress -Dmaven.test.skip=true

        - name: Tests
            run: mvn test --no-transfer-progress

Corriger l'erreur sur le code

## 3 - Ajouter une étape de qualité du code
Créer un compte sur sonarsource hub : https://www.sonarsource.com/products/sonarcloud/signup/

Créer un projet 
renommer la branche master en main sur sonarsource 
Ajouter la configuration proposée aux actions github

Corriger les erreurs "Remove this unused import" remonter par sonarsource
Verifier le covrege de votre projet

Ajouter la section suivante au fichier pom: 

	<profiles>
	   <profile>
  	   <id>coverage</id>
  	   <build>
	   <plugins>
	    <plugin>
	      <groupId>org.jacoco</groupId>
	     <artifactId>jacoco-maven-plugin</artifactId>
	      <version>0.8.7</version>
	      <executions>
		<execution>
		  <id>prepare-agent</id>
		  <goals>
		    <goal>prepare-agent</goal>
		  </goals>
		</execution>
		<execution>
		  <id>report</id>
		  <goals>
		    <goal>report</goal>
		  </goals>
		  <configuration>
		    <formats>
		      <format>XML</format>
		    </formats>
		  </configuration>
		</execution>
	      </executions>
	    </plugin>
	   </plugins>
	  </build>
	</profile>
    </profiles>

Ajouter "-Pcoverage" a configuratuon sonar de votre pipline

## 4 - Ajouter une étape création d'image docker

Ajouter un fichier "Dockerfile"

    FROM eclipse-temurin:17-jdk-alpine
    VOLUME /tmp
    ARG JAR_FILE
    COPY ${JAR_FILE} app.jar
    ENTRYPOINT ["java","-jar","/app.jar"]

Créer un compte sur docker hub : https://hub.docker.com/  

Ajouter les secrets suivants sur github:  
    -DOCKER_HUB_USERNAME  
    -DOCKER_HUB_PASSWORD    

Ajouter la section suivante sur github actions:

    - name: Publish to Docker Hub
      uses: docker/build-push-action@v1     
      with:       
       username: ${{ secrets.DOCKER_HUB_USERNAME }} 
       password:  ${{ secrets.DOCKER_HUB_PASSWORD }}
       repository: yourusername/yourprojectname       
       tags: ${{github.run_number}}


