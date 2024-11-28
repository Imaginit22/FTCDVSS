package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import java.lang.Math; 

@Autonomous(name="LAuto", group="Autonomous")
public class LeftAnomous extends LinearOpMode {

    // Hardware used
    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    private DcMotor armMotor = null;
    private Servo gripperServo = null;


    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {
        
        //Map taken from Will's MecanumTeleOp
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
        


        // Wait for start
        waitForStart();

        //Where we think we are
        /*
         * All of the following assumes:
         * 1. Looking at the field from the red wall.
         * 2. Origin is the center of the field.
         * Robot is positioned as if it were its center. 
         * If it were centered on the origin, it would be at 0, 0
         * X axis runs from -72 to 72 as you go from left to right
         * Y axis runs from -72 to 72 as you go further away from red wall.
         * (Both of the above as per https://ftc-docs.firstinspires.org/en/latest/game_specific_resources/field_coordinate_system/field-coordinate-system.html)
         * Facing is with 0 as pointing straight down Y axis, 360 degrees points you straight down again
         * 
         */
        double xPos = 0;
        double yPos = 0;
        //rotation as described above
        double facing = 0;

        // Set target position. NEEDS TO BE CALIBRATED
        int ticksPerInch = 1000; 
        
        private void toPos(double xDest, double yDest, double dDest) {
            // Reset encoders
            frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            // Set to use encoders
            frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            double x = xDest - xPos;
            double y = yDest - yPos;

            //find distance
            double hyp = Math.hypot(x, y);
            
            //x is opposite, y is adjacent
            double angle = Math.asin(x/hyp);
            double radian = Math.toRadians(angle);
            double PIover4 = Math.PI/4
            double FRBL = Math.sin(angle - PIover4)
            double FLBR = Math.sin(angle + PIover4)


            frontLeftMotor.setTargetPosition(ticksPerInch * FLBR);
            frontRightMotor.setTargetPosition(ticksPerInch * FRBL);
            backLeftMotor.setTargetPosition(ticksPerInch * FRBL);
            backRightMotor.setTargetPosition(ticksPerInch * FLBR);
            
            //if true, normalize by FRBL, else normalize by FLBR
            double normalize = FLBR
            if (FLBR > FRBL) {
                normalize = FRBL
            }
            frontLeftMotor.setPower(FLBR / normalize);
            frontRightMotor.setPower(FRBL / normalize);
            backLeftMotor.setPower(FRBL / normalize);
            backRightMotor.setPower(FLBR / normalize);
    
            while (frontLeftMotor.isBusy() || frontRightMotor.isBusy(), backLeftMotor.isBusy(), backRightMotor.isBusy()) {
                telemetry.addData("Motors", 
                frontLeft.getCurrentPosition() + " " +
                frontRight.getCurrentPosition() + " " +
                backLeft.getCurrentPosition() + " " +
                backRight.getCurrentPosition());
            }
            //Stop moving
            xPos = xDest;
            yPos = yDest;
            leftMotor.setPower(0);
            rightMotor.setPower(0);
        }
        /*
        * Move to position x, y and facing d. 
        */

        //camera pseudocode for if i get that 
        private void toPosCamera(int x, int y, int d) {

        }
        if (opModeIsActive()) {
            runtime.reset();

        }
    }
}
