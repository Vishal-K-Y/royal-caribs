step-1: Move into the proxy-client and proxy-server directories one by one, and run:
          'mvn clean package'

step-2: Once the JAR files are ready, head to the root directory. Then, fire up Docker with:
          'docker-compose up --build'

step-3: 	• In mac/linux run 'curl -x http://localhost:8080 http://httpforever.com/'
	        • On windows run 'curl.exe -x http://localhost:8080 http://httpforever.com/' 
