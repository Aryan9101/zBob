package frc.team1816.robot.commands;

import com.edinarobotics.utils.gamepad.Gamepad;
import com.edinarobotics.utils.log.Logger;
import com.edinarobotics.utils.log.Logging;
import edu.wpi.first.wpilibj.command.Command;
import frc.team1816.robot.Components;
import frc.team1816.robot.Robot;
import frc.team1816.robot.subsystems.Collector;
import frc.team1816.robot.subsystems.Drivetrain;
import frc.team1816.robot.subsystems.Elevator;

public class GamepadDriveCommand extends Command {

    private Drivetrain drivetrain;
    private Collector collector;
    private Gamepad gamepad;
    public StringBuilder sb;

    public GamepadDriveCommand(Gamepad gamepad) {
        super("gamepaddrivecommand");
        this.drivetrain = Components.getInstance().drivetrain;
        this.collector = Components.getInstance().collector;
        this.gamepad = gamepad;
        requires(drivetrain);
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void execute() {
        double right = gamepad.getLeftY();
        double left = gamepad.getLeftY();
        double rotation = gamepad.getRightX();

//        System.out.println("left enc: " + drivetrain.talonPositionLeft() + " right enc: " + drivetrain.talonPositionRight());
//        System.out.println("left spd: " + drivetrain.getLeftTalonVelocity() + "right spd: " + drivetrain.getRightTalonVelocity());
//
//        System.out.println("gyro angle: " + drivetrain.getGyroAngle());
//        System.out.println("gyro connected: " + drivetrain.gyroActiveCheck());

//        System.out.println("inches traveled: " + drivetrain.getLeftTalonInches());

        if(Math.abs(gamepad.getLeftY()) < 0.03) {
            drivetrain.setDrivetrainPercent(left, right, rotation);
        }
        else {
            drivetrain.setDrivetrain(left, right, rotation);
        }

        if(gamepad.rightTrigger().get()) {
            collector.setCollectorSpeed(0.5,0.5);
        }
    }

    @Override
    protected boolean isFinished() {
//        System.out.println("GamePadDrive Command Terminated");
        return false;
    }
}
