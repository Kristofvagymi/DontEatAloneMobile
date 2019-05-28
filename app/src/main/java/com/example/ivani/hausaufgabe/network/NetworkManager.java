package com.example.ivani.hausaufgabe.network;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.example.ivani.hausaufgabe.MainActivity;
import com.example.ivani.hausaufgabe.R;
import com.example.ivani.hausaufgabe.adapter.DetailedInformationVisualizer;
import com.example.ivani.hausaufgabe.adapter.HistoricEventAdapter;
import com.example.ivani.hausaufgabe.adapter.PagerAdapter;
import com.example.ivani.hausaufgabe.adapter.UpcomingEventAdapter;
import com.example.ivani.hausaufgabe.model.CreateEventModel;
import com.example.ivani.hausaufgabe.model.EventDetailedModel;
import com.example.ivani.hausaufgabe.model.EventPreviewModel;
import com.example.ivani.hausaufgabe.model.LoginModel;
import com.example.ivani.hausaufgabe.model.UserModel;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkManager {
    Retrofit retrofit;
    ApiManager service;
    private FragmentActivity activity;

    public NetworkManager(FragmentActivity activity){
        this.activity = activity;
        retrofit=new Retrofit.Builder ()
                .baseUrl("http://152.66.177.172:1337")
                .client(new OkHttpClient.Builder().build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service=retrofit.create(ApiManager.class);
    }

    public void login(final LoginModel loginModel) {
        service.login(loginModel.getEmail(),loginModel.getPassword()).enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(@NonNull Call<UserModel> call,
                                   @NonNull Response<UserModel> response) {
                if (response.body() != null) {
                    Toast.makeText(activity, R.string.succAuthentication, Toast.LENGTH_SHORT).show();
                    UserModel userModel = response.body();
                    Intent loginSuccessful = new Intent(activity, MainActivity.class);
                    loginSuccessful.putExtra("authUser", userModel);
                    loginSuccessful.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    activity.startActivity(loginSuccessful);
                } else {
                    Toast.makeText(activity, R.string.incorrectemailorpw, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<UserModel> call, @NonNull Throwable throwable) {
                throwable.printStackTrace();
                Toast.makeText(activity,
                        R.string.networkerror,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void register(UserModel userModel) {
        service.register(
                userModel.getFirstName(),
                userModel.getLastName(),
                userModel.getEmail(),
                userModel.getPassword(),
                userModel.getDateOfBirth(),
                userModel.getJob(),
                userModel.getHobby(),
                userModel.getSex(),
                userModel.getPhoneNumber()).enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(@NonNull Call<UserModel> call,
                                   @NonNull Response<UserModel> response) {
                if (response.body() != null) {
                    Toast.makeText(activity, R.string.succRegister, Toast.LENGTH_SHORT).show();
                    UserModel userModel = response.body();
                    Intent loginSuccessful = new Intent(activity, MainActivity.class);
                    loginSuccessful.putExtra("authUser", userModel);
                    loginSuccessful.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    activity.startActivity(loginSuccessful);
                } else {
                    if (response.code() == 400) {
                        Toast.makeText(activity, R.string.invalidparameters, Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 409) {
                        Toast.makeText(activity, R.string.emailnotunique, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(activity, R.string.registrationerror, Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<UserModel> call, @NonNull Throwable throwable) {
                throwable.printStackTrace();
                Toast.makeText(activity, R.string.networkerror, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateUserModel(UserModel userModel) {
        service.updateUserModel(
                userModel.getFirstName(),
                userModel.getLastName(),
                userModel.getEmail(),
                userModel.getPassword(),
                userModel.getDateOfBirth(),
                userModel.getJob(),
                userModel.getHobby(),
                userModel.getSex(),
                userModel.getPhoneNumber()).enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(@NonNull Call<UserModel> call,
                                   @NonNull Response<UserModel> response) {
                if (response.body() != null) {
                    Toast.makeText(activity, R.string.succdatachange, Toast.LENGTH_SHORT).show();
                    UserModel userModel = response.body();
                    ((MainActivity) activity).updateUserModel(userModel);
                } else {
                    if (response.code() == 400) {
                        Toast.makeText(activity, R.string.invalidparameters, Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 409) {
                        Toast.makeText(activity, R.string.emailnotunique, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(activity, R.string.registrationerror, Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<UserModel> call, @NonNull Throwable throwable) {
                throwable.printStackTrace();
                Toast.makeText(activity, R.string.networkerror, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void createEvent(CreateEventModel createEventModel) {
        service.createEvent(
                createEventModel.getTitle(),
                createEventModel.getCity(),
                createEventModel.getCountry(),
                createEventModel.getDescription(),
                createEventModel.getDateTime(),
                createEventModel.getNumOfMaxParticipants(),
                createEventModel.getUserId()).enqueue(new Callback<EventPreviewModel>() {
            @Override
            public void onResponse(@NonNull Call<EventPreviewModel> call,
                                   @NonNull Response<EventPreviewModel> response) {
                if (response.body() != null) {
                    ((MainActivity) activity).closeDialogFragment();
                    ((MainActivity) activity).updateEventLists();
                    Toast.makeText(activity, R.string.eventcreated, Toast.LENGTH_SHORT).show();
                } else {
                    if (response.code() == 400) {
                        Toast.makeText(activity, R.string.invalidparameters, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(activity, R.string.error, Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<EventPreviewModel> call, @NonNull Throwable throwable) {
                throwable.printStackTrace();
                Toast.makeText(activity, R.string.networkerror, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getEvents(final UpcomingEventAdapter adapter, UserModel userModel) {
        service.getEvents(userModel.getId()).enqueue(new Callback< List<EventPreviewModel> >() {
            @Override
            public void onResponse(@NonNull Call< List<EventPreviewModel> > call,
                                   @NonNull Response< List<EventPreviewModel> > response) {
                if (response.body() != null) {
                    adapter.updateListItems(response.body());
                } else {
                    Toast.makeText(activity, R.string.error, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call< List<EventPreviewModel> > call, @NonNull Throwable throwable) {
                throwable.printStackTrace();
                Toast.makeText(activity, R.string.networkerror, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getHistoricEvents(final HistoricEventAdapter adapter, UserModel userModel) {
        service.getHistoricEvents(userModel.getId()).enqueue(new Callback< List<EventPreviewModel> >() {
            @Override
            public void onResponse(@NonNull Call< List<EventPreviewModel> > call,
                                   @NonNull Response< List<EventPreviewModel> > response) {
                if (response.body() != null) {
                    adapter.updateListItems(response.body());
                } else {
                    Toast.makeText(activity, R.string.error, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call< List<EventPreviewModel> > call, @NonNull Throwable throwable) {
                throwable.printStackTrace();
                Toast.makeText(activity, R.string.networkerror, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getEventDetails(EventPreviewModel eventPreviewModel, final DetailedInformationVisualizer historicEventFragment) {
        service.getEventDetails(eventPreviewModel.getId()).enqueue(new Callback<EventDetailedModel >() {
            @Override
            public void onResponse(@NonNull Call< EventDetailedModel > call,
                                   @NonNull Response< EventDetailedModel > response) {
                if (response.body() != null) {
                    historicEventFragment.showDetailedInformation(response.body());
                } else {
                    Toast.makeText(activity, R.string.error, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<EventDetailedModel > call, @NonNull Throwable throwable) {
                throwable.printStackTrace();
                Toast.makeText(activity, R.string.networkerror, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void applyToAnEvent(int eventId, int userId, final PagerAdapter pagerAdapter) {
        service.applyToAnEvent(eventId, userId).enqueue(new Callback<EventPreviewModel >() {
            @Override
            public void onResponse(@NonNull Call< EventPreviewModel > call,
                                   @NonNull Response< EventPreviewModel > response) {
                if (response.body() != null) {
                    Toast.makeText(activity, R.string.succApply, Toast.LENGTH_SHORT).show();
                    pagerAdapter.updateEventFragments();
                } else {
                    Toast.makeText(activity, R.string.error, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<EventPreviewModel > call, @NonNull Throwable throwable) {
                throwable.printStackTrace();
                Toast.makeText(activity, R.string.networkerror, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
