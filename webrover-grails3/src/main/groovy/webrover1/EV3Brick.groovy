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
//
//----------------------------------------------------------------------------

package webrover1;

import lejos.hardware.BrickInfo;
import lejos.hardware.DeviceException;
import lejos.hardware.port.Port;
import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RMIRemoteEV3;
import lejos.remote.ev3.RMISampleProvider;
import lejos.remote.ev3.RemoteEV3;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;


/**
 * Abstraction of a LEGO EV3 Brick running LeJOS
 * 
 * @author louiseadennis
 * @author ryanvanderwerf
 *
 *  Adapted and heavily modified by Ryan Vanderwerf 2014 to run on EV3 0.8.1
 *  contact: rvanderwerf@gmail.com or @RyanVanderwerf on twitter
 */
public class EV3Brick {
	private String btname, btaddress;
	private RemoteEV3 comm;
	private BrickInfo nxt;
	private DataInputStream input;
	private DataOutputStream output;

	
	private boolean connected = false;
	
	public RMIRegulatedMotor motorA;
	public RMIRegulatedMotor motorB;
	public RMIRegulatedMotor motorC;



	public static String APort = "A";
	public static String BPort = "B";
	public static String CPort = "C";
	
	public static int SPort1 = 0;
	public static int SPort2 = 1;
	public static int SPort3 = 2;
	public static int SPort4 = 3;

	/**
	 * Construct an abstraction of an NXT Brick
	 * 
	 */
	public EV3Brick(String name,
                    String address,
                    char motorAType,
                    char motorBType,
                    char motorCType) {
		// first we create an AIL Agent.
		btname = name;
		btaddress = address;
		try {
			System.out.println("Connecting to " + btname + "(" + btaddress + ")");
			//openBluetooth();
            //bluetooth still iffy on EV3, use recommended RMI instead
            comm = new RemoteEV3(btaddress);
            connected = true;
			System.out.println("Connected to " + btname + "(" + btaddress + ")");
			
		} catch (RemoteException e) {
			System.out.println(e);
			e.printStackTrace(System.out);
			System.out.println("Could not open RMI connection to " + btname + "(" + btaddress + ")");
			connected = false;
		}  catch (NotBoundException nbe) {
            System.out.println(nbe);
            nbe.printStackTrace(System.out);
            System.out.println("Could not open RMI connection to " + btname + "(" + btaddress + ")");
            connected = false;
        } catch (MalformedURLException mue) {
            System.out.println(mue);
            mue.printStackTrace(System.out);
            System.out.println("Could not open RMI connection to " + btname + "(" + btaddress + ")");
            connected = false;
        }


        if (connected) {

            // values are N(NXT), L(EV3 Large), M (EV3 Medium), or G (MindsensorsGlideWheel)

            try {
                motorA = comm.createRegulatedMotor("A",motorAType);
            } catch (DeviceException e) {
                e.printStackTrace();
                close();
            }
            //motorB = comm.createRegulatedMotor("B",motorBType);
            try {
                motorC = comm.createRegulatedMotor("C",motorCType);
            } catch (DeviceException e) {
                e.printStackTrace();
                close();
            }

        }
		
	}
	
	public boolean isConnected() {
		return connected;
	}

    /**
     * Remote object of the regulated motor, be sure to close these
     * @return
     */
	public RMIRegulatedMotor getMotorA() {
		return motorA;
	}
	
	public RMIRegulatedMotor getMotorB() {
		return motorB;
	}
	
	public RMIRegulatedMotor getMotorC() {
		return motorC;
	}

    public RMISampleProvider createSampleProvider(String port, String sensorClass, String operation) {
        return comm.createSampleProvider(port,sensorClass,operation);


    }

    /**
     * Initial handle to the ports, which we will turn into RMISampleProviders later
     * @param i
     * @return
     */
	public Port getSensorPort(int i) {
		if (i == 1) {
			return comm.getPort("S1");
		}
		if (i == 2) {
            return comm.getPort("S2");
		}
		if (i == 3) {
            return comm.getPort("S3");
		}
		if (i == 4) {
            return comm.getPort("S4");
		}
		else return comm.getPort("S1");
	}


    /**
     * shut down ports nicely so we don't have to reboot the robot so much
     */
	public void close() {
		try {
			comm.getAudio().systemSound(0);

            try {
                output.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                input.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                getMotorA().close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                getMotorB().close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                getMotorC().close();
            } catch (Exception e) {
                e.printStackTrace();
            }



		} catch (Exception e) {
			System.out.println("Error closing comms");
		}
	}
	



}
