package com.example.ivani.hausaufgabe.network;

import com.example.ivani.hausaufgabe.model.EventDetailedModel;
import com.example.ivani.hausaufgabe.model.EventPreviewModel;
import com.example.ivani.hausaufgabe.model.UserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiManager {

    @FormUrlEncoded
    @POST("login")
    Call<UserModel> login(@Field("email") String email, @Field("password") String pw);

    @FormUrlEncoded
    @POST("signup")
    Call<UserModel> register(
            @Field("firstname") String firstName,
            @Field("lastname") String lastName,
            @Field("email") String email,
            @Field("password") String password,
            @Field("date") String date,
            @Field("job") String job,
            @Field("hobby") String hobby,
            @Field("sex") String sex,
            @Field("phone") String phone);


    @FormUrlEncoded
    @POST("updatemodel")
    Call<UserModel> updateUserModel(
            @Field("firstname") String firstName,
            @Field("lastname") String lastName,
            @Field("email") String email,
            @Field("password") String password,
            @Field("date") String date,
            @Field("job") String job,
            @Field("hobby") String hobby,
            @Field("sex") String sex,
            @Field("phone") String phone);

    @FormUrlEncoded
    @POST("createevent")
    Call<EventPreviewModel> createEvent(
            @Field("title") String title,
            @Field("city") String city,
            @Field("country") String country,
            @Field("description") String description,
            @Field("date") String date,
            @Field("maxparticipants") int numOfParticipants,
            @Field("userid") int usermodel);


    @GET("getevents")
    Call< List<EventPreviewModel> > getEvents(@Query("userid") int id);

    @GET("gethistoricevents")
    Call< List<EventPreviewModel> > getHistoricEvents(@Query("userid") int id);


    @GET("getdetailed")
    Call<EventDetailedModel> getEventDetails(@Query("eventid") int id);

    @FormUrlEncoded
    @POST("applytoevent")
    Call<EventPreviewModel> applyToAnEvent(@Field("eventid")int eventId, @Field("userid") int userId);
}
