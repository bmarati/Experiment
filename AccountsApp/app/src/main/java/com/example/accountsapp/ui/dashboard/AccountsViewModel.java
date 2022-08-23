package com.example.accountsapp.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.example.accountsapp.ui.dashboard.data.Account;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AccountsViewModel extends ViewModel {

    private static final int MY_SOCKET_TIMEOUT_MS = 50000;
    private final MutableLiveData<String> mText;
    private final MutableLiveData<ArrayList<Account>> accountsData;
    private ArrayList<Account> dataModalArrayList;

    public AccountsViewModel() {
        mText = new MutableLiveData<>();
        accountsData = new MutableLiveData<>();
        dataModalArrayList = new ArrayList<>();
        String url = "https://fssservices.bookxpert.co/api/getownerslist/2021-01-16/payments/owner";

        StringRequest stringRequest = new StringRequest( url,
                stringResponse -> {
                    try {
                        Gson gson = new Gson();
                        JsonParser jsonParser = new JsonParser();
                        JsonArray response = (JsonArray) jsonParser.parse(stringResponse);
//                        JSONArray response=new JSONArray(stringResponse);
                        dataModalArrayList = new ArrayList<>();
                        for (int i = 0; i < response.size(); i++) {
//                            Js responseObj = response.get(i);
//
//                            int id = responseObj.getInt("ActID");
//                            String name = responseObj.getString("ActName");
//                            double amount = responseObj.getDouble("amount");
//                            dataModalArrayList.add(new Account(id, name, amount));
                        }
                        accountsData.setValue(dataModalArrayList);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                },
                error -> {

                });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = VolleySingleton.getRequestQueue();
        if(requestQueue != null)
        requestQueue.add(stringRequest);
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<ArrayList<Account>> getAccounts(){
        return accountsData;
    }
}