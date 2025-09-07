package hardwareConfig.frontDistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import hardwareConfig.baseConstants.DistanceSensorConstants;

public class FrontDistanceSensorConstants {
    static {
        DistanceSensorConstants.name="testDistance";
        DistanceSensorConstants.maxDistance=200;    //cm --to be set later from the specs
        DistanceSensorConstants.minDistance=2;  //cm --to be set later from the specs
        DistanceSensorConstants.distanceUnit = DistanceUnit.MM;
    }
}