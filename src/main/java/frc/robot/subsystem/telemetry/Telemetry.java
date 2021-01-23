/**
 * two lidars
 * one boolean to see if the robot is parallel at a certain distance
 * 
 * boolean isSquare(double distance)
 * 
 */

package frc.robot.subsystem.telemetry;

import edu.wpi.first.cameraserver.CameraServer;
//import edu.wpi.first.wpilibj.MedianFilter;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystem.PortMan;
import java.util.logging.Logger;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.geometry.Translation2d;
import frc.robot.subsystem.SubsystemFactory;


public class Telemetry extends SubsystemBase{ 
    //private LidarPWM xLidar, yLidar, buttLidar;
    private TalonSRX testMotor;


    private static Logger logger = Logger.getLogger(Telemetry.class.getName());

    private Position target;
    private Position position;
    private double speed = 0.3;
    private double movementTolerance = 7.6;
    private double rotationTolerance = 2;

    public Telemetry(Position robotPosition, Position targetPosition) {
        position = robotPosition;
        target = targetPosition;
    }
    
    public void init(PortMan portMan) throws Exception{
        logger.entering(Telemetry.class.getName(), "init()");
        
        testMotor = new TalonSRX(portMan.acquirePort(PortMan.can_18_label, "Telemetry.testMotor"));

        //Assign lidars to portman
        //xLidar = new LidarPWM(portMan.acquirePort(PortMan.digital0_label, "Telemetry.xlidar"));
        //yLidar = new LidarPWM(portMan.acquirePort(PortMan.digital1_label, "Telemetry.xlidar"));


        CameraServer.getInstance().startAutomaticCapture();

        logger.exiting(Telemetry.class.getName(), "init()");
    }

    public boolean isSquare(){
        if (Math.abs(position.gety1()-position.gety2()) > rotationTolerance)
            squareUp();
        return true;
    }
    
    public void squareUp(){
        position.updatePosition();
        while(!isSquare()){
            if (position.gety1() < position.gety2() - rotationTolerance){
                SubsystemFactory.getInstance().getDriveTrain().drive(new Translation2d(0, 0), speed ,true);
            } else if(position.gety1() > position.gety2() + rotationTolerance){
                SubsystemFactory.getInstance().getDriveTrain().drive(new Translation2d(0, 0), -speed ,true);
            }
            
            position.updatePosition();
        }     
    }

    public void moveHorizontal(Position r, Position t){
        position = r;
        target = t;
        position.updatePosition();
        while(position.getx() > target.getTargetx() + movementTolerance || position.getx() < target.getTargetx() + movementTolerance)
        {
            if (position.getx() > target.getTargetx() + movementTolerance){
                //move up
                SubsystemFactory.getInstance().getDriveTrain().drive(new Translation2d(speed, 0), 0 ,true);
            } else{
                //move down
                SubsystemFactory.getInstance().getDriveTrain().drive(new Translation2d(-speed, 0), 0 ,true);
            }

            position.updatePosition();
        }
    }

    public void moveVertical(Position r, Position t){
        position = r;
        target = t;
        position.updatePosition();
        while(position.gety1() > target.getTargety() + movementTolerance || position.gety1() < target.getTargety() + movementTolerance)
        {
            if (position.gety1() < target.getTargety() + movementTolerance){
                //move up
                SubsystemFactory.getInstance().getDriveTrain().drive(new Translation2d(0, speed), 0,true);
            } else{
                //move down
                SubsystemFactory.getInstance().getDriveTrain().drive(new Translation2d(0, speed), 0, true);
            }

            position.updatePosition();

        }

    }

    public void testMotor(double po){
        testMotor.set(ControlMode.PercentOutput, po);
    }
}