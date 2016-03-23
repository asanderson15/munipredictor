package org.asanderson.muni.NextBusClient.routes;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * Created by asanderson on 3/15/16.
 */
@Root(name="route", strict=false)
public class RouteWrapper {

    @Attribute(name="tag", required=true)
    public String id;

    @Attribute(name="title", required=true)
    public String title;

    public String toString() {
        return this.id + " - " + this.title;
    }

}
