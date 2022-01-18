package uz.medion.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import uz.medion.data.constants.Constants
import uz.medion.data.model.*
import uz.medion.data.model.remote.Resource
import uz.medion.data.model.remote.Status
import uz.medion.data.retrofit.ApiClient

class Repository {

    private val compositeDisposable = CompositeDisposable()
    private val apiClient = ApiClient.getApiClient()

    fun sendComment(
        commentItem: AboutDoctorCommentItem,
        response: MutableLiveData<Resource<AboutDoctorCommentItem>>,
    ) {
        compositeDisposable.add(
            apiClient.sendComment(1000, "HERE SHOULD BE SOMETHING", "TOKEN")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<SUCCESSTEST>() {
                    override fun onNext(t: SUCCESSTEST) {
//                        Log.i("---------", "onNext: ${t.statusCode}")
//                        if (t.statusCode == 200) {
//                            response.value = Resource(Status.SUCCESS, t, null, null)
//                        } else {
//                            response.value = Resource(Status.ERROR, t, t.message, null)
//                        }
                    }

                    override fun onError(e: Throwable) {
//                        Log.i("---------", "onNext: ${e.message}")
//
//                        response.value = Resource(Status.ERROR, null, e.message, e)
                    }

                    override fun onComplete() {}
                })
        )
    }

    fun responseOfRequestEmail(
        email: String,
        response: MutableLiveData<Resource<ResponseOfRequestEmail>>,
    ) {
        compositeDisposable.add(
            apiClient.requestMail(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<ResponseOfRequestEmail>() {
                    override fun onNext(t: ResponseOfRequestEmail) {
                        response.value = Resource(Status.SUCCESS, t, null, null)
                    }

                    override fun onError(e: Throwable) {
                        if (e is HttpException) {
                            val errorBody = e.response()?.errorBody()
                            val error =
                                Gson().fromJson<RegistrationErrorResponse>(errorBody!!.charStream(),
                                    object : TypeToken<RegistrationErrorResponse>() {}.type)
                            response.value = Resource(Status.ERROR, null, error.message, e)
                            Log.d("----------", "onError: $error")
                        }
                    }

                    override fun onComplete() {}

                })
        )
        response.value = Resource(Status.LOADING, null, null, null)
    }

    fun getIsRegistrationFlowAvailable(
        userName: String,
        response: MutableLiveData<Resource<IsRegistrationFlowAvailable>>,
    ) {
        compositeDisposable.add(
            apiClient.isRegistrationFlowAvailable(userName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<IsRegistrationFlowAvailable>() {
                    override fun onNext(t: IsRegistrationFlowAvailable) {
                        response.value = Resource(Status.SUCCESS, t, null, null)
                    }

                    override fun onError(e: Throwable) {
                        if (e.message?.contains("401", true) == true) {
                            Constants.setUnAuthorized(true)
                        }
                        response.value = Resource(Status.ERROR, null, e.message, e)
                    }

                    override fun onComplete() {}
                })
        )
        response.value = Resource(Status.LOADING, null, null, null)
    }

    fun verifyAndRegisterUser(
        requestId: String,
        code: String,
        registrationRequest: RegistrationRequest,
        response: MutableLiveData<Resource<RegistrationResponse>>,
    ) {
        compositeDisposable.add(
            apiClient.verifyAndRegisterUser(
                requestId,
                code, registrationRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<RegistrationResponse>() {
                    override fun onNext(t: RegistrationResponse) {
                        response.value = Resource(Status.SUCCESS, t, null, null)
                    }

                    override fun onError(e: Throwable) {
                        if (e.message?.contains("401", true) == true) {
                            Constants.setUnAuthorized(true)
                        }
                        response.value = Resource(Status.ERROR, null, e.message, e)
                    }

                    override fun onComplete() {}
                })
        )
        response.value = Resource(Status.LOADING, null, null, null)
    }

    fun login(
        login: Login,
        response: MutableLiveData<Resource<UserLogin>>,
    ) {
        compositeDisposable.add(
            apiClient.login(login)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<UserLogin>() {
                    override fun onNext(t: UserLogin) {
                        response.value = Resource(Status.SUCCESS, t, null, null)
                    }

                    override fun onError(e: Throwable) {
                        if (e.message?.contains("401", true) == true) {
                            Constants.setUnAuthorized(true)
                        }
                        response.value = Resource(Status.ERROR, null, e.message, e)
                    }

                    override fun onComplete() {}
                })
        )
        response.value = Resource(Status.LOADING, null, null, null)
    }

    fun aboutClinic(response: MutableLiveData<Resource<AboutClinicResponse>>) {
        compositeDisposable.add(
            apiClient.aboutClinic("Bearer ${Constants.token}")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<AboutClinicResponse>() {
                    override fun onNext(t: AboutClinicResponse) {
                        response.value = Resource(Status.SUCCESS, t, null, null)
                    }

                    override fun onError(e: Throwable) {
                        if (e.message?.contains("401", true) == true) {
                            Constants.setUnAuthorized(true)
                        }
                        response.value = Resource(Status.ERROR, null, e.message, e)
                    }

                    override fun onComplete() {}

                })
        )
        response.value = Resource(Status.LOADING, null, null, null)
    }

    fun speciality(response: MutableLiveData<Resource<List<SpecialityItemResponse>>>) {
        compositeDisposable.add(
            apiClient.speciality("Bearer ${Constants.token}")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<List<SpecialityItemResponse>>() {
                    override fun onNext(t: List<SpecialityItemResponse>) {
                        response.value = Resource(Status.SUCCESS, t, null, null)
                    }

                    override fun onError(e: Throwable) {
                        if (e.message?.contains("401", true) == true) {
                            Constants.setUnAuthorized(true)
                        }
                        response.value = Resource(Status.ERROR, null, e.message, e)
                    }

                    override fun onComplete() {}

                })
        )
        response.value = Resource(Status.LOADING, null, null, null)
    }

    fun doctorBySpeciality(url: String, response: MutableLiveData<Resource<List<DoctorResponse>>>) {
        compositeDisposable.add(
            apiClient.doctorsBySpeciality(url, "Bearer ${Constants.token}")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<List<DoctorResponse>>() {
                    override fun onNext(t: List<DoctorResponse>) {
                        response.value = Resource(Status.SUCCESS, t, null, null)
                    }

                    override fun onError(e: Throwable) {
                        if (e.message?.contains("401", true) == true) {
                            Constants.setUnAuthorized(true)
                        }
                        response.value = Resource(Status.ERROR, null, e.message, e)
                    }

                    override fun onComplete() {}
                })
        )
        response.value = Resource(Status.LOADING, null, null, null)
    }

    fun monthlyDate(doctorId: Int, response: MutableLiveData<Resource<List<MonthlyDateResponse>>>) {
        compositeDisposable.add(
            apiClient.monthlyDate(doctorId, "Bearer ${Constants.token}")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<List<MonthlyDateResponse>>() {
                    override fun onNext(t: List<MonthlyDateResponse>) {
                        response.value = Resource(Status.SUCCESS, t, null, null)
                    }

                    override fun onError(e: Throwable) {
                        if (e.message?.contains("401", true) == true) {
                            Constants.setUnAuthorized(true)
                        }
                        response.value = Resource(Status.ERROR, null, e.message, e)
                    }

                    override fun onComplete() {}
                })
        )
        response.value = Resource(Status.LOADING, null, null, null)
    }

    fun monthlyTime(date: String, doctorId: Int, response: MutableLiveData<Resource<MonthlyTimeResponse>>) {
        compositeDisposable.add(
            apiClient.monthlyTime(date, doctorId, "Bearer ${Constants.token}")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<MonthlyTimeResponse>() {
                    override fun onNext(t: MonthlyTimeResponse) {
                        response.value = Resource(Status.SUCCESS, t, null, null)
                    }

                    override fun onError(e: Throwable) {
                        if (e.message?.contains("401", true) == true) {
                            Constants.setUnAuthorized(true)
                        }
                        response.value = Resource(Status.ERROR, null, e.message, e)
                    }

                    override fun onComplete() {}
                })
        )
        response.value = Resource(Status.LOADING, null, null, null)
    }

    fun branch(response: MutableLiveData<Resource<List<BranchResponse>>>){
        compositeDisposable.add(
            apiClient.branch("Bearer ${Constants.token}")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<List<BranchResponse>>() {
                    override fun onNext(t: List<BranchResponse>) {
                        response.value = Resource(Status.SUCCESS, t, null, null)
                    }

                    override fun onError(e: Throwable) {
                        if (e.message?.contains("401", true) == true) {
                            Constants.setUnAuthorized(true)
                        }
                        response.value = Resource(Status.ERROR, null, e.message, e)
                    }

                    override fun onComplete() {}
                })
        )
        response.value = Resource(Status.LOADING, null, null, null)
    }

}