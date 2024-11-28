package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

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
         * 3. Robot is positioned as if it were its center. 
         * If it were centered on the origin, it would be at 0, 0
         * 4. X axis runs from -72 to 72 as you go from left to right. 
         * 5. Y axis runs from -72 to 72 as you go further away from red wall.
         * (Both of the above as per https://ftc-docs.firstinspires.org/en/latest/game_specific_resources/field_coordinate_system/field-coordinate-system.html)
         * 6. Facing is with 0 as pointing straight down Y axis
         * 
         */
        double xPos = 0;
        double yPos = 0;
        //rotation as described above
        double facing = 0;
        private void toPos(int x, int y)
        if (opModeIsActive()) {
            runtime.reset();

        }
    }
}
