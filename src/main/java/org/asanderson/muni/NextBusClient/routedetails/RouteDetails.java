package org.asanderson.muni.NextBusClient.routedetails;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by asanderson on 3/15/16.
 */

@Root(name="body", strict=false)
public class RouteDetails {

    @Attribute(name="tag")
    @Path("route")
    public String id;

    @Attribute(name="title")
    @Path("route")
    public String title;

    @Attribute(name="color")
    @Path("route")
    public String color;

    @Attribute(name="oppositeColor")
    @Path("route")
    public String oppositeColor;

    @ElementList(required=true, inline=true, entry="stop")
    @Path("route")
    public List<RouteStop> routeStops;

    @ElementList(required=true, inline=true, entry="direction")
    @Path("route")
    public List<RouteDirection> routeDirections;
}
