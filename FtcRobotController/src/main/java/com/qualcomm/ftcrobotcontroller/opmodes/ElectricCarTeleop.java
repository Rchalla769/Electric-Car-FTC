package com.qualcomm.ftcrobotcontroller.opmodes;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

public class ElectricCarTeleop extends OpMode
{
    Servo driveMotor;
    Servo brake;
    DcMotor steering;
    double engageValue = 1.0;
    double disengageValue = 0.0;

    @Override
    public void start()
    {
        //DRIVE
        //1.0 - full forward
        //.55 - stop
        //0.0 - full back
        //test.setPosition(0.65);

        //Brake
        //engage - 1.0
        //disengage - 0.0

        driveMotor = hardwareMap.servo.get("Drive Motor");
        brake = hardwareMap.servo.get("Brake");
        releaseBrake();

    }

    @Override
    public void loop()
    {
        drive();

        if(gamepad1.a)
        {
            pushBrake();
        }
        else if(gamepad1.b)
        {
            releaseBrake();
        }


    }

    public void drive()
    {
        double throttle = -gamepad1.left_stick_y;

        throttle = scaleValue(throttle);

        driveMotor.setPosition(throttle);

    }s

    public double scaleValue(double x)
    {
        if(x > 0)
        {
            return 0.55+(x*0.25);
        }
        else if(x == 0)
        {
            return 0.55;
        }
        else
        {
            return 0.55-(x*0.25);
        }
    }

    public void pushBrake()
    {
        brake.setPosition(engageValue);
    }

    public void releaseBrake()
    {
        brake.setPosition(disengageValue);
    }

    @Override
    public void stop()
    {
        brake.setPosition(1.0);
        driveMotor.setPosition(0.55);
    }
}
