package frc.robot.subsystem.telemetry.commands;

import java.util.logging.Logger;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystem.telemetry.Telemetry;
import frc.robot.subsystem.telemetry.Position;

public class Auton extends CommandBase{

    int targetx = 10;
    int targety = 10;
    Position position = new Position();
    Position target = new Position(targetx, targety);
    private Telemetry telemetry;

    public Auton(Telemetry t)
    {
        telemetry = t;
    }

    public void initialize()
    {

    }

    public void execute()
    {
        position.updatePosition();
        //telemetry.isSquare();
        telemetry.moveVertical(position, target);
    }
}