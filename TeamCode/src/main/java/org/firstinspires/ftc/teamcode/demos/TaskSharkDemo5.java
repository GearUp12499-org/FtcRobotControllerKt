package org.firstinspires.ftc.teamcode.demos;

import android.util.Log;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.Set;

import io.github.gearup12499.taskshark.FastScheduler;
import io.github.gearup12499.taskshark.Scheduler;
import io.github.gearup12499.taskshark.Task;
import io.github.gearup12499.taskshark.api.BuiltInTags;
import io.github.gearup12499.taskshark.api.LogOutlet;
import io.github.gearup12499.taskshark.prefabs.OneShot;
import io.github.gearup12499.taskshark.prefabs.Wait;
import io.github.gearup12499.taskshark_android.TaskSharkAndroid;

/**
 * Or run something continuously...
 */

@Autonomous
public class TaskSharkDemo5 extends LinearOpMode {
    @Override
    public void runOpMode() {
        TaskSharkAndroid.setup();
        LogOutlet.getCurrentLogger().setLevel(LogOutlet.Level.Debug);

        Scheduler scheduler = new FastScheduler();

        scheduler.add(new BackgroundTask());
        scheduler.add(Wait.s(3))
                .then(new OneShot(() -> Log.i("Demo", "waited 3 seconds")))
                .then(Wait.s(2));

        waitForStart();
        while (opModeIsActive()) scheduler.tick();
    }

    public static class BackgroundTask extends Task<BackgroundTask> {
        private final ElapsedTime timer = new ElapsedTime();

        @NonNull
        @Override
        public Set<String> getTags() {
            return Set.of(BuiltInTags.DAEMON);
        }

        @Override
        public void onStart() {
            timer.reset(); // Start the timer when the task starts
        }

        @Override
        public boolean onTick() {
            if (timer.seconds() > 0.25) Log.i("Demo", "the background task is running!");
            // Returning 'false' makes the task never end.
            return false;
        }
    }
}
