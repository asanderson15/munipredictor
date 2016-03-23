package org.asanderson.muni.NextBusClient.routepredictions;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by asanderson on 3/16/16.
 */
@Root(name="direction", strict=false)
public class DirectionPrediction {

    @Attribute(name="title")
    public String directionTitle;

    @ElementList(required=true, inline=true, entry="prediction")
    public List<PredictionDetail> predictionDetailList;
}
