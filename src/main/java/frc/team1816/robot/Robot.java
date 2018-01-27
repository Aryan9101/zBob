package frc.team1816.robot;

import com.edinarobotics.utils.gamepad.Gamepad;
import com.edinarobotics.utils.log.Logging;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team1816.robot.commands.*;
import frc.team1816.robot.subsystems.Drivetrain;

public class Robot extends IterativeRobot {

    public static Logging logger;
    private Drivetrain drivetrain;
    private double time;
    private SendableChooser<Command> autoChooser;
    private int _loops = 0;

    public void robotInit() {
        Components.getInstance();
        Controls.getInstance();
        logger = new Logging("LogTest");

        drivetrain = Components.getInstance().drivetrain;

        autoChooser = new SendableChooser<>();
        autoChooser.addObject("Left Start Auto", new LeftAutoStartCommand());
        autoChooser.addObject("Right Start Auto", new RightAutoStartCommand());
        autoChooser.addObject("Center Start Auto", new CenterAutoStartCommand());
        autoChooser.addDefault("Auto-Run", new DriveXInchesCommand(100, 0.8, false));

        SmartDashboard.putData("Autonomous", autoChooser);
    }

    @Override
    public void disabledInit() {
    }

    @Override
    public void autonomousInit() {
        //String gamedata;
        drivetrain.resetEncoders();
//        gamedata = DriverStation.getInstance().getGameSpecificMessage();

        Command autoCommand = autoChooser.getSelected();

//        Command autoCommand = new RotateXDegreesCommand(90);
//        Command autoCommand = new DriveXInchesCommand(120,0.2, true);
        autoCommand.start();
    }

    @Override
    public void teleopInit() {
        Gamepad gamepad0 = Controls.getInstance().gamepad0;
        Gamepad gamepad1 = Controls.getInstance().gamepad1;

        drivetrain.resetEncoders();
        drivetrain.setDefaultCommand(new GamepadDriveCommand(gamepad0));
    }

    @Override
    public void testInit() {

    }

    @Override
    public void disabledPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void testPeriodic() {
        LiveWindow.run();
    }
}