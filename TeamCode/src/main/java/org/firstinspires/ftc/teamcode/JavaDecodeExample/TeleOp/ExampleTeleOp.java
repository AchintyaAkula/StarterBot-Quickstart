package org.firstinspires.ftc.teamcode.JavaDecodeExample.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Example TeleOp", group = "EveryBot Quickstart")
public class ExampleTeleOp extends OpMode {
    // Drive motors declaration
    private DcMotorEx frontLeftDriveMotor;
    private DcMotorEx frontRightDriveMotor;
    private DcMotorEx backLeftDriveMotor;
    private DcMotorEx backRightDriveMotor;

    // Flywheel motors declaration
    private DcMotorEx leftFlywheelMotor;
    private DcMotorEx rightFlywheelMotor;
    // 4 speed flywheel system
    public static final Double speed1Velocity = 0.0;
    public static final Double speed2Velocity = 0.0;
    public static final Double speed3Velocity = 0.0;
    public static final Double speed4Velocity = 0.0;

    // Intake motor declaration
    private DcMotorEx intakeMotor;

    // Shooter gate servo declaration
    private Servo shooterGateServo;
    // Pre-defined servo positions to open and close gate
    public static final Double gateOpenPosition = 0.0;
    public static final Double gateClosePosition = 0.0;

    @Override
    public void init() {
        // Assign drive motors
        frontLeftDriveMotor = hardwareMap.get(DcMotorEx.class, "frontLeftDriveMotor");
        frontRightDriveMotor = hardwareMap.get(DcMotorEx.class, "frontRightDriveMotor");
        backLeftDriveMotor = hardwareMap.get(DcMotorEx.class, "backLeftDriveMotor");
        backRightDriveMotor = hardwareMap.get(DcMotorEx.class, "backRightDriveMotor");
        // Set directions of drive motors
        frontLeftDriveMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRightDriveMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        backLeftDriveMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightDriveMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        // Assign flywheel motors
        leftFlywheelMotor = hardwareMap.get(DcMotorEx.class, "leftFlywheelMotor");
        rightFlywheelMotor = hardwareMap.get(DcMotorEx.class, "rightFlywheelMotor");
        // Set directions of flywheel motors
        leftFlywheelMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFlywheelMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        // Assign intake motor
        intakeMotor = hardwareMap.get(DcMotorEx.class, "intakeMotor");
        // Set direction of intake motor
        intakeMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        // Assign shooter gate servo
        shooterGateServo = hardwareMap.get(Servo.class, "shooterGateServo");
        // Set default position of shooter gate servo to closed
        shooterGateServo.setPosition(gateClosePosition);
    }
    @Override
    public void loop() {
        // Update all parts of the bot
        updateEverybot();
    }

    // Method to update all components
    public void updateEverybot() {
        updateDrive();
        updateIntake();
        updateShooterGate();
        updateFlywheelVelocity();
    }
    // Method to calculate and set drive powers
    public void updateDrive() {
        // Retrieve gamepad inputs for drive
        double drivePower = -gamepad1.left_stick_y;
        double strafePower = -gamepad1.left_stick_x * 1.1;
        double turnPower = gamepad1.right_stick_x;

        // Power limit is the greatest power a motor can have, which will keep all motors on the same ratio
        double powerLimit = Math.max(Math.abs(drivePower) + Math.abs(strafePower) + Math.abs(turnPower), 1.0);

        // Calculate powers for each wheel using gamepad inputs
        double frontLeftDrivePower = (drivePower + strafePower + turnPower) / powerLimit;
        double frontRightDrivePower = (drivePower - strafePower + turnPower) / powerLimit;
        double backLeftDrivePower = (drivePower - strafePower - turnPower) / powerLimit;
        double backRightDrivePower = (drivePower + strafePower - turnPower) / powerLimit;

        // Assign the respective powers to the drive motors
        frontLeftDriveMotor.setPower(frontLeftDrivePower * 0.9);
        frontRightDriveMotor.setPower(frontRightDrivePower * 0.9);
        backLeftDriveMotor.setPower(backLeftDrivePower * 0.9);
        backRightDriveMotor.setPower(backRightDrivePower * 0.9);
    }
    // Method to calculate and set intake power
    public void updateIntake() {
        // Retrieves trigger values and sets it to the intake motor
        double intakePower = gamepad1.right_trigger - gamepad1.left_trigger;
        intakeMotor.setPower(intakePower);
    }
    // Method to update shooter gate: close/open
    public void updateShooterGate() {
        // Toggling system to check what is the current position when button pressed, and set position based on that
        if (gamepad1.rightBumperWasPressed()) {
            if (shooterGateServo.getPosition() == gateOpenPosition) {
                shooterGateServo.setPosition(gateClosePosition);
            } else {
                shooterGateServo.setPosition(gateOpenPosition);
            }
        }
    }
    // Method to update flywheel speeds: speed 1, speed 2, speed 3, speed 4
    public void updateFlywheelVelocity() {
        if (gamepad1.xWasPressed()) {
            leftFlywheelMotor.setVelocity(speed1Velocity);
            rightFlywheelMotor.setVelocity(speed1Velocity);
        } else if (gamepad1.yWasPressed()) {
            leftFlywheelMotor.setVelocity(speed2Velocity);
            rightFlywheelMotor.setVelocity(speed2Velocity);
        } else if (gamepad1.bWasPressed()) {
            leftFlywheelMotor.setVelocity(speed3Velocity);
            rightFlywheelMotor.setVelocity(speed3Velocity);
        } else if (gamepad1.aWasPressed()) {
            leftFlywheelMotor.setVelocity(speed4Velocity);
            rightFlywheelMotor.setVelocity(speed4Velocity);
        }
    }
}