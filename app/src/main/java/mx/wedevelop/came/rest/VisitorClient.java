package mx.wedevelop.came.rest;


import java.util.List;

import mx.wedevelop.came.model.Service;
import mx.wedevelop.came.model.Visitor;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by root on 5/12/16.
 */
public interface VisitorClient {

    @GET("/visitor")
    Call<List<Visitor>> getVisitors();

    @POST("/visitor")
    Call<Visitor> saveVisitor(@Body Visitor visitor);

    @GET("/service")
    Call<List<Service>> getServices();
}
