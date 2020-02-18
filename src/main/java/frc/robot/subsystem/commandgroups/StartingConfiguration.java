/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystem.commandgroups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystem.controlpanel.ControlPanel;
import frc.robot.subsystem.controlpanel.commands.SpinnerUp;
import frc.robot.subsystem.intake.Intake;
import frc.robot.subsystem.intake.commands.IntakeStop;
import frc.robot.subsystem.intake.commands.IntakeUp;
import frc.robot.subsystem.onewheelshooter.OneWheelShooter;
import frc.robot.subsystem.onewheelshooter.commands.OneWheelStop;
import frc.robot.subsystem.transport.Transport;
import frc.robot.subsystem.transport.commands.SideGateClose;
import frc.robot.subsystem.transport.commands.StopTransport;
import frc.robot.subsystem.transport.commands.TailGateUp;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class StartingConfiguration extends SequentialCommandGroup {
  /**
   * Creates a new StartingConfiguration.
   */
  public StartingConfiguration(Transport t, Intake i, ControlPanel cp, OneWheelShooter s) {
    super(new TailGateUp(t), new SideGateClose(t), new StopTransport(t), new OneWheelStop(s), new IntakeUp(i), new IntakeStop(i), new SpinnerUp(cp));
  }
}
