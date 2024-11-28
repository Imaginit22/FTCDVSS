// Right left trigger for the horizontal extension into the submersible
// Mecanum wheel drive for both joysticks
// Button a is to flip between hanging state and wall grab state
// dpad_left and dpad_right for the rotation of the claw
// Button b is to peck down and grab a piece from the submersible

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;



@TeleOp
public class MecanumTeleOp extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {


        // Declare our motors
        // Make sure your ID's match your configuration
        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("backLeftMotor");
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("frontRightMotor");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("backRightMotor");



        // Reverse the right side motors. This may be wrong for your setup.
        // If your robot moves backwards when commanded to go forwards,
        // reverse the left side instead.
        // See the note about this earlier on this page.
        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);


        DcMotor horz = hardwareMap.dcMotor.get("horizontal extension1");
        Servo peck = hardwareMap.servo.get("pecking");

        Servo claw_horz = hardwareMap.servo.get("claw horizontal");
        Servo claw_vertical = hardwareMap.servo.get("claw vertical");
        Servo claw_horz_rot = hardwareMap.servo.get("rotation");
        double rot_inc = 0;

        DcMotor vert1 = hardwareMap.dcMotor.get("vertical extension1");
        DcMotor vert2 = hardwareMap.dcMotor.get("vertical extension2");
        DcMotor armMotor = hardwareMap.dcMotor.get("armMotor");


        horz.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        horz.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        vert1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        vert1.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        vert2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        vert2.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        
        
        /////////////////////////////////////////////////////// //
        // Actions to occur immediately after the Teleop beings //
        //////////////////////////////////////////////////////////
        
        // Send the horizontal arm out
        horz.setTargetPosition(100);
        horz.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        horz.setPower(1); // Set motor power
        
        // Immediately send the pecking out
        peck.setPosition(0.5);

        // Open the claw
        claw_vertical.setPosition(1);

        // Pull the horizontal arm back in (the peck is initited now)
        horz.setTargetPosition(0);
        horz.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        horz.setPower(1); // Set motor power


        boolean wallState = true;
        // Put the arm into wall grab state
        // Bring the vertical arm down
        vert1.setTargetPosition(0);
        vert1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        vert1.setPower(1); // Set motor power
        vert2.setTargetPosition(0);
        vert2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        vert2.setPower(1); // Set motor power

        // Bring the arm motor to the wall side
        armMotor.setTargetPosition(0);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armMotor.setPower(1); // Set motor power

        // Open the claw
        claw_vertical.setPosition(1);


        waitForStart();

        if (isStopRequested()) return;        
        // Inside your main loop:
        while (opModeIsActive()) {
            double y = gamepad1.left_stick_y; 
            double x = gamepad1.left_stick_x * 1.1;
            double rx = gamepad1.right_stick_x;

            double horz_out = (1+gamepad1.right_trigger)/2;
            double horz_in = (1+gamepad1.left_trigger)/2;
        
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;
        
            // Mecanum wheel drive with the two joysticks
            frontLeftMotor.setPower(frontLeftPower);
            backLeftMotor.setPower(backLeftPower);
            frontRightMotor.setPower(frontRightPower);
            backRightMotor.setPower(backRightPower);

            // Extension into the submersible with left and right trigger
            if (horz_out !=0) {
                horz.setPower(horz_out);
            } else if (horz_in !=0) {
                horz.setPower(-horz_in);
            }


            // Pecking with button press b
            if (gamepad1.b) {
                // Peck down by an inch
                peck.setPosition(0.2);
                // Close the claw
                claw_horz.setPosition(0);
                sleep(500);  // Optional, allow some time for pecking down
                // Peck up
                peck.setPosition(0.5);

                // Pull the horizontal arm back in (the peck is initited now)
                horz.setTargetPosition(0);
                horz.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                horz.setPower(1); // Set motor power

            }

            // Rotate the claw with dpad
            if (gamepad1.dpad_up) {
                claw_horz.setPosition(0);  // Ensure claw is open
                rot_inc = Math.min(1.0, Math.max(-1.0, rot_inc + 0.1));  // Clamp the value to -1 to 1
                claw_horz_rot.setPosition(rot_inc);
            }
            
            if (gamepad1.dpad_down) {
                claw_horz.setPosition(0);  // Ensure claw is open
                rot_inc = Math.min(1.0, Math.max(-1.0, rot_inc - 0.1));  // Clamp the value to -1 to 1
                claw_horz_rot.setPosition(rot_inc);
            }
            

        
            // Toggle wall or hanging state with button press a
            if (gamepad1.a) {
                // The bot is at the wall and going to prepare to hang the piece
                if (wallState) {

                    // Close the claw in preparation for hanging (assuming piece is captured)
                    claw_vertical.setPosition(0);

                    // Move pinion down to bar
                    vert1.setTargetPosition(500);
                    vert1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    vert1.setPower(1); // Set motor power

                    vert2.setTargetPosition(500);
                    vert2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    vert2.setPower(1); // Set motor power

                    // Move arm to high position
                    armMotor.setTargetPosition(1000); // Example low position value
                    armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    armMotor.setPower(1); // Set motor power
                    wallState = false; // Update the state to wall state true

                // The bot is done hanging the piece and is now going to wall grab
                } else {
                    // Move arm to low position
                    armMotor.setTargetPosition(0); // Example high position value
                    armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    armMotor.setPower(1); // Set motor power
                    wallState = true; // Update the state to false

                    // Open the claw
                    claw_vertical.setPosition(1);

                    // Move pinion down to the wall
                    vert1.setTargetPosition(0);
                    vert1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    vert1.setPower(1); // Set motor power

                    vert2.setTargetPosition(0);
                    vert2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    vert2.setPower(1); // Set motor power

                }
            }
        }
    }
}