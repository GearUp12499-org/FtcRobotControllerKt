package org.firstinspires.ftc.teamcode.demos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.testing.FakeMotor;

import io.github.gearup12499.taskshark.FastScheduler;
import io.github.gearup12499.taskshark.Scheduler;
import io.github.gearup12499.taskshark.Task;
import io.github.gearup12499.taskshark_android.TaskSharkAndroid;

@Disabled
@Autonomous
public class TaskSharkDemo3 extends LinearOpMode {
    @Override
    public void runOpMode() {
        TaskSharkAndroid.setup();

        Scheduler scheduler = new FastScheduler();

        FakeMotor slide = new FakeMotor("slide");

        slide.setTargetPosition(0);
        slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slide.setPower(0.5);

        scheduler.add(new RunToPositionTask(/* TODO */));

        waitForStart();
        ElapsedTime e = new ElapsedTime();
        double lastTime = e.seconds();
        while (opModeIsActive()) {
            scheduler.tick();

            slide.simulate(e.seconds() - lastTime);
        }
    }

    public static class RunToPositionTask extends Task<RunToPositionTask> {
        public RunToPositionTask(/* TODO */) {
            /* TODO */
        }

        @Override
        public void onStart() {
            /* TODO */
        }

        @Override
        public boolean onTick() {
            /* TODO */
            return false;
        }
    }
}
