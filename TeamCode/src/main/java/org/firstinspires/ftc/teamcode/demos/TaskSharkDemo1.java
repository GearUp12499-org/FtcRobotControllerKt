package org.firstinspires.ftc.teamcode.demos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.testing.FakeServo;

import io.github.gearup12499.taskshark.FastScheduler;
import io.github.gearup12499.taskshark.Scheduler;
import io.github.gearup12499.taskshark.api.LogOutlet;
import io.github.gearup12499.taskshark.prefabs.OneShot;
import io.github.gearup12499.taskshark.prefabs.Wait;
import io.github.gearup12499.taskshark_android.TaskSharkAndroid;

/**
 * TaskShark Demo 1: basic showcase
 */

//@TeleOp
@Autonomous
public class TaskSharkDemo1 extends LinearOpMode {
    @Override
    public void runOpMode() {
        // Configures logging for TaskShark.
        TaskSharkAndroid.setup();
        LogOutlet.getCurrentLogger().setLevel(LogOutlet.Level.Debug);

        // Creates a new scheduler. The scheduler is in charge of managing the tasks.
        Scheduler scheduler = new FastScheduler();
        // Pretend we use HardwareMap here
        Servo exampleServo = new FakeServo("example");

        // Add some tasks ("setup")
        scheduler
                // set the servo to 1
                .add(new OneShot(() -> exampleServo.setPosition(1.0)))
                // then wait 3 seconds
                .then(Wait.s(3))
                // then set the servo to 0
                .then(new OneShot(() -> exampleServo.setPosition(0.0)));
        // Add some tasks ("setup")
scheduler
        .add(new OneShot(() -> exampleServo.setPosition(1.0)))
        .then(Wait.s(3))
        .then(new OneShot(() -> exampleServo.setPosition(0.0)));
        /*
        Note that none of that servo stuff has actually _happened_ yet!
        This is more of a 'planning' phase where you describe what's going to happen
        when the op mode is running (or whenever the scheduler is being ticked)
         */

        waitForStart();
        while (opModeIsActive()) {
            // Repeatedly run the scheduler. This executes the instructions in order.
            scheduler.tick();
        }
    }
}
