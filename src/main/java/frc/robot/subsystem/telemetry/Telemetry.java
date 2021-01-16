/**
 * two lidars
 * one boolean to see if the robot is parallel at a certain distance
 * 
 * boolean isSquare(double distance)
 * 
 */

package frc.robot.subsystem.telemetry;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.MedianFilter;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystem.PortMan;
import java.util.logging.Logger;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;


public class Telemetry extends SubsystemBase{
    
    //private LidarPWM frontLidar, rearLidar, buttLidar;
    private TalonSRX testMotor = new TalonSRX(portMan.acquirePort(PortMan.can_18_label, "Position.testMotor"));
    private static Logger logger = Logger.getLogger(Telemetry.class.getName());

    //distance between side sensors
    private double betweenDistance;
    private double tolerance = 5;
    private double targetDistance;

    private double x1, x2, y;

    public Telemetry(double distance, Position robotPosition) {
        targetDistance = distance;
        x1 = robotPosition.getx1();
        x2 = robotPosition.getx2();
        y = robotPosition.gety();
    }

    public boolean isSquare(){

        if (Math.abs(x1-x2) > tolerance)
            squareUp();
        return true;
        
    }
    
    public void squareUp(){

        if (!isSquare()){

            double errorDistance = Math.abs(x1-x2);
            double angleError = Math.atan(distanceError/betweenDistance);

            double distanceToMove = angleError*betweenLidarDistance;

            if (frontLidarDistance < rearLidarDistance){
                //move front wheels right distanceToMove
            } else{
                //move front wheels left distanceToMove
            }

            frontLidarDistance = frontLidar.getDistance();
            rearLidarDistance = rearLidar.getDistance();
        }

        while(!isSquare()){
            if (frontLidarDistance < rearLidarDistance){
                //move back wheels left correction, turn right
            } else{
                //move back wheels right correction, turn left
            }
            frontLidarDistance = frontLidar.getDistance();
            rearLidarDistance = rearLidar.getDistance();
        }     
        
    }

    public void moveHorizontal(){
        double distanceError = Math.abs(frontLidarDistance - targetDistance);

        if (distanceError > tolerance){
            if (frontLidarDistance > targetDistance){
                //move left distanceError
            } else{
                //move right distanceError
            }
        }
    }

    public void moveVertical(){
        double distanceError = Math.abs(frontLidarDistance - targetDistance);

        if (distanceError > tolerance){
            if (frontLidarDistance > targetDistance){
                //move left distanceError
            } else{
                //move right distanceError
            }
        }
    }
    
    public double getTolerance(){
        return tolerance;
    }

    public void setTolerance(double tol){
        lidarTolerance = tol;
    }

    public double getBetweenLidar(){
        return betweenLidarDistance;
    }

    public void testMotor(double po){
        testMotor.set(ControlMode.PercentOutput, po);
    }
}