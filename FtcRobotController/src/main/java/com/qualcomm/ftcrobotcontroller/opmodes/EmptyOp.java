package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.ftccommon.DbgLog;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * An empty op mode serving as a template for custom OpModes
 */
public class EmptyOp extends OpMode
{
    DcMotor test;
    DcMotorController motorController;
    int testCurrentEncoder = 0;
    DcMotorController.RunMode testRunMode;
    int numOpLoops = 1;
    /*
    * Constructor
    */
    public EmptyOp()
    {

    }

    /*
    * Code to run when the op mode is first enabled goes here
    * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#start()
    */
    @Override
    public void start()
    {
        motorController = hardwareMap.dcMotorController.get("Motor Controller 1");
        test = hardwareMap.dcMotor.get("Steering");

        test.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
    }


    /*
    * This method will be called repeatedly in a loop
    * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#loop()
    */
    @Override
    public void loop()
    {
        if (motorController.getMotorControllerDeviceMode() == DcMotorController.DeviceMode.WRITE_ONLY) {
            if(gamepad1.a)
            {
                test.setPower(0.5);
            }
            else
            {
                test.setPower(0.0);
            }
        }

        if (numOpLoops % 17 == 0) {
            // Note: If you are using the NxtDcMotorController, you need to switch into "read" mode
            // before doing a read, and into "write" mode before doing a write. This is because
            // the NxtDcMotorController is on the I2C interface, and can only do one at a time. If you are
            // using the USBDcMotorController, there is no need to switch, because USB can handle reads
            // and writes without changing modes. The NxtDcMotorControllers start up in "write" mode.
            // This method does nothing on USB devices, but is needed on Nxt devices.
            motorController.setMotorControllerDeviceMode(DcMotorController.DeviceMode.READ_ONLY);
        }

        if (motorController.getMotorControllerDeviceMode() == DcMotorController.DeviceMode.READ_ONLY) {

            // Update the reads after some loops, when the command has successfully propagated through.
            telemetry.addData("test power", test.getPower());

            testCurrentEncoder = test.getCurrentPosition();

            telemetry.addData("test curr enc", testCurrentEncoder);

            // Only needed on Nxt devices, but not on USB devices
            motorController.setMotorControllerDeviceMode(DcMotorController.DeviceMode.WRITE_ONLY);

            // Reset the loop
            numOpLoops = 0;
        }
        numOpLoops++;
    }

    /*
    * Code to run when the op mode is first disabled goes here
    * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#stop()
    */
    public void stop()
    {
        if (motorController.getMotorControllerDeviceMode() == DcMotorController.DeviceMode.WRITE_ONLY) {
            test.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        }
    }

}
