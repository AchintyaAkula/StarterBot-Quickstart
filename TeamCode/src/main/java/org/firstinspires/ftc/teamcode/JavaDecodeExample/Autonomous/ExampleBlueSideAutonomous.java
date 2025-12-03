package org.firstinspires.ftc.teamcode.JavaDecodeExample.Autonomous;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "Example blue side autonomous", group = "EveryBot Quickstart")
public class ExampleBlueSideAutonomous extends OpMode {
    private Follower follower;
    private int pathState = 0;
    public ExamplePathing pathing = new ExamplePathing();
    public ElapsedTime shootTimer = new ElapsedTime();
    public int ballsShot = 0;

    private DcMotorEx leftFlywheelMotor;
    private DcMotorEx rightFlywheelMotor;
    public static final Double speed1Velocity = 0.0;
    public static final Double speed2Velocity = 0.0;
    public static final Double speed3Velocity = 0.0;
    public static final Double speed4Velocity = 0.0;

    private DcMotorEx intakeMotor;

    private Servo shooterGateServo;
    public static final Double gateOpenPosition = 0.0;
    public static final Double gateClosePosition = 0.0;

    @Override
    public void init() {
        leftFlywheelMotor = hardwareMap.get(DcMotorEx.class, "leftFlywheelMotor");
        rightFlywheelMotor = hardwareMap.get(DcMotorEx.class, "rightFlywheelMotor");
        leftFlywheelMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFlywheelMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        intakeMotor = hardwareMap.get(DcMotorEx.class, "intakeMotor");
        intakeMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        shooterGateServo = hardwareMap.get(Servo.class, "shooterGateServo");
        shooterGateServo.setPosition(gateClosePosition);

        pathing.initialize();
        pathing.buildPaths();
    }
    @Override
    public void loop() {
        follower.update();
        autonomousPathUpdate();
    }

    public void autonomousPathUpdate() {
        switch (pathState) {
            case 0:
                follower.followPath(pathing.scorePreload);
                pathState = 1;
                break;
            case 1:
                if(!follower.isBusy()) {
                    if (ballsShot == 3) {
                        ballsShot = 0;
                        leftFlywheelMotor.setVelocity(0.0);
                        rightFlywheelMotor.setVelocity(0.0);
                        intakeMotor.setPower(0.0);
                        follower.followPath(pathing.goToPickup);
                        pathState = 2;
                    } else {
                        if (accelerateFlywheel()) {
                            intakeMotor.setPower(1.0);
                            shooterGateServo.setPosition(gateOpenPosition);
                            try {
                                Thread.sleep(1000);
                                ballsShot += 1;
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                            shooterGateServo.setPosition(gateClosePosition);
                        }
                    }
                }
                break;
            case 2:
                if (!follower.isBusy()) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    intakeMotor.setPower(1.0);
                    follower.followPath(pathing.grabPickup);
                    pathState = 3;
                }
            case 3:
                if (!follower.isBusy()) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    intakeMotor.setPower(0.0);
                    follower.followPath(pathing.scorePickup);
                    pathState = 4;
                }
            case 4:
                if(!follower.isBusy()) {
                    if (ballsShot == 3) {
                        ballsShot = 0;
                        leftFlywheelMotor.setVelocity(0.0);
                        rightFlywheelMotor.setVelocity(0.0);
                        intakeMotor.setPower(0.0);
                        follower.followPath(pathing.leaveLaunchZone);
                        pathState = 5;
                    } else {
                        if (accelerateFlywheel()) {
                            intakeMotor.setPower(1.0);
                            shooterGateServo.setPosition(gateOpenPosition);
                            try {
                                Thread.sleep(1000);
                                ballsShot += 1;
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                            shooterGateServo.setPosition(gateClosePosition);
                        }
                    }
                }
        }
    }

    public Boolean accelerateFlywheel() {
        leftFlywheelMotor.setVelocity(speed2Velocity);
        rightFlywheelMotor.setVelocity(speed2Velocity);
        if (leftFlywheelMotor.getVelocity() == speed2Velocity) {
            return true;
        } else {
            return false;
        }
    }
}