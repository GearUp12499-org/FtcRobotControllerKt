package org.firstinspires.ftc.teamcode.demos;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.testing.FakeMotor;
import org.firstinspires.ftc.teamcode.testing.FakeServo;

import io.github.gearup12499.taskshark.FastScheduler;
import io.github.gearup12499.taskshark.Scheduler;
import io.github.gearup12499.taskshark.Task;
import io.github.gearup12499.taskshark.api.LogOutlet;
import io.github.gearup12499.taskshark.prefabs.OneShot;
import io.github.gearup12499.taskshark.prefabs.Wait;
import io.github.gearup12499.taskshark_android.TaskSharkAndroid;

/**
 * Let's try to move the motor and the servo at the same time.
 */

@Autonomous
public class TaskSharkDemo4 extends LinearOpMode {
    @Override
    public void runOpMode() {
        TaskSharkAndroid.setup();
        LogOutlet.getCurrentLogger().setLevel(LogOutlet.Level.Debug);

        Scheduler scheduler = new FastScheduler();

        FakeMotor slide = new FakeMotor("slide");
        FakeServo servo = new FakeServo("servo");

        scheduler
                .add(new RunToPositionTask(slide, 2400))
                .then(new OneShot(() -> Log.i("Demo", "Run to position finished")));
        scheduler
                .add(new OneShot(() -> servo.setPosition(1.0)))
                .then(Wait.ms(250))
                .then(new OneShot(() -> servo.setPosition(0.0)))
                .then(Wait.ms(250))
                .then(new OneShot(() -> Log.i("Demo", "Servo finished")));

        waitForStart();

        ElapsedTime e = new ElapsedTime();
        double lastTime = e.seconds();
        while (opModeIsActive()) {
            scheduler.tick();
            slide.simulate(e.seconds() - lastTime);
        }
    }

    public static class RunToPositionTask extends Task<RunToPositionTask> {
        public static final int TOLERANCE = 10;

        private final DcMotor motor;
        private final int target;

        public RunToPositionTask(DcMotor motor, int target) {
            this.motor = motor;
            this.target = target;
        }

        @Override
        public void onStart() {
            motor.setTargetPosition(target);
        }

        @Override
        public boolean onTick() {
            return Math.abs(motor.getCurrentPosition() - target) < TOLERANCE;
        }
    }
}
