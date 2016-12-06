package mx.wedevelop.came.rest;


import java.util.List;

import mx.wedevelop.came.model.Service;
import mx.wedevelop.came.model.Visitor;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by root on 5/12/16.
 */
public interface VisitorClient {

    @GET("/visitor")
    Call<List<Visitor>> getVisitors();

    @GET("/service")
    Call<List<Service>> getServices();
}
