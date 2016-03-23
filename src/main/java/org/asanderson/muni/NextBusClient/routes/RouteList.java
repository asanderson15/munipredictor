package org.asanderson.muni.NextBusClient.routes;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by asanderson on 3/15/16.
 */
@Root(name = "body", strict=false)
public class RouteList {

    @ElementList(required=true, inline=true, entry="route")
    public List<RouteWrapper> routes;

}
