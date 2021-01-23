/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystem.telemetry;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.subsystem.SBInterface;

/**
 * Add your docs here.
 */
public class PositionSBTab implements SBInterface {
    public Position position;
    public ShuffleboardTab tab;

    public NetworkTableEntry yCoordinate;
    public NetworkTableEntry xCoordinate;
    public NetworkTableEntry isSquare;
    public NetworkTableEntry tolerance;

    public PositionSBTab(Position p){
        position = p;
        
        tab = Shuffleboard.getTab("Position");

        yCoordinate = tab.add("Front Lidar Distance", 0).getEntry();
        xCoordinate = tab.add("Rear Lidar Distance", 0).getEntry();
        //isSquare = tab.add("Is Squared?", false).getEntry();
        //tolerance = tab.add("Lidar Tolerance", 0.0).getEntry();
    }
    public void update(){
        //isSquare.setBoolean(telemetry.isSquare());
        yCoordinate.setDouble(position.gety1());
        xCoordinate.setDouble(position.getx());
        //isSquare.setBoolean(telemetry.isSquare(5));
    }
}