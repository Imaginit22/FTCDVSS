package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Basic: Linear OpMode", group="Linear OpMode")

public class Drive_And_ARM extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    private DcMotor armMotor;
    private DcMotor armMotor2;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        leftDrive  = hardwareMap.get(DcMotor.class, "motorLeft");
        rightDrive = hardwareMap.get(DcMotor.class, "motorRight");
        armMotor = hardwareMap.get(DcMotor.class, "arm");
        armMotor2 = hardwareMap.get(DcMotor.class, "arm2");

        // To drive forward, most robots need the motor on one side to be reversed, because the axles point in opposite directions.
        // Pushing the left stick forward MUST make robot go forward. So adjust these two lines based on your first test drive.
        // Note: The settings here assume direct drive on left and right wheels.  Gear Reduction or 90 Deg drives may require direction flips
        leftDrive.setDirection(DcMotor.Direction.REVERSE);
        rightDrive.setDirection(DcMotor.Direction.FORWARD);
        armMotor2.setDirection(DcMotor.Direction.REVERSE);
        double multiplier = 1;

        // Wait for the game to start (driver presses START)
        waitForStart();
        runtime.reset();
        

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // Setup a variable for each drive wheel to save power level for telemetry
            double leftPower;
            double rightPower;

            leftDrive.setPower(gamepad1.left_stick_y-0.004);
            rightDrive.setPower(gamepad1.right_stick_y);
            //set motor power to 50%, wait 0.175 seconds, set motor power to insignificant value just to keep it engaged
            if (gamepad1.right_bumper){
                armMotor.setPower(0.5);
                armMotor2.setPower(0.5);
                sleep(175);
                armMotor.setPower(0);
                armMotor2.setPower(0);
                
    
            }
            
            if (gamepad1.left_bumper){
                armMotor.setPower(-0.5);
                armMotor2.setPower(-0.5);
                sleep(175);
                armMotor.setPower(0);
                armMotor2.setPower(0);
            }
            /*boolean last_move = gamepad1.a;
            if (gamepad1.a && !last_move){
                if (multiplier == 1){
                    multiplier = 0.25;
                }else if (multiplier == 0.25){
                    multiplier = 1;
                }
            }
            */
            /*
            if (gamepad1.right_bumper) {
                if (gamepad1.left_bumper == false) {
                    armMotor.setTargetPosition(5);
                    armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    armMotor.setPower(0.5);
                    sleep(500);
                    armMotor.setTargetPosition(0);
                    armMotor.setPower(0.5);
                    gamepad1.right_bumper = false;
                    
                    //armMotor.setTargetPosition(45);
                    
                }    
            }
            
            if (gamepad1.left_bumper) {
                if (gamepad1.right_bumper == false) {                
                    armMotor.setTargetPosition(-5);
                    armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    armMotor.setPower(0.5);
                    sleep(500);
                    armMotor.setTargetPosition(0);
                    armMotor.setPower(0.5);
                    gamepad1.left_bumper = false;
                }
            }
            */

            // Show the elapsed game time and wheel power.
            //telemetry.addData("Status", "Run Time: " + runtime.toString());
            //telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
            telemetry.update();
        }
    }
}