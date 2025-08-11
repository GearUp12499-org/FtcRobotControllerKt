package org.firstinspires.ftc.teamcode.testing

import android.util.Log
import com.qualcomm.robotcore.hardware.HardwareDevice
import com.qualcomm.robotcore.hardware.Servo
import com.qualcomm.robotcore.hardware.ServoController

class FakeServo(val name: String) : Servo {
    fun log(s: String) {
        Log.i("Fakes", "FakeServo $name : $s")
    }

    override fun getController(): ServoController? = null

    override fun getPortNumber(): Int = 1

    private var direction: Servo.Direction = Servo.Direction.FORWARD
    private var position: Double = 0.0

    override fun setDirection(direction: Servo.Direction) {
        log("direction = $direction")
        this.direction = direction
    }

    override fun getDirection() = direction

    override fun setPosition(position: Double) {
        log("position = $position")
        this.position = position
    }

    override fun getPosition() = position

    override fun scaleRange(min: Double, max: Double) {
        TODO("Not yet implemented")
    }

    override fun getManufacturer() = HardwareDevice.Manufacturer.Other

    override fun getDeviceName() = "FakeServo $name"

    override fun getConnectionInfo() = "fake"

    override fun getVersion() = 1

    override fun resetDeviceConfigurationForOpMode() {
        log("reset")
        position = 0.0
        direction = Servo.Direction.FORWARD
    }

    override fun close() {
        log("closed")
    }
}