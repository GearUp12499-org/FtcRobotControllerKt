package org.firstinspires.ftc.teamcode.demos;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.testing.FakeMotor;

import io.github.gearup12499.taskshark.FastScheduler;
import io.github.gearup12499.taskshark.Scheduler;
import io.github.gearup12499.taskshark.Task;
import io.github.gearup12499.taskshark.api.LogOutlet;
import io.github.gearup12499.taskshark.prefabs.OneShot;
import io.github.gearup12499.taskshark_android.TaskSharkAndroid;


@Autonomous
public class TaskSharkDemo3 extends LinearOpMode {
    @Override
    public void runOpMode() {
        TaskSharkAndroid.setup();
        LogOutlet.getCurrentLogger().setLevel(LogOutlet.Level.Debug);

        Scheduler scheduler = new FastScheduler();

        FakeMotor slide = new FakeMotor("slide");

        slide.setTargetPosition(0);
        slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slide.setPower(0.5);

        scheduler.add(new RunToPositionTask(slide, 1200))
                .then(new OneShot(() -> Log.i("Demo", "made it!")));

        waitForStart();
        ElapsedTime e = new ElapsedTime();
        double lastTime = e.seconds();
        while (opModeIsActive()) {
            scheduler.tick();

            double now = e.seconds();
            slide.simulate(now - lastTime);
        }
    }

    public static class RunToPositionTask extends Task<RunToPositionTask> {
        public static final int tolerance = 10;

        private final DcMotor motor;
        private final int position;

        public RunToPositionTask(DcMotor motor, int position) {
            this.motor = motor;
            this.position = position;
        }

        @Override
        public void onStart() {
            motor.setTargetPosition(position);
        }

        @Override
        public boolean onTick() {
            return Math.abs(motor.getCurrentPosition() - position) < tolerance;
        }
    }
}
