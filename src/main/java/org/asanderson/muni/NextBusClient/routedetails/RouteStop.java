package org.asanderson.muni.NextBusClient.routedetails;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * Created by asanderson on 3/15/16.
 */

@Root(name="stop", strict=false)
public class RouteStop {

    @Attribute(name="tag")
    public String id;

    @Attribute(name="title")
    public String title;

    @Attribute(name="lat")
    public String lat;

    @Attribute(name="lon")
    public String lon;
}
