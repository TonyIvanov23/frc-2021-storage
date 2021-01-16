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
    private TalonSRX testMotor;
    private static Logger logger = Logger.getLogger(Telemetry.class.getName());

    //distance between side sensors
    private double betweenDistance;
    private double tolerance = 5;
    private Position target;
    private Position position;

    public Telemetry(Position robotPosition, Position targetPosition) {
        position = robotPosition;
        target = targetPosition;
    }

    public void init(PortMan portMan) throws Exception{
        logger.entering(Telemetry.class.getName(), "init()");
        
        testMotor = new TalonSRX(portMan.acquirePort(PortMan.can_18_label, "Telemetry.testMotor"));

        CameraServer.getInstance().startAutomaticCapture();

        logger.exiting(Telemetry.class.getName(), "init()");
    }

    public boolean isSquare(){
        if (Math.abs(position.getx1()-position.getx2()) > tolerance)
            squareUp();
        return true;
    }
    
    public void squareUp(){
        if (!isSquare()){
            double errorDistance = Math.abs(position.getx1()-position.getx2());
            double angleError = Math.atan(distanceError/betweenDistance);

            double distanceToMove = angleError*betweenLidarDistance;

            if (position.getx1() < position.getx2()){
                //move front wheels right distanceToMove
            } else{
                //move front wheels left distanceToMove
            }

            position.updatePosition(); 
        }

        while(!isSquare()){
            if (position.getx1() < position.getx2()){
                //move back wheels left correction, turn right
            } else{
                //move back wheels right correction, turn left
            }
            
            position.updatePosition();
        }     
    }

    public void moveHorizontal(){
        double distanceError = Math.abs(position.getx1() - target.getx1());

        if (distanceError > tolerance){
            if (position.getx1() > target.getx1()){
                //move left distanceError
            } else{
                //move right distanceError
            }
        }
    }

    public void moveVertical(){
        double distanceError = Math.abs(position.gety() > target.gety());

        if (distanceError > tolerance){
            if (position.gety() < target.gety()){
                //move forward distanceError
            } else{
                //move back distanceError
            }
        }
    }
    
    public double getTolerance(){
        return tolerance;
    }

    public void setTolerance(double tol){
        tolerance = tol;
    }

    public double getBetween(){
        return betweenDistance;
    }

    public void testMotor(double po){
        testMotor.set(ControlMode.PercentOutput, po);
    }
}