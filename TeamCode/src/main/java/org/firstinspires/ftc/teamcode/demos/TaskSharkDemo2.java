package org.firstinspires.ftc.teamcode.demos;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.testing.FakeMotor;

import io.github.gearup12499.taskshark.FastScheduler;
import io.github.gearup12499.taskshark.Scheduler;
import io.github.gearup12499.taskshark.prefabs.OneShot;
import io.github.gearup12499.taskshark.prefabs.WaitUntil;
import io.github.gearup12499.taskshark_android.TaskSharkAndroid;

/**
 * TaskShark Demo 2: wait until
 */
@Disabled
@Autonomous
public class TaskSharkDemo2 extends LinearOpMode {
    @Override
    public void runOpMode() {
        TaskSharkAndroid.setup();

        Scheduler scheduler = new FastScheduler();

        // Let's try to use the encoder to move the slide to a position.
        FakeMotor slide = new FakeMotor("slide");

        // built in PID because lazy
        slide.setTargetPosition(0);
        slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slide.setPower(0.5);

        // Okay. How do we implement this?
        final int position = 1200;
        final int tolerance = 10;
        // note: there's a better way to do this that will come up later
        scheduler
                // First, let's set the position...
                .add(new OneShot(() -> {
                    // This is a lambda expression. You can put whatever code you want in here
                    // ( /* arguments */ ) -> { /* code */ }
                    slide.setTargetPosition(position);
                }))
                // And then we wait until we've reached the target...
                .then(new WaitUntil(() -> Math.abs(slide.getCurrentPosition() - position) < tolerance))
                // And then do something else idk
                .then(new OneShot(() -> Log.i("TaskSharkDemo2", "made it!")))
        ;

        waitForStart();
        ElapsedTime e = new ElapsedTime();
        double lastTime = e.seconds();
        while (opModeIsActive()) {
            scheduler.tick();

            // simulate run to position (badly)
            slide.simulate(e.seconds() - lastTime);
        }
    }
}
