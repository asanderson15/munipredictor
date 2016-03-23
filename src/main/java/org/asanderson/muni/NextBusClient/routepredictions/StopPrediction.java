package org.asanderson.muni.NextBusClient.routepredictions;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by asanderson on 3/16/16.
 */
@Root(name="predictions", strict=false)
public class StopPrediction {

    @Attribute(name="routeTitle", required=true)
    public String routeTitle;

    @Attribute(name="routeTag", required=true)
    public String routeId;

    @Attribute(name="stopTitle", required=true)
    public String stopTitle;

    @Attribute(name="stopTag", required=true)
    public String stopId;

    @ElementList(required=false, inline=true, entry="direction")
    public List<DirectionPrediction> directions;

}
