package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

@TeleOp(name = "drive ILT bo!")
@Config
public class meet3bot extends LinearOpMode {

    public static double x0 = 0;
    public static double x1 = 0;

    public static double y0 = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        // Declare our motors
        // Make sure your ID's match your configuration
        DcMotorEx frontLeftMotor = hardwareMap.get(DcMotorEx.class, "LFMotor");
        DcMotorEx backLeftMotor = hardwareMap.get(DcMotorEx.class, "LBMotor");
        DcMotorEx frontRightMotor = hardwareMap.get(DcMotorEx.class, "RFMotor");
        DcMotorEx backRightMotor = hardwareMap.get(DcMotorEx.class, "RBMotor");


        // Reverse the right side motors. This may be wrong for your setup.
        // If your robot moves backwards when commanded to go forwards,
        // reverse the left side instead.
        // See the note about this earlier on this page.
        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            double y = -y0; // Remember, Y stick value is reversed
            double x = x0 * 1.1; // Counteract imperfect strafing
            double rx = x1;

            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio,
            // but only if at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;

            frontLeftMotor.setPower(frontLeftPower);
            backLeftMotor.setPower(backLeftPower);
            frontRightMotor.setPower(frontRightPower);
            backRightMotor.setPower(backRightPower);
            telemetry.addData("y:", gamepad1.left_stick_y);
            telemetry.addData("x:", gamepad1.left_stick_x);
            telemetry.addData("x2:", gamepad1.right_stick_x);
            telemetry.addData("getCurrent", frontLeftMotor.getCurrent(CurrentUnit.AMPS));
            telemetry.update();
        }
    }
}