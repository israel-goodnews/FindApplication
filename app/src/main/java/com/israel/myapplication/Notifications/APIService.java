package com.israel.myapplication.Notifications;

import com.israel.myapplication.Notifications.MyResponse;
import com.israel.myapplication.Notifications.Sender;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {

    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAADfKMcho:APA91bFYnDrAFV9LIIzA0ktRNx-2r5AYse96DEfF3UkCjytmdN9RVwqnbRhcVigC0OTZcqWM0CrenxaBPbogHE4FNthtnvwrSJKIkOQqJtadDA2ESG-iU3Ag3CtpDDGl4v_1N8i8DlZP"
    })
    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
