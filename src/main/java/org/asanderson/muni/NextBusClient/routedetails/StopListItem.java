package org.asanderson.muni.NextBusClient.routedetails;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * Created by asanderson on 3/15/16.
 */
@Root(name="stop", strict=false)
public class StopListItem {

    @Attribute(name="tag")
    String id;

}
