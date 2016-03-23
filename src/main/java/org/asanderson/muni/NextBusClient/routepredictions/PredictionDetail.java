package org.asanderson.muni.NextBusClient.routepredictions;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * Created by asanderson on 3/16/16.
 */
@Root(name="prediction", strict=false)
public class PredictionDetail {

    @Attribute(name="epochTime", required=true)
    public String epochTime;

    @Attribute(name="seconds", required=true)
    public String seconds;

    @Attribute(name="minutes", required=true)
    public String minutes;

    @Attribute(name="affectedByLayover", required=false)
    public String enroute;

    @Attribute(name="vehicle", required=true)
    public String vehicleId;

    @Attribute(name="tripTag", required=true)
    public String tripId;

    public String toString() {
        String star = (this.enroute == null || !this.enroute.equals("true")) ? "" : "*";
        return this.minutes + star;
    }
}
