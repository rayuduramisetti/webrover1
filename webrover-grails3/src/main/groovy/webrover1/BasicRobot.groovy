// ----------------------------------------------------------------------------
// Copyright (C) 2012 Louise A. Dennis, and  Michael Fisher 
//
// This file is part of the Engineering Autonomous Space Software (EASS) Library.
// 
// The EASS Library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 3 of the License, or (at your option) any later version.
// 
// The EASS Library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
// Lesser General Public License for more details.
// 
// You should have received a copy of the GNU Lesser General Public
// License along with the EASS Library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
// 
// To contact the authors:
// http://www.csc.liv.ac.uk/~lad
// Adapted and heavily modified by Ryan Vanderwerf 2014 to run on EV3 0.8.1
// contact: rvanderwerf@gmail.com or @RyanVanderwerf on twitter
//
//----------------------------------------------------------------------------

package webrover1;

import java.rmi.RemoteException;
import java.util.LinkedHashMap;

import lejos.remote.ev3.RMISampleProvider;
import lejos.robotics.navigation.RMIDifferentialPilot;
import webrover1.EV3Brick;

public class BasicRobot {
	EV3Brick brick;
	RMIDifferentialPilot pilot;
	boolean haspilot = false;


    LinkedHashMap sensors = new LinkedHashMap();
   /* LinkedHashMap sensor2;
    LinkedHashMap sensor3;
    LinkedHashMap sensor4;*/
	
	public BasicRobot(String name, String address, String motorAType, String motorBType, String motorCType) {

        brick = new EV3Brick(name, address, motorAType.charAt(0), motorBType.charAt(0), motorCType.charAt(0));
	}
	
	public boolean hasPilot() {
		return haspilot;
	}
	public void setPilot(RMIDifferentialPilot npilot) {
		pilot = npilot;
		haspilot = true;
	}
	public RMIDifferentialPilot getPilot() {
		return pilot;
	}

	public void setSensor(int portnumber, sensor) {
            sensors.put(portnumber,sensor)
	}

    /**
     * SampleProvider is what actually maintains the remote connection to the sensor
     * @param portnumber 1-4
     * @return
     */
	public Object getSensor(int portnumber) {
          if (portnumber <= sensors?.size())
		  sensors.get(portnumber)
	}

	
	public EV3Brick getBrick() {
		return brick;
	}
	
	public boolean isConnected() {

        return getBrick().isConnected();
	}


    /**
     * cleanup on shutdown
     */
	public void close() {
        closeSenors();
        // brick will close the motors too
		brick.close();
	}

    /**
     * close sensor sampleproviders so you don't have to reboot the robot (as much)
     */
    private void closeSenors() {

        sensors.each { sensor ->
            try {
                sensor?.close()
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

    }
}