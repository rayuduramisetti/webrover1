package webrover1

import org.springframework.beans.factory.DisposableBean
import eass.mas.nxt.NXTBrick
import eass.mas.nxt.BasicRobot
import eass.mas.nxt.RoverUltrasonicSensor
import grails.converters.JSON

import java.io.PrintStream

import lejos.nxt.remote.RemoteMotor
import lejos.robotics.navigation.DifferentialPilot

class RobotService implements DisposableBean {

	def robot
	
	def RobotService() {
		robot = new BasicRobot('noor', '00165312A8AB')
		
		if (robot.isConnected()) {
		    /* Robot set up */
			def brick = robot.getBrick();
			def claudia_motorLeft = brick.getMotorC();
			def claudia_motorRight = brick.getMotorA();
			def pilot = new DifferentialPilot(5, 11, claudia_motorLeft, claudia_motorRight);
			pilot.setTravelSpeed(10);
			pilot.setRotateSpeed(15);
			robot.setPilot(pilot);
			
			RoverUltrasonicSensor uSensor = new RoverUltrasonicSensor(brick, 1);
			robot.setSensor(1, uSensor);
		}
	}
	
    def left(duration) {
		robot.pilot.rotateLeft()
		Thread.sleep(duration)
		robot.pilot.stop()
    }
	def right(duration) {
		robot.pilot.rotateRight()
		Thread.sleep(duration)
		robot.pilot.stop()
	}
	def forward(duration) {
		robot.pilot.forward()
		Thread.sleep(duration)
		robot.pilot.stop()
	}
	def backward(duration) {
		robot.pilot.backward()
		Thread.sleep(duration)
		robot.pilot.stop()
	}
	def stop() {
		robot.pilot.stop()
	}

	def sense() {
	    int sensornumber = 1;
	    def distance = ((RoverUltrasonicSensor) robot.getSensor(1)).distance()
		return distance
	}

    void destroy() throws Exception {
		robot.close()
    }
}
