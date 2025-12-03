package org.firstinspires.ftc.teamcode.JavaDecodeExample.Autonomous;

import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;

public class ExamplePathing {
    private Pose startPose = new Pose();
    private Pose scorePose = new Pose();
    private Pose intakeStartPose = new Pose();
    private Pose intakeEndPose = new Pose();
    private Pose endPose = new Pose();

    public Path scorePreload, goToPickup, grabPickup, scorePickup, leaveLaunchZone;

    public void initialize() {
        initialize(false);
    }
    public void initialize(Boolean mirror) {
        if (mirror) {
            startPose = new Pose().mirror();
            scorePose = new Pose().mirror();
            intakeStartPose = new Pose().mirror();
            intakeEndPose = new Pose().mirror();
            endPose = new Pose().mirror();
        } else {
            startPose = new Pose();
            scorePose = new Pose();
            intakeStartPose = new Pose();
            intakeEndPose = new Pose();
            endPose = new Pose();
        }
    }
    public void buildPaths() {
        scorePreload = new Path(new BezierLine(startPose, scorePose));
        scorePreload.setLinearHeadingInterpolation(startPose.getHeading(), scorePose.getHeading());

        goToPickup = new Path(new BezierLine(scorePose, intakeStartPose));
        goToPickup.setLinearHeadingInterpolation(scorePose.getHeading(), intakeStartPose.getHeading());

        grabPickup = new Path(new BezierLine(intakeStartPose, intakeEndPose));
        grabPickup.setLinearHeadingInterpolation(intakeStartPose.getHeading(), intakeEndPose.getHeading());

        scorePickup = new Path(new BezierLine(intakeEndPose, scorePose));
        scorePickup.setLinearHeadingInterpolation(intakeEndPose.getHeading(), scorePose.getHeading());

        leaveLaunchZone = new Path(new BezierLine(scorePose, endPose));
        leaveLaunchZone.setLinearHeadingInterpolation(scorePose.getHeading(), endPose.getHeading());
    }
}
