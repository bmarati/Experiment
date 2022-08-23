package com.example.accountsapp.ui.dashboard;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.accountsapp.R;
import com.example.accountsapp.ui.dashboard.data.Account;

import java.util.ArrayList;

public class AccountsAdapter extends RecyclerView.Adapter<AccountsAdapter.AccountViewHolder> {

    private ArrayList<Account> courseModalArrayList;
    private Context context;

    public AccountsAdapter(ArrayList<Account> courseModalArrayList, Context context) {
        this.courseModalArrayList = courseModalArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public AccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.account_item, parent, false);
        return new AccountViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountViewHolder holder, int position) {
        Account modal = courseModalArrayList.get(position);
        holder.name.setText(modal.getName());
        holder.accountId.setText(modal.getId());
        holder.amount.setText(String.format("%s", modal.getAmount()));
    }

    @Override
    public int getItemCount() {
        return courseModalArrayList.size();
    }

    public static class AccountViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView accountId;
        private final TextView amount;

        public AccountViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.account_name);
            accountId = itemView.findViewById(R.id.account_id);
            amount = itemView.findViewById(R.id.amount);
        }
    }
}

