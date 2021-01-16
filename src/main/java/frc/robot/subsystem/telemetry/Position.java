package frc.robot.subsystem.position;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.MedianFilter;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystem.PortMan;
import java.util.logging.Logger;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Position{

    //x1 = frontSensor, x2 = rearSensor, y = buttSensor
    private double x1;
    private double x2;
    private double y;

    private LidarPWM frontLidar, rearLidar, buttLidar;
    //private TalonSRX testMotor;
    private double frontLidarOffset = 0;
    private double rearLidarOffset = 0;

    private static Logger logger = Logger.getLogger(Position.class.getName());

    private MedianFilter filterFront;
    private MedianFilter filterRear;
    private MedianFilter filterButt;

    public Position(){

    }

    public void init(PortMan portMan) throws Exception{
        logger.entering(Position.class.getName(), "init()");

        frontLidar = new LidarPWM(portMan.acquirePort(PortMan.digital0_label, "Position.frontLidar"));
        rearLidar = new LidarPWM(portMan.acquirePort(PortMan.digital1_label, "Position.rearLidar"));
        buttLidar = new LidarPWM(portMan.acquirePort(PortMan.digital2_label, "Position.bttLidar"));
        filterFront = new MedianFilter(10);
        filterRear = new MedianFilter(10);
        filterButt = new MedianFilter(10);
        //testMotor = new TalonSRX(portMan.acquirePort(PortMan.can_18_label, "Position.testMotor"));

        CameraServer.getInstance().startAutomaticCapture();

        logger.exiting(Position.class.getName(), "init()");
    }

    public void updateLidars(){
        frontLidarDistance = frontLidar.getDistance();
        rearLidarDistance = rearLidar.getDistance();
        buttLidarDistance = buttLidar.getDistance();
    }

    public void updateUltrasound(){
        //whatever you do for ultrasound???
    }

    public void updatePosition(){
        //some fusion of lidars and ultrasound to make the position accurate, then update x1, x2, y
    }

    /*public double getFrontLidarDistance(){
        return filterFront.calculate(frontLidar.getDistance() - 10);
    }

    public double getRearLidarDistance(){
        return filterRear.calculate(rearLidar.getDistance());
    }*/

    public double getx1(){
        return x1;
    }

    public double getx2(){
        return x2;
    }

    public double gety(){
        return y;
    }
}