package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

public class ServoControl extends OpMode
{
    Servo test;
    double startTime = 0;
    boolean engaged = false;
    boolean disengaged = true;

    @Override
    public void start()
    {
        test = hardwareMap.servo.get("test");

    }

    @Override
    public void loop()
    {
        //DRIVE
        //1.0 - full forward
        //.55 - stop
        //0.0 - full back
        //test.setPosition(0.65);

        //Brake
        //engage - 1.0
        //disengage - 0.0
        test.setPosition(1.0);

    }

    public void engageBrake()
    {
            if (startTime == 0) {
                startTime = this.time;
            }
            if ((this.time - startTime) < 3) {
                test.setPosition(0.0);
            } else {
                startTime = 0;
                engaged = true;
                disengaged = false;
                test.setPosition(0.5);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        telemetry.addData("Engage: ", "true");
    }

    public void disengageBrake()
    {
            if (startTime == 0) {
                startTime = this.time;
            }
            if ((this.time- -startTime) < 3) {
                test.setPosition(1.0);
            } else {
                startTime = 0;
                disengaged = true;
                engaged = false;
                test.setPosition(0.5);
            }
        telemetry.addData("Engage: ", "false");
    }

    @Override
    public void stop()
    {
        test.setPosition(0.0);
    }
}
