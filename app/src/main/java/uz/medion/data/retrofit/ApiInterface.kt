package uz.medion.data.retrofit

import io.reactivex.Observable
import retrofit2.http.*
import uz.medion.data.model.*

interface ApiInterface {

    @GET("/api/v1/registration")
    fun isRegistrationFlowAvailable(
        @Header("username") userName: String,
    ): Observable<IsRegistrationFlowAvailable>

    @POST("/api/v1/registration/request") // createRegistrationRequest
    fun requestMail(
        @Query("username") userName: String, // email
    ): Observable<ResponseOfRequestEmail>

    @Headers("Content-Type: application/json")
    @POST("/api/v1/registration/{requestId}")
    fun verifyAndRegisterUser(
        @Path("requestId") requestId: String,
        @Query("code") code: String,
        @Body registrationRequest: RegistrationRequest,
    ): Observable<RegistrationResponse>

    @Headers("Content-Type: application/json")
    @POST("/api/v1/auth/login")
    fun login(
        @Body login: Login,
    ): Observable<UserLogin>

    //HomeFragment:: About clinic
    @Headers("Content-Type: application/json")
    @GET("/api/v1/about/info")
    fun aboutClinic(
        @Header("Authorization") token: String,
    ): Observable<AboutClinicResponse>

    //HomeFragment:: Specialities
    @Headers("Content-Type: application/json")
    @GET("/api/v1/speciality/list")
    fun speciality(
        @Header("Authorization") token: String,
    ): Observable<List<SpecialityItemResponse>>

    //OurDoctorsFragment:: Getting Doctors by Speciality
    @Headers("Content-Type: application/json")
    @GET
    fun doctorsBySpeciality(
        @Url url: String,
        @Header("Authorization") token: String,
    ): Observable<List<DoctorResponse>>

    //AboutDoctor:: Booking
    @GET("/api/v1/doctor/get/{id}")
    fun doctorById(
        @Path("id") id: Int,
        @Header("Authorization") token: String
    ): Observable<DoctorResponse>

    @Headers("Content-Type: application/json")
    @GET("/api/v1/booking/date")
    fun monthlyDate(
        @Query("doctorId") doctorId: Int,
        @Header("Authorization") token: String,
    ): Observable<List<MonthlyDateResponse>>

    @Headers("Content-Type: application/json")
    @GET("/api/v1/booking/time")
    fun monthlyTime(
        @Query("date") date: String,
        @Query("doctorId") doctorId: Int,
        @Header("Authorization") token: String,
    ): Observable<DataResponse>

    //Address and contacts
    @Headers("Content-Type: application/json")
    @GET("/api/v1/branch")
    fun branch(
        @Header("Authorization") token: String,
    ): Observable<List<BranchResponse>>

    //About Doctor // comments
    @Headers("Content-Type: application/json")
    @GET("/api/v1/comment/{doctorId}")
    fun comments(
        @Path("doctorId") doctorId: Int,
        @Header("Authorization") token: String,
    ): Observable<List<CommentResponse>>

    @Headers("Content-Type: application/json")
    @POST("/api/v1/comment")
    fun sendComment(
        @Body comment: SendComment,
        @Header("Authorization") token: String,
    ): Observable<List<CommentResponse>>

    //MyDoctor //favourites
    @Headers("Content-Type: application/json")
    @GET("/api/v1/user/mydoctors")
    fun myDoctors(
        @Query("name") userName: String,
        @Header("Authorization") token: String,
    ): Observable<List<DoctorResponse>>

    @Headers("Content-Type: application/json")
    @GET("/api/v1/user/getfavorite")
    fun myDoctorsFavourite(
        @Query("name") userName: String,
        @Header("Authorization") token: String,
    ): Observable<List<DoctorResponse>>

    @Headers("Content-Type: application/json")
    @POST("/api/v1/user/favorite/{doctorId}")
    fun setDoctorFavourite(
        @Path("doctorId") doctorId: Int,
        @Header("Authorization") token: String
    ): Observable<Boolean>

}