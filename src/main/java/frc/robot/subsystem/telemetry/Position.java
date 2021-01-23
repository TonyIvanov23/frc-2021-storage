package frc.robot.subsystem.telemetry;

import edu.wpi.first.cameraserver.CameraServer;
//import edu.wpi.first.wpilibj.MedianFilter;
//import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystem.PortMan;
import java.util.logging.Logger;

//import com.ctre.phoenix.motorcontrol.ControlMode;
//import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Position{
    //x1 = frontSensor, x2 = rearSensor, y = buttSensor
    private double y1;
    private double y2;
    private double x;

    private LidarPWM frontLidar, rearLidar, buttLidar;
    //private TalonSRX testMotor;
    //private double frontLidarOffset = 0;
    //private double rearLidarOffset = 0;

    private static Logger logger = Logger.getLogger(Position.class.getName());

    //private MedianFilter filterFront;
    //private MedianFilter filterRear;
    //private MedianFilter filterButt;

    //private double frontLidarDistance;
    //private double rearLidarDistance;
    //private double buttLidarDistance;

    private double targetx;
    private double targety;

    private double halfLength = 38.1;

    public Position(){

    }

    public Position(double x, double y){
        this.targetx = x;
        this.targety = y;
    }

    public void init(PortMan portMan) throws Exception{
        logger.entering(Position.class.getName(), "init()");

        frontLidar = new LidarPWM(portMan.acquirePort(PortMan.digital6_label, "Position.frontLidar"));
        rearLidar = new LidarPWM(portMan.acquirePort(PortMan.digital1_label, "Position.rearLidar"));
        buttLidar = new LidarPWM(portMan.acquirePort(PortMan.digital2_label, "Position.buttLidar"));
        //filterFront = new MedianFilter(10);
        //filterRear = new MedianFilter(10);
        //filterButt = new MedianFilter(10);
        //testMotor = new TalonSRX(portMan.acquirePort(PortMan.can_18_label, "Position.testMotor"));

        CameraServer.getInstance().startAutomaticCapture();

        logger.exiting(Position.class.getName(), "init()");
    }

    public void updateLidars(){
        y1 = frontLidar.getDistance() + halfLength;
        y2 = rearLidar.getDistance() + halfLength;
        x = buttLidar.getDistance() + halfLength;
    }

    public void updateUltrasound(){
        //whatever you do for ultrasound???
    }

    public void updatePosition(){
        updateLidars();
        //updateUltrasound();
        //some fusion of lidars and ultrasound to make the position accurate, then update x1, x2, y
    }

    /*public double getFrontLidarDistance(){
        return filterFront.calculate(frontLidar.getDistance() - 10);
    }

    public double getRearLidarDistance(){
        return filterRear.calculate(rearLidar.getDistance());
    }*/

    public double gety1(){
        return y1;
    }

    public double gety2(){
        return y2;
    }

    public double getx(){
        return x;
    }

    public double getTargetx()
    {
        return targetx;
    }

    public double getTargety()
    {
        return targety;
    }
}