package com.asv.champions.network;


import com.asv.champions.model.BaseResponse;
import com.asv.champions.model.ClubItem;
import com.asv.champions.model.DeleteRequest;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface AsvService {

   @Headers("Content-Type: application/json")
   @GET("/clubs")
   Observable<List<ClubItem>> getAllClubs();

   @Headers("Content-Type: application/json")
   @POST("/club")
   Observable<BaseResponse> createClub(@Body ClubItem body);

   @Headers("Content-Type: application/json")
   @HTTP(method = "DELETE", path = "/club", hasBody = true)
   Observable<BaseResponse> deleteClub(@Body DeleteRequest body);

   @Headers("Content-Type: application/json")
   @PUT("/club")
   Observable<BaseResponse> updateClub(@Body ClubItem body);


}
