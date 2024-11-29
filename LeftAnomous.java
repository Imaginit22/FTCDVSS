package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.CRServo;
import java.lang.Math; 

@Autonomous(name="LAuto", group="Autonomous")
public class LeftAutonomous extends LinearOpMode {

    // Hardware used
    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    private DcMotor armMotor = null;
    private Servo gripperServo = null;


    private ElapsedTime runtime = new ElapsedTime();


    //Map taken from Will's MecanumTeleOp
    // Declare our motors
    // Make sure your ID's match your configuration
    DcMotor frontLeft, backLeft, frontRight, backRight;
    DcMotor horz;
    //Servo claw_horz, claw_vertical, claw_horz_rot;
    CRServo peck;
    double rot_inc = 0;
    DcMotor vert1, vert2; 
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
    double ticksPerInch = 45.0; 
    @Override
    public void runOpMode() {
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backRight = hardwareMap.dcMotor.get("backRight");
        horz = hardwareMap.dcMotor.get("Horizontal");
        peck = hardwareMap.crservo.get("peck");
        //claw_horz = hardwareMap.servo.get("sample claw");
        //claw_vertical = hardwareMap.servo.get("specimen claw");
        //claw_horz_rot = hardwareMap.servo.get("rotation");
        vert1 = hardwareMap.dcMotor.get("Vertical 1");
        vert2 = hardwareMap.dcMotor.get("Vertical 2");
        armMotor = hardwareMap.dcMotor.get("armMotor");
        horz.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        vert1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        vert2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // Wait for start
        waitForStart();
        
        
        /*
        * Move to position x, y and facing d. 
        */

        //camera pseudocode for if i get that 
        /*private void toPosCamera(int x, int y, int d) {

        }*/
        if (opModeIsActive()) {
            runtime.reset();
            toPos(0,100, 0);
            //toPos(0,0,0);
        }
    }
    public void toPos(double xDest, double yDest, double dDest) {
        // Reset encoders
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backRight = hardwareMap.dcMotor.get("backRight");
        horz = hardwareMap.dcMotor.get("Horizontal");
        peck = hardwareMap.crservo.get("peck");
        //claw_horz = hardwareMap.servo.get("sample claw");
        //claw_vertical = hardwareMap.servo.get("specimen claw");
        //claw_horz_rot = hardwareMap.servo.get("rotation");
        vert1 = hardwareMap.dcMotor.get("Vertical 1");
        vert2 = hardwareMap.dcMotor.get("Vertical 2");
        armMotor = hardwareMap.dcMotor.get("armMotor");
        horz.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        vert1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        vert2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.REVERSE);
        // Set to use encoders
        

        double x = xDest - xPos;
        double y = yDest - yPos;
        telemetry.addData("XandY X: ", String.valueOf(x) + " Y: " + String.valueOf(y));
        //find distance
        double hyp = Math.hypot(x, y);
        telemetry.addData("HYPOT: ", String.valueOf(hyp));
        //x is opposite, y is adjacent
        double angle = Math.asin(x/hyp);
        double radian = Math.toRadians(angle);
        double PIover4 = Math.PI/4;
        double FRBL = Math.sin(angle - PIover4);
        double FLBR = Math.sin(angle + PIover4);
        telemetry.addData("PRELIMVECT: ", String.valueOf(FRBL) + "  " + String.valueOf(FLBR));

        
        
        //if true, normalize by FRBL, else normalize by FLBR
        double normalize = FLBR;
        if (FLBR > FRBL) {
            normalize = FRBL;
        }
        FLBR  = FLBR/normalize;
        FRBL = FRBL/normalize;
        if (FLBR > 1) {
            FRBL /= FLBR;
            FLBR /= FLBR;
        }
        if (FRBL > 1) {
            FLBR /= FRBL;
            FRBL /= FRBL;
        }
        //FUCK floating points
        if (Math.abs(FLBR) < .0001) {
            FLBR = 0;
        }
        if (Math.abs(FRBL) < .0001) {
            FRBL = 0;
        }
        telemetry.addData("VECTORS: ", String.valueOf(FLBR) + " " + String.valueOf(FRBL) + String.valueOf(FRBL == 1));
        
        while(opModeIsActive()) {
            
            telemetry.update();
            break;
        }
        frontLeft.setTargetPosition((int)(ticksPerInch * hyp/FLBR));
        frontRight.setTargetPosition((int)(ticksPerInch * hyp/FRBL));
        backLeft.setTargetPosition((int)(ticksPerInch * hyp/FRBL));
        backRight.setTargetPosition((int)(ticksPerInch * hyp/FLBR));
        
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        
        frontLeft.setPower(FLBR);
        frontRight.setPower(FRBL);
        backLeft.setPower(FRBL);
        backRight.setPower(FLBR);
        while (frontLeft.isBusy() || frontRight.isBusy() || backLeft.isBusy() || backRight.isBusy()) {
            telemetry.addData("Motors", 
            frontLeft.getCurrentPosition() + " " +
            frontRight.getCurrentPosition() + " " +
            backLeft.getCurrentPosition() + " " +
            backRight.getCurrentPosition());
            telemetry.update();
        }
        //Stop moving and be at new spot
        xPos = xDest;
        yPos = yDest;
    }
}
//https://cad.onshape.com/documents/8984e00f04b05696d8db3ecb/w/967eeac02a8de364065c7c08/e/193021613c0f3adca058692b