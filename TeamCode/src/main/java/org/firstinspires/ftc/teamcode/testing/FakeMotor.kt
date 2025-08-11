package org.firstinspires.ftc.teamcode.testing

import android.util.Log
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.HardwareDevice
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType
import kotlin.math.abs
import kotlin.math.sign

class FakeMotor(val name: String) : DcMotor {
    fun log(s: String) {
        Log.i("Fakes", "FakeMotor $name : $s")
    }

    private var _motorType: MotorConfigurationType = MotorConfigurationType.getUnspecifiedMotorType()

    override fun getMotorType() = _motorType

    override fun setMotorType(motorType: MotorConfigurationType?) {
        log("motor type = $motorType")
        _motorType = motorType ?: MotorConfigurationType.getUnspecifiedMotorType()
    }

    override fun getController() = null

    override fun getPortNumber() = 1

    private var _zeroPowerBehavior = DcMotor.ZeroPowerBehavior.FLOAT

    override fun setZeroPowerBehavior(zeroPowerBehavior: DcMotor.ZeroPowerBehavior) {
        log("zpb = $zeroPowerBehavior")
        _zeroPowerBehavior = zeroPowerBehavior
    }

    override fun getZeroPowerBehavior() = _zeroPowerBehavior

    @Deprecated("don't do it")
    override fun setPowerFloat() {
        zeroPowerBehavior = DcMotor.ZeroPowerBehavior.FLOAT
        power = 0.0
    }

    override fun getPowerFloat() = zeroPowerBehavior == DcMotor.ZeroPowerBehavior.FLOAT && power == 0.0

    private var _targetPosition: Int = 0
    private var _currentPosition: Int = 0

    override fun setTargetPosition(position: Int) {
        var realPos = position
        if (direction == DcMotorSimple.Direction.REVERSE) realPos = -realPos
        log("target position = $position (real: $realPos)")
        _targetPosition = realPos
    }

    override fun getTargetPosition() = when (direction) {
        DcMotorSimple.Direction.REVERSE -> -_targetPosition
        DcMotorSimple.Direction.FORWARD -> _targetPosition
    }

    override fun isBusy() = (mode == DcMotor.RunMode.RUN_TO_POSITION && _currentPosition != _targetPosition)

    override fun getCurrentPosition() = when (direction) {
        DcMotorSimple.Direction.REVERSE -> -_currentPosition
        DcMotorSimple.Direction.FORWARD -> _currentPosition
    }

    private var _mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER

    override fun setMode(mode: DcMotor.RunMode) {
        log("mode = $mode")
        _mode = mode
    }

    override fun getMode() = _mode

    private var _direction = DcMotorSimple.Direction.FORWARD

    override fun setDirection(direction: DcMotorSimple.Direction) {
        log("direction = $direction")
        _direction = direction
    }

    override fun getDirection() = _direction

    private var _power = 0.0

    override fun setPower(power: Double) {
        log("power = $power")
        _power = when {
            power > 1.0 -> 1.0
            power < -1.0 -> -1.0
            else -> power
        }
    }

    override fun getPower() = _power

    override fun getManufacturer() = HardwareDevice.Manufacturer.Other

    override fun getDeviceName() = "FakeMotor $name"

    override fun getConnectionInfo() = "fake"

    override fun getVersion() = 1

    override fun resetDeviceConfigurationForOpMode() {
        log("reset")
        _power = 0.0
        _direction = DcMotorSimple.Direction.FORWARD
        _mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
        _targetPosition = 0
        _zeroPowerBehavior = DcMotor.ZeroPowerBehavior.FLOAT
    }

    override fun close() {
        log("closed")
    }

    companion object {
        const val MAX_SPEED = 900 // ticks per second, 1.0 power (naive linear approx.)
    }

    /**
     * @param [deltaTime] time in seconds
     */
    fun simulate(deltaTime: Double) {
        val adv = (deltaTime * _power * MAX_SPEED)
        if (mode == DcMotor.RunMode.RUN_TO_POSITION) {
            val delta = abs(_targetPosition - _currentPosition)
            if (delta < adv) {
                _currentPosition = _targetPosition
            } else {
                _currentPosition += (adv * (_targetPosition - _currentPosition).sign).toInt()
            }
        } else if (mode != DcMotor.RunMode.STOP_AND_RESET_ENCODER) {
            _currentPosition += adv.toInt()
        }
    }
}