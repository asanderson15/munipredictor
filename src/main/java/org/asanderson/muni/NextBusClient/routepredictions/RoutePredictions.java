package org.asanderson.muni.NextBusClient.routepredictions;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by asanderson on 3/16/16.
 */

@Root(name="body", strict=false)
public class RoutePredictions {

    @ElementList(required=false, inline=true, entry="predictions")
    public List<StopPrediction> stopPredictions;

}
