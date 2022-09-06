# weather-data
### Steps to run
#### Step 1: mvn clean install
#### Step 2: docker build -t weatherapp . 
#### Step 3: kubectl apply -f deployment/weather-configmap.yaml
#### Step 4: kubectl apply -f deployment/weather-deployment.yaml
#### Step 5: kubectl port-forward <pod-name> 8080:8080 
#### Step 6: localhost:8080 
