package org.firstinspires.ftc.teamcode.demos;

import android.util.Log;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.sun.tools.javac.util.List;

import org.firstinspires.ftc.teamcode.testing.FakeMotor;
import org.firstinspires.ftc.teamcode.testing.FakeServo;

import java.util.Set;

import io.github.gearup12499.taskshark.FastScheduler;
import io.github.gearup12499.taskshark.Lock;
import io.github.gearup12499.taskshark.Scheduler;
import io.github.gearup12499.taskshark.Task;
import io.github.gearup12499.taskshark.api.BuiltInTags;
import io.github.gearup12499.taskshark.prefabs.OneShot;
import io.github.gearup12499.taskshark.prefabs.Wait;
import io.github.gearup12499.taskshark_android.TaskSharkAndroid;

/**
 * Or run something continuously...
 */
@Disabled
@Autonomous
public class TaskSharkDemo6 extends LinearOpMode {
    public static final Lock DRIVE_MOTORS = new Lock.StrLock("drive_motors");

    @Override
    public void runOpMode() {
        TaskSharkAndroid.setup();

        Scheduler scheduler = new FastScheduler();

        // Imagine this 3 second wait does something with the drive motors
        scheduler.add(
                Wait.s(3).require(DRIVE_MOTORS)
        );
        // And here's a second thing, with a 200 ms delay
        scheduler.add(Wait.ms(200))
                        .then(Wait.s(3).require(DRIVE_MOTORS));

        waitForStart();
        while (opModeIsActive()) scheduler.tick();
    }
}
