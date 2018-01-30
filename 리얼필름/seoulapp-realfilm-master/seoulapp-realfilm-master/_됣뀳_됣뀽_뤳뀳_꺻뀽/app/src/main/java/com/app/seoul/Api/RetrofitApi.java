package com.app.seoul.Api;

import com.app.seoul.Model.Event.e_val;
import com.app.seoul.Model.EventInfo.einfo_val;
import com.app.seoul.Model.Value;
import com.app.seoul.Model.info;
import com.app.seoul.Model.p_setting;
import com.app.seoul.Model.profile;
import com.app.seoul.Model.userprofile;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

/**
 * Created by ihoyong on 2017. 9. 15..
 */

public interface RetrofitApi {

    @FormUrlEncoded
    @POST("board.php")
    Call<Value> board(@Field("uid") String uid);

    @GET("search.php")
    Call<Value> search();

    @GET("notice.php")
    Call<Value> notice();

    @FormUrlEncoded
    @POST("login.php")
    Call<Value> login(@Field("id") String id, @Field("pw") String pw);

    @FormUrlEncoded
    @POST("register_check.php")
    Call<Value> registercheck(@Field("id") String id);

    @FormUrlEncoded
    @POST("register.php")
    Call<Value> register(@Field("id") String id,
                         @Field("pw") String pw,
                         @Field("name") String name,
                         @Field("age") String age,
                         @Field("sex") int sex);

    @FormUrlEncoded
    @POST("office_qna.php")
    Call<Value> office_qna(@Field("id") String id,
                           @Field("name") String name,
                           @Field("address") String address,
                           @Field("address_a") String address_a,
                           @Field("phone") String phone);

    @FormUrlEncoded
    @POST("qna.php")
    Call<Value> qna(@Field("id") String id,
                    @Field("content") String content,
                    @Field("email") String email);


    @Multipart
    @POST("upload.php")
    Call<Value> upload(@Part List<MultipartBody.Part> flim_image,
                       @PartMap Map<String, RequestBody> params);

    @FormUrlEncoded
    @POST("info.php")
    Call<info> info(@Field("bid") String bid,
                    @Field("uid") String uid);

    @FormUrlEncoded
    @POST("b_like.php")
    Call<Value> b_like(@Field("bid") String bid,
                       @Field("uid") String uid,
                       @Field("vali") int vali);

    @FormUrlEncoded
    @POST("bookmark.php")
    Call<Value> bm_btn(@Field("bid") String bid,
                       @Field("uid") String uid,
                       @Field("vali") int vali);

    @FormUrlEncoded
    @POST("fallow.php")
    Call<Value> fl_btn(@Field("bid") String bid,
                       @Field("uid") String uid,
                       @Field("vali") int vali);

    @FormUrlEncoded
    @POST("ufal.php")
    Call<Value> ufal(@Field("uid") String uid,
                     @Field("tid") String tid,
                     @Field("vali") int vali);

    @FormUrlEncoded
    @POST("comment_upload.php")
    Call<Value> comment_upload(@Field("bid") String bid,
                               @Field("uid") String uid,
                               @Field("content") String content);

    @FormUrlEncoded
    @POST("comment.php")
    Call<Value> comment(@Field("bid") String bid);

    @FormUrlEncoded
    @POST("profile.php")
    Call<profile> profile(@Field("uid") String uid);


    @FormUrlEncoded
    @POST("pro_a.php")
    Call<Value> pro_a(@Field("uid") String ab,
                      @Field("pp") int pp);

    @FormUrlEncoded
    @POST("profile_setting.php")
    Call<p_setting> pset(@Field("uid") String uid);

    @Multipart
    @POST("upload_pimage.php")
    Call<Value> pimage(@Part MultipartBody.Part pimage, @PartMap Map<String, RequestBody> params);

    @Multipart
    @POST("upload_pback.php")
    Call<Value> pback(@Part MultipartBody.Part pback,
                      @PartMap Map<String, RequestBody> params);

    @FormUrlEncoded
    @POST("chpw.php")
    Call<Value> chpw(@Field("uid") String uid,
                     @Field("now") String now,
                     @Field("newa") String newa);

    @FormUrlEncoded
    @POST("userprofile.php")
    Call<userprofile> uprofile(@Field("uid") String uid,
                               @Field("tid") String tid);

    @FormUrlEncoded
    @POST("up_a.php")
    Call<Value> up_a(@Field("uid") String uid);

    @FormUrlEncoded
    @POST("up_b.php")
    Call<Value> up_b(@Field("uid") String uid);

    @FormUrlEncoded
    @POST("up_c.php")
    Call<Value> up_c(@Field("uid") String ab);

    @FormUrlEncoded
    @POST("search_top10.php")
    Call<Value> top10(@Field("uid") String ab);

    @FormUrlEncoded
    @POST("search_top5.php")
    Call<Value> top5(@Field("uid") String ab);

    @FormUrlEncoded
    @POST("search_jong.php")
    Call<Value> top3(@Field("uid") String ab);


    @GET("locationBasedList?serviceKey=KEY&MobileOS=AND&MobileApp=real&_type=json&listYN=Y&arrange=O&contentTypeId=39&numOfRoews=5&pageNo=1&startPage=1&radius=1000&cat1=A05&cat2=A0502")
    Call<Value> response(@Query("mapX") String mapX, @Query("mapY") String mapY, @Query("cat3") String cat3);

    @GET("searchFestival?serviceKey=KEY&MobileOS=AND&MobileApp=real&arrange=O&listYN=Y&eventStartDate=20171023&areaCode=1&_type=json&pageNo=1&cat1=A02")
    Call<e_val> event(@Query("cat2") String cat2,
                      @Query("cat3") String cat3);

    @GET("detailIntro?ServiceKey=KEY&contentTypeId=15&MobileOS=AND&MobileApp=real&_type=json")
    Call<einfo_val> einfo(@Query("contentId") int contentId);

    @FormUrlEncoded
    @POST("search_a.php")
    Call<Value> search_a(@Field("search") String search,
                         @Field("ck") int ck, @Field("uid") String uid);

    @FormUrlEncoded
    @POST("deleteinfo.php")
    Call<Value> deleteinfo(@Field("bid") String bid,
                           @Field("uid") String uid);

    @FormUrlEncoded
    @POST("deletecomment.php")
    Call<Value> commentdel(@Field("cid") String cid);

    @FormUrlEncoded
    @POST("eventinfo.php")
    Call<Value> eventinfo(@Field("uid") String uid,
                          @Field("mapx") double mapx,
                          @Field("mapy") double mapy);

    @FormUrlEncoded
    @POST("revise.php")
    Call<Value> revise(@Field("title") String title,
                       @Field("content") String content,
                       @Field("bid") String bid);

    @FormUrlEncoded
    @POST("chstatus.php")
    Call<Value> chstatus(@Field("uid") String uid,
                         @Field("status") String status);

}
