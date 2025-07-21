package hardwareConfig.motor.constants;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.localization.Localizers;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
public class MotorConstants {
    public static String name;
    public static String motorConfigurationType;
    public static double ticksPerRev;
    public static double gearing;
    public static double achieveableMaxRPMFraction;
    public static DcMotor.RunMode mode;
    public static DcMotorSimple.Direction direction;
    public static double maxAngle;
    public static double minAngle;
    public static double startAngle;
    public static double targetAngle;
    public static double tolerableError;
    public static double feedforward;
}
