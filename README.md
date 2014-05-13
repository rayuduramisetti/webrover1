webrover1 - EV3
=========

Code for WebRover1 project, part of International Space Apps Challenge 2013, Repurposed and rebuilt by Ryan Vanderwerf for EV3 Platform
for Gr8Conf.eu 2014

Original Project page: http://spaceappschallenge.org/project/webrover1/

## Set up

1. Download Grails 2.2.5 and unzip somewhere suitable or just install GVM (www.gvmtool.net)
   http://www.grails.org/download

2. Download Ryan's leJOS fork with RMIDifferentialPilot & Gradle support
   git clone git://git.code.sf.net/u/ryanv78665/lejos u-ryanv78665-lejos

3. Install leJOS on EV3 brick (this was already done -- or see readme!) (see my fork or sd image)

4. Configure robot details in grails-app/conf/Config.groovy

5. Run Grails App

cd lejos-server
grails run-app

(or -Dgrails.env=claudia run-app)

9. Access from browser

http://localhost:8080/api/forward/1000

10. Assumes motors in A and C, sensor in 1 (optional) 


## JSON 

````
{'direction':[left|right|forward|back|stop],
duration:'integer, milliseconds',
distance:'cm',
velocity:integer}
````

## 'Video'

Added Android camera running IP Webcam (address hardwired into HTML page at the moment)
http://lifehacker.com/5650095/ip-webcam-turns-your-android-phone-into-a-remote-camera


## UI Sketches

### Main drive screen
1. Web cam feed at the top (if we do a webcam feed)
2. D-Pad controller touch area below. User swipes (or click/touch) to tell robot which direction to drive in
3. Underneath drive controls, set command delay in seconds
4. Under that, buttons to set other options and build your own autonomous rules
![Main drive screen](https://pbs.twimg.com/media/BITfLrlCEAMjBGX.jpg)

### Rule builder
Easy rule builder, adapting Tiago Jesus original desktop interface for smaller, mobile devices https://github.com/tiagojesus/LegoRoversUI

![Rule builder](https://pbs.twimg.com/media/BITi_QnCUAIuRyi.jpg:large)
