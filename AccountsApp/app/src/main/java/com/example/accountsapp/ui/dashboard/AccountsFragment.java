package com.example.accountsapp.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.accountsapp.R;
import com.example.accountsapp.databinding.FragmentDashboardBinding;
import com.example.accountsapp.ui.dashboard.data.Account;

import java.util.ArrayList;

public class AccountsFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private AccountsAdapter adapter;
    private RecyclerView accounts_list;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AccountsViewModel accountsViewModel =
                new ViewModelProvider(this).get(AccountsViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        accounts_list = root.findViewById(R.id.accounts);

        accountsViewModel.getAccounts().observe(getViewLifecycleOwner(), accounts -> {
            adapter = new AccountsAdapter(accounts, this.requireActivity());
            LinearLayoutManager manager = new LinearLayoutManager(this.requireActivity());
            accounts_list.setHasFixedSize(true);
            accounts_list.setLayoutManager(manager);
            accounts_list.setAdapter(adapter);
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}