package webrover1

import lejos.hardware.port.PortException
import lejos.hardware.sensor.EV3ColorSensor
import lejos.hardware.sensor.EV3TouchSensor
import lejos.hardware.sensor.EV3UltrasonicSensor
import lejos.remote.ev3.RMISampleProvider
import lejos.robotics.SampleProvider
import lejos.robotics.navigation.RMIDifferentialPilot

import java.rmi.RemoteException

class EV3Robot {
	def robot
	
	def setup(config) {
		robot = new BasicRobot(config.name, config.address, config.motorA, config.motorB, config.motorC)
		
		if (robot.isConnected()) {
			/* Robot set up */
			def brick = robot.getBrick();
			def claudia_motorLeft = brick.getMotorC();
			def claudia_motorRight = brick.getMotorA();
			def pilot = new RMIDifferentialPilot(5, 5, 11, claudia_motorLeft, claudia_motorRight,false);
			pilot.setTravelSpeed(10);
			pilot.setRotateSpeed(15);
			robot.setPilot(pilot);
			
			if (config.sensor.equals('ultrasonic')) {
				try {
                    EV3UltrasonicSensor uSensor = new EV3UltrasonicSensor(brick.getSensorPort(1));


                    //RMISampleProvider sp =
                    //        robot.brick.createSampleProvider("S1", "lejos.hardware.sensor.EV3UltrasonicSensor", "distance");

                    //sp.fetchSample()
                    /*SampleProvider sp = uSensor.getDistanceMode()
                    float[] sample = new float[1]
                    sp.fetchSample(sample,0)
                    println(sample)
*/
                    robot.setSensor(1,uSensor);
                } catch (RemoteException re) {
                    re.printStackTrace()
                    System.out.println("Error initializing ultrasonic sensor RemoteException - shutting down.")
                    teardown()
                } catch (PortException pe) {
                    pe.printStackTrace()
                    System.out.println("Error initializing ultrasonic sensor - PortException shutting down."+pe.getMessage())
                    teardown()
                } catch (Exception e) {
                    e.printStackTrace()
                    System.out.println("Error initializing ultrasonic sensor Exception - shutting down.")
                    teardown()
                }

			} else if (config.sensor.equals('touch')) {
				EV3TouchSensor tSensor = new EV3TouchSensor(brick.getSensorPort(1));
				robot.setSensor(1, tSensor);
			} else if (config.sensor.equals('light')) {
				EV3ColorSensor lSensor = new EV3ColorSensor(brick.getSensorPort(1));
				robot.setSensor(1, lSensor);
			}
		}
	}
	
	def forward() {
		robot.pilot.forward()
	}
	
	def left() {
		robot.pilot.rotateLeft()
	}
	
	def right() {
		robot.pilot.rotateRight()
	}
	
	def backward() {
		robot.pilot.backward()
	}
	
	def stop() {
		robot.pilot.stop()
	}
	
	def sense(config) {
		int sensornumber = 1;
		if (config.sensor.equals('ultrasonic')) {
			return [distance:distance()]
		} else if (config.sensor.equals('touch')) {
			return [pressed:pressed()]
		} else if (config.sensor.equals('sound')) {
			def value = ((EV3UltrasonicSensor) robot.getSensor(1)).readValue()
			return [sound:value]
		} else if (config.sensor.equals('light')) {
			def value = ((EV3ColorSensor) robot.getSensor(1)).getLightValue();
			return [light:value]
		}
	}
	
	def distance() {
        if (robot?.getSensor(1)) {
            SampleProvider sensor = robot.getSensor(1).getDistanceMode()
            float[] sample = new float[1]
            sensor.fetchSample(sample,0)
            if (!Float.isInfinite(sample[0]))
               return sample
            else
               return -1
        }

	}
	
	def pressed() {
		((EV3TouchSensor) robot.getSensor(1)).isPressed()
	}
	
	def teardown() {

		robot.close()		
	}
}
