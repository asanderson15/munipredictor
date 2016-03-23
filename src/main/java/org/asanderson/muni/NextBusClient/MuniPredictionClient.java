package org.asanderson.muni.NextBusClient;

import org.asanderson.muni.NextBusClient.routedetails.RouteDetails;
import org.asanderson.muni.NextBusClient.routepredictions.RoutePredictions;
import org.asanderson.muni.NextBusClient.routes.RouteList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;

/**
 * Created by asanderson on 3/15/16.
 */
public interface MuniPredictionClient {

    @GET("service/publicXMLFeed?command=routeList&a=sf-muni")
    Call<RouteList> getRoutes();

    @GET("service/publicXMLFeed?command=routeConfig&a=sf-muni")
    Call<RouteDetails> getRouteConfig(@Query("r") String r);

    @GET("service/publicXMLFeed?command=predictionsForMultiStops&a=sf-muni")
    Call<RoutePredictions> getRoutePredictions(@Query("stops") List<String> stops);
}
