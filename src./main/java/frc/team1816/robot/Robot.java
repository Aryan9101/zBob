package frc.team1816.robot;

import com.edinarobotics.utils.gamepad.Gamepad;
import com.edinarobotics.utils.log.Logging;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team1816.robot.commands.*;
import frc.team1816.robot.subsystems.Collector;
import frc.team1816.robot.subsystems.Drivetrain;
import frc.team1816.robot.subsystems.Elevator;

public class Robot extends IterativeRobot {

    public static Logging logger;
    private Drivetrain drivetrain;
    private Elevator elevator;
    private Collector collector;

    private SendableChooser<Command> autoChooser;

    private LeftAutoStartCommand leftAuto;
    private RightAutoStartCommand rightAuto;

    private NetworkTable table;

    public void robotInit() {
        Components.getInstance();
        Controls.getInstance();
        table = NetworkTable.getTable("Shuffleboard_PID");

        drivetrain = Components.getInstance().drivetrain;
        elevator = Components.getInstance().elevator;
        collector = Components.getInstance().collector;

        //leftAuto = new LeftAutoStartCommand();
        //rightAuto = new RightAutoStartCommand();

        autoChooser = new SendableChooser<>();
        //autoChooser.addObject("Left Start Auto", leftAuto);
        //autoChooser.addObject("Right Start Auto", rightAuto);
//        autoChooser.addObject("Center Start Auto", new CenterAutoStartCommand());
        //autoChooser.addDefault("Auto-Run", new DriveXInchesCommand(100, 0.8, false));

        SmartDashboard.putData("Autonomous", autoChooser);

//         table.putNumber("Left P", drivetrain.p_L);
//         table.putNumber("Left I", drivetrain.i_L);
//         table.putNumber("Left D", drivetrain.d_L);
//         table.putNumber("Left F", drivetrain.f_L);
//         table.putNumber("Left izone", drivetrain.izone_L);
//
//         table.putNumber("Right P", drivetrain.p_R);
//         table.putNumber("Right I", drivetrain.i_R);
//         table.putNumber("Right D", drivetrain.d_R);
//         table.putNumber("Right F", drivetrain.f_R);
//         table.putNumber("Right izone", drivetrain.izone_R);
    }

    @Override
    public void disabledInit() {
    }

    @Override
    public void autonomousInit() {
        logger = new Logging("AutoLog");
        drivetrain.resetEncoders();

        try {
            leftAuto.selectAutoL();
            rightAuto.selectAutoR();
        } catch (Exception e) {
            System.out.println("-----AUTO ALREADY CREATED, RUNNING PREVIOUS-----");
        }

        Command autoCommand = autoChooser.getSelected();

//        Command autoCommand = new ArcDriveCommand(48, 0.4, 90);
//        Command autoCommand = new RotateXDegreesCommand(90);
//        Command autoCommand = new DriveXInchesCommand(48,0.5, false);

        System.out.println("Auto Running: " + autoCommand.getName());
        autoCommand.start();
    }

    @Override
    public void teleopInit() {
        logger = new Logging("TeleopLog1");

        Gamepad gamepad0 = Controls.getInstance().gamepad0;
        //Gamepad gamepad1 = Controls.getInstance().gamepad1;

        drivetrain.resetEncoders();
        drivetrain.setDefaultCommand(new GetPDPStats(gamepad0));
        //elevator.setDefaultCommand(new GamepadElevatorCommand(gamepad1));

        // collector.setDefaultCommand(new GamepadCollectorCommand(gamepad1));

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
//        System.out.println("L Velocity (ticks/100ms): " + drivetrain.getLeftTalonVelocity());
//        System.out.println("R Velocity (ticks/100ms): " + drivetrain.getRightTalonVelocity());
//        System.out.println("Left Ticks (grayhill): " + drivetrain.talonPositionLeft());
//        System.out.println("Right Ticks (grayhill): " + drivetrain.talonPositionRight());
//        System.out.println("Gyro: " + drivetrain.getGyroAngle());
        //System.out.println("Elevator ticks: " + elevator.getTicks());

        Scheduler.getInstance().run();
    }

    @Override
    public void testPeriodic() {
        LiveWindow.run();
    }

    public void closeLogger() {
        logger.close();
    }
}