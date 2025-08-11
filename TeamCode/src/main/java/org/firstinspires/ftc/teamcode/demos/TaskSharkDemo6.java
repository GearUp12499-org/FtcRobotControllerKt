package org.firstinspires.ftc.teamcode.demos;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import io.github.gearup12499.taskshark.FastScheduler;
import io.github.gearup12499.taskshark.Lock;
import io.github.gearup12499.taskshark.Scheduler;
import io.github.gearup12499.taskshark.api.LogOutlet;
import io.github.gearup12499.taskshark.prefabs.OneShot;
import io.github.gearup12499.taskshark.prefabs.Wait;
import io.github.gearup12499.taskshark_android.TaskSharkAndroid;

/**
 * Or run something continuously...
 */

@Autonomous
public class TaskSharkDemo6 extends LinearOpMode {
    public static final Lock DRIVE_MOTORS = new Lock.StrLock("drive_motors");

    @Override
    public void runOpMode() {
        TaskSharkAndroid.setup();
        LogOutlet.getCurrentLogger().setLevel(LogOutlet.Level.Debug);

        Scheduler scheduler = new FastScheduler();

        // Imagine this 3 second wait does something with the drive motors
        scheduler.add(
                Wait.s(3).require(DRIVE_MOTORS)
        ).then(new OneShot(() -> Log.i("Demo", "first finished")));
        // And here's a second thing, with a 200 ms delay
        scheduler.add(Wait.ms(200))
                .then(Wait.s(3).require(DRIVE_MOTORS))
                .then(new OneShot(() -> Log.i("Demo", "second finished")));

        waitForStart();
        while (opModeIsActive()) scheduler.tick();
    }
}
