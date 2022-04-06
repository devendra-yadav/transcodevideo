# transcodevideo
 
transcodevideo.zip file containing the source code is attached with the email.
Extract the zip file as desired location
Open CMD and go the location where the zip is extracted. 
Make sure the java version is 17 when you check on CMD.
If its not 17 then please set correct JAVA_HOME and PATH.

**To build the package jar execute following cmd.**
 ‘mvn clean package’
This cmd will create a jar inside ‘target’ folder.

There are few JUNIT test cases written you can see the test results as well in the output of above cmd

**To run the application execute following cmd.**

java -jar target\transcodevideo-0.0.1-SNAPSHOT.jar

H2 console available at '/h2-console'. Database available at 'jdbc:h2:mem:ad40b741-2f9c-4c0e-9250-04f502c8b8c2'

This jdbc url will change everytime server is restarted.

Note: Can also use ‘mvn spring-boot:run’ to run the application.

**H2 console is enabled for the application hence one can open following url and enter the jdbc url mentioned in the output and enter the in memory DB.**

http://localhost:8080/h2-console


Only change JDBC URL. Rest all fields to keep as it is.
When you enter you will see there is a table called TRANSCODE_TASK

This table can be referred to see any details/updates of the tasks created in the application.

**Now application is up and running. We can call the end points. Open another CMD to call the endpoints.**

**/transcode-task/init**

Sample cmd (sending form data with POST request)
curl -X POST -F taskUuid=d5737348-b47a-11ec-b909-0242ac120002 -F video=@C:\Devendra\sample_videos\The-Boat.mp4 http://localhost:8080/transcode-task/init
Note: file size limit currently is 100M.
Checks for input file type whether it is video file or not is missing currently.

**/transcode-task/{taskUuid}/status**

Sample cmd (GET request)

curl -X GET http://localhost:8080/transcode-task/d5737348-b47a-11ec-b909-0242ac120002/status

**/transcode-task/{taskUuid}/transcoded-video**

Sample cmd
curl -X GET http://localhost:8080/transcode-task/d5737348-b47a-11ec-b909-0242ac120002/transcoded-video -o transcoded_video
this will download the output file if its successfully transcoded.
