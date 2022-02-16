package com.apps.etbo5ly_client.common.services;


import com.apps.etbo5ly_client.model.BuffetsDataModel;
import com.apps.etbo5ly_client.model.CartOrderModel;
import com.apps.etbo5ly_client.model.CategoryDataModel;
import com.apps.etbo5ly_client.model.CountryDataModel;
import com.apps.etbo5ly_client.model.CouponDataModel;
import com.apps.etbo5ly_client.model.DishesDataModel;
import com.apps.etbo5ly_client.model.FilterModel;
import com.apps.etbo5ly_client.model.KitchenDataModel;
import com.apps.etbo5ly_client.model.NotificationDataModel;
import com.apps.etbo5ly_client.model.OrderDataModel;
import com.apps.etbo5ly_client.model.PlaceGeocodeData;
import com.apps.etbo5ly_client.model.PlaceMapDetailsData;
import com.apps.etbo5ly_client.model.SendOrderModel;
import com.apps.etbo5ly_client.model.SingleKitchenDataModel;
import com.apps.etbo5ly_client.model.SingleOrderDataModel;
import com.apps.etbo5ly_client.model.StatusResponse;
import com.apps.etbo5ly_client.model.UserModel;

import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface Service {

    @GET("place/findplacefromtext/json")
    Call<PlaceMapDetailsData> searchOnMap(@Query(value = "inputtype") String inputtype,
                                          @Query(value = "input") String input,
                                          @Query(value = "fields") String fields,
                                          @Query(value = "language") String language,
                                          @Query(value = "key") String key
    );

    @GET("geocode/json")
    Call<PlaceGeocodeData> getGeoData(@Query(value = "latlng") String latlng,
                                      @Query(value = "language") String language,
                                      @Query(value = "key") String key);

    @FormUrlEncoded
    @POST("api/Catering/login")
    Single<Response<UserModel>> login(@Field("phone_code") String phone_code,
                                      @Field("phone") String phone,
                                      @Field("yes_i_read_it") String yes_i_read_it
    );


    @Multipart
    @POST("api/Catering/signup")
    Observable<Response<UserModel>> signUp(@Part("name") RequestBody name,
                                           @Part("phone_code") RequestBody phone_code,
                                           @Part("phone") RequestBody phone,
                                           @Part("email") RequestBody email,
                                           @Part("longitude") RequestBody longitude,
                                           @Part("latitude") RequestBody latitude,
                                           @Part("type") RequestBody type,
                                           @Part("software_type") RequestBody software_type,
                                           @Part MultipartBody.Part logo


    );

    @Multipart
    @POST("api/Catering/userUpdateProfile")
    Observable<Response<UserModel>> updateProfile(@Part("user_id") RequestBody user_id,
                                                  @Part("name") RequestBody name,
                                                  @Part("email") RequestBody email,
                                                  @Part MultipartBody.Part logo


    );

    @GET("api/Catering/indexSlider")
    Single<Response<CategoryDataModel>> getMainSlider();

    @GET("api/Catering/indexCategory")
    Single<Response<CategoryDataModel>> getCategories();

    @GET("api/Catering/most_famous")
    Single<Response<KitchenDataModel>> getPopularKitchen(@Query("user_id") String user_id,
                                                         @Query("latitude") String latitude,
                                                         @Query("longitude") String longitude,
                                                         @Query("option_id") String option_id
    );

    @GET("api/Catering/Caterer_is_special")
    Single<Response<KitchenDataModel>> getFeaturedKitchen(@Query("user_id") String user_id,
                                                          @Query("latitude") String latitude,
                                                          @Query("longitude") String longitude,
                                                          @Query("option_id") String option_id
    );


    @GET("api/Catering/Caterer_free_delivery")
    Single<Response<KitchenDataModel>> getFreeDeliveryKitchen(@Query("user_id") String user_id,
                                                              @Query("latitude") String latitude,
                                                              @Query("longitude") String longitude,
                                                              @Query("option_id") String option_id
    );

    @POST("api/Catering/filtter")
    Single<Response<KitchenDataModel>> filterKitchen(@Body FilterModel filterModel);

    @GET("api/Service/Offers")
    Single<Response<KitchenDataModel>> getOffer(@Query("option_id") String option_id);

    @GET("api/Catering/CatererDetails")
    Single<Response<SingleKitchenDataModel>> getKitchenDetails(@Query(value = "Caterer_id") String caterer_id,
                                                               @Query("user_id") String user_id
    );

    @GET("api/Catering/CatererBuffets")
    Single<Response<BuffetsDataModel>> getBuffets(@Query(value = "Caterer_id") String caterer_id);


    @GET("api/Catering/CatererFeasts")
    Single<Response<BuffetsDataModel>> getFeasts(@Query(value = "Caterer_id") String caterer_id);


    @GET("api/Catering/indexCategoryDishes")
    Single<Response<DishesDataModel>> getCategoryDishes(@Query(value = "category_dishes_id") String category_dishes_id,
                                                        @Query(value = "Caterer_id") String Caterer_id

    );

    @GET("api/Catering/myOrder")
    Single<Response<OrderDataModel>> getCatererOrder(@Query(value = "user_id") String user_id,
                                                     @Query(value = "is_end") String is_end

    );

    @GET("api/Catering/SingelOrder")
    Single<Response<SingleOrderDataModel>> getSingleOrder(@Query(value = "order_id") String order_id

    );


    @GET("api/Catering/indexFavorite")
    Single<Response<KitchenDataModel>> getFavorite(@Query("option_id") String option_id,
                                                   @Query(value = "user_id") String user_id);

    @FormUrlEncoded
    @POST("api/Catering/addFavorite")
    Single<Response<StatusResponse>> addRemoveFavorite(@Field(value = "caterer_id") String caterer_id,
                                                       @Field("user_id") String user_id);


    @GET("api/Catering/notifications")
    Single<Response<NotificationDataModel>> getNotifications(@Query("option_id") String option_id,
                                                             @Query(value = "user_id") String user_id
    );


    @FormUrlEncoded
    @POST("api/Catering/CountactUs")
    Single<Response<StatusResponse>> contactUs(@Field("name") String name,
                                               @Field("email") String email,
                                               @Field("subject") String subject,
                                               @Field("message") String message


    );


    @GET("api/Catering/governorates")
    Single<Response<CountryDataModel>> getCountry();

    @GET("api/Catering/cities")
    Single<Response<CountryDataModel>> getCityByCountryId(@Query("governorates_id") String country_id);


    @GET("api/Service/zone_cover")
    Single<Response<SingleKitchenDataModel>> getCoveredZone(@Query("Caterer_id") String Caterer_id);

    @FormUrlEncoded
    @POST("api/Catering/copon")
    Single<Response<CouponDataModel>> checkCoupon(@Field("copon") String copon_code,
                                                  @Field("user_id") String user_id

    );

    @POST("api/Catering/storeOrder")
    Single<Response<SingleOrderDataModel>> sendOrder(@Body CartOrderModel model);


    @FormUrlEncoded
    @POST("api/Catering/logout")
    Single<Response<StatusResponse>> logout(@Field("user_id") String user_id,
                                            @Field("token") String phone_token


    );

    @FormUrlEncoded
    @POST("api/Catering/firebaseTokens")
    Single<Response<StatusResponse>> updateFireBaseToken(@Field("token") String token,
                                                         @Field("user_id") String user_id,
                                                         @Field("type") String type


    );

    @FormUrlEncoded
    @POST("api/Catering/status_Order")
    Single<Response<StatusResponse>> reSendOrder(@Field("order_id") String order_id,
                                                 @Field("status_order") String status_order


    );

    @FormUrlEncoded
    @POST("api/Catering/storeRate")
    Single<Response<StatusResponse>> rateOrder(@Field("caterer_id") String caterer_id,
                                               @Field("order_id") String order_id,
                                               @Field("user_id") String user_id,
                                               @Field("value") String value,
                                               @Field("comment") String comment


    );


}