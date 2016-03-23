package org.asanderson.muni.NextBusClient.routedetails;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asanderson on 3/15/16.
 */
@Root(name="direction", strict=false)
public class RouteDirection {

    @Attribute(name="tag")
    public String directionTag;

    @Attribute(name="title")
    public String title;

    @ElementList(required=true, inline=true, entry="stop")
    public List<StopListItem> stops;

    @Attribute(name="name")
    public String directionBound;

    public ArrayList<String> getStopList() {
        ArrayList<String> stopList = new ArrayList<>();
        for(StopListItem stop : stops) {
            stopList.add(stop.id);
        }
        return stopList;
    }
}
