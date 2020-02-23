package frc.robot.subsystem.transport;

import java.util.logging.Logger;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANPIDController;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.MedianFilter;
import edu.wpi.first.wpilibj.PWMTalonSRX;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystem.PortMan;
import frc.robot.subsystem.SubsystemFactory;
import frc.robot.subsystem.transport.commands.TakeIn;
import edu.wpi.first.wpilibj.DigitalInput;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Transport extends SubsystemBase {

    private static Logger logger = Logger.getLogger(Transport.class.getName());

    private DoubleSolenoid doubleSolenoidLeft;
    private DoubleSolenoid doubleSolenoidRight;
    private PWMTalonSRX bottomTransport;
    private PWMTalonSRX topTransport;
    private double motorTopSpeedForward;
    private double motorBottomSpeedForward;
    private double motorSpeedBackward;
    private double shooterSpeed;

    private int ballCount = 0;
    private boolean pastValue1 = false;
    private boolean pastValue2 = false;
    private DigitalInput transportReceiverSwitch;
    private DigitalInput transportSenderSwitch;

    private boolean gateUp;
    private double transportTime;

    private PowerDistributionPanel pdp;
    private boolean isBeamSensorBroken;
    private boolean isBallInMotion;

    //private Counter ballCount;
    public Transport() {
    }

    public void init(PortMan portMan) throws Exception {
        logger.entering(Transport.class.getName(), "init()");

        // sideGate
        doubleSolenoidLeft = new DoubleSolenoid(portMan.acquirePort(PortMan.pcm6_label, "Transport.gate2"), portMan.acquirePort(PortMan.pcm7_label, "Transport.gate3"));
        // tailgate
        doubleSolenoidRight = new DoubleSolenoid(portMan.acquirePort(PortMan.pcm2_label, "Transport.gate4"), portMan.acquirePort(PortMan.pcm3_label, "Transport.gate5"));
        gateUp = false;
        
        transportReceiverSwitch = new DigitalInput(portMan.acquirePort(PortMan.digital0_label, "Transport.IntakeEnterSensor"));
        transportSenderSwitch = new DigitalInput(portMan.acquirePort(PortMan.digital1_label, "Transport.IntakeExitSensor"));

        bottomTransport = new PWMTalonSRX(portMan.acquirePort(PortMan.pwm6_label, "Transport.LeftIntake"));
        topTransport = new PWMTalonSRX(portMan.acquirePort(PortMan.pwm7_label, "Transport.RightIntake"));
        //ballCount = new Counter(Counter.Mode.kSemiperiod)

        /*
        leftIntake.config_kP(0, .5, 0);
        leftIntake.config_kI(0, 0, 0);
        leftIntake.config_kD(0, 0, 0);
        leftIntake.config_kF(0, 0, 0);
        leftIntake.configMotionCruiseVelocity(4096, 0);
        leftIntake.configMotionAcceleration(4096, 0);

        rightIntake.config_kP(0, .5, 0);
        rightIntake.config_kI(0, 0, 0);
        rightIntake.config_kD(0, 0, 0);
        rightIntake.config_kF(0, 0, 0);
        rightIntake.configMotionCruiseVelocity(4096, 0);
        rightIntake.configMotionAcceleration(4096, 0);
        */

        transportTime = 1.25;

        //ballCount.setUpSource(enterSwitch);
        //ballCount.setSemiPeriodMode(true);

        isBeamSensorBroken = false;
        isBallInMotion = false;
        shooterSpeed = .9;
        motorTopSpeedForward = .7;
        motorBottomSpeedForward= .5;
        motorSpeedBackward = .5;

        setDefaultCommand(new TakeIn(this));

        logger.exiting(Transport.class.getName(), "init()");
    }

    public void moveSideGateOpen(){
        doubleSolenoidLeft.set(DoubleSolenoid.Value.kReverse);
        gateUp = true;
    }
    public void moveSideGateClose(){
      doubleSolenoidLeft.set(DoubleSolenoid.Value.kForward);
      gateUp = false;
    }
    public void moveTailGateUp(){
        doubleSolenoidRight.set(DoubleSolenoid.Value.kForward);
    }
    public void moveTailGateDown(){
        doubleSolenoidRight.set(DoubleSolenoid.Value.kReverse);
    }
    public boolean getGateStatus(){
      return gateUp;
    }
    public void shoot(){
        ballCount = 0;
        bottomTransport.set(shooterSpeed);
        topTransport.set(-shooterSpeed);
    }

    public void take() {
        logger.info("take");
        isBallInMotion = true;
        bottomTransport.set(motorBottomSpeedForward);
        topTransport.set(-motorTopSpeedForward);
    }

    public void expel() {
        logger.info("expel");
        isBeamSensorBroken = true;
        bottomTransport.set(-motorSpeedBackward);
        topTransport.set(motorSpeedBackward);
    }

    public void stop() {
        isBeamSensorBroken = false;
        isBallInMotion = false;
        bottomTransport.set(0);
        topTransport.set(0);
    }

    public int count() {
        return 0;
    }
    /*
    public double getVelocity() {
        return leftIntake.getSelectedSensorVelocity();
    }
    */

    public int getBallCount() {
        if(getTransportReceiverSwitch() == true && pastValue1 == false){
            ballCount++;
        }
        if(getTransportSenderSwitch() == true && pastValue2 == false){
            ballCount++;
        }
        pastValue1 = getTransportReceiverSwitch();
        pastValue2 = getTransportSenderSwitch();
        /*
        if(ballCount >= 5){
            leftIntake.set(ControlMode.PercentOutput,0);
            rightIntake.set(ControlMode.PercentOutput,0);
        }
        */
        return ballCount;
    }

    public boolean getTransportReceiverSwitch(){
        if(!isBeamSensorBroken)
            return transportReceiverSwitch.get();
        return true;
    }

    public boolean getTransportSenderSwitch(){
        return transportSenderSwitch.get();
    }
    
    public void update(){
        
    }
    public double getTime(){
        return Timer.getFPGATimestamp();
    }
    public void setTargetTime(double a){
        transportTime = a;
    }
    public double getTargetTime(){
        return transportTime;
    }
    public void setMotorSpeedForward(double a){
        motorTopSpeedForward = a;
    }
    public void setMotorSpeedBackward(double a){
        motorSpeedBackward = a;
    }
    public double getTopMotorSpeedForward(){
        return motorTopSpeedForward;
    }
    public double getBottomMotorSpeedForward(){
        return motorBottomSpeedForward;
    }
    public void setTopMotorSpeedForward(double a){
        motorTopSpeedForward = a;
    }
    public void setBottomMotorSpeedForward(double a){
        motorBottomSpeedForward = a;
    }
    public double getMotorSpeedBackward(){
        return motorSpeedBackward;
    }
    public double getCurrent(){
        return SubsystemFactory.getInstance().getPDP().getCurrent(13) + SubsystemFactory.getInstance().getPDP().getCurrent(12);
    }
    public double getShooterSpeed(){
        return shooterSpeed;
    }
    public void setShooterSpeed(double a){
        shooterSpeed = a;
    }
}
