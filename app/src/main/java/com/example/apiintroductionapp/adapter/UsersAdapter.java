package com.example.apiintroductionapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



import androidx.recyclerview.widget.RecyclerView;

import com.example.apiintroductionapp.R;
import com.example.apiintroductionapp.model.User;

import java.util.List;

import io.reactivex.annotations.NonNull;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {
    private final List<User> data;
    private final LayoutInflater inflater;
    public UsersAdapter(
            Context context,
            List<User> data
    ) {
        this.inflater = LayoutInflater.from(context);
        this.data = data;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = this.inflater.inflate(R.layout.item_users_list, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User item = data.get(position);
        if (item == null) {
            return;
        } //if
        holder.txtUsersListUserName.setText(item.getFullName());
        holder.txtUsersListUserEmail.setText(item.getEmail());
    }
    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtUsersListUserName;
        TextView txtUsersListUserEmail;
        ViewHolder(View itemView) {
            super(itemView);
            this.txtUsersListUserName = itemView.findViewById(R.id.txtUsersListUserName);
            this.txtUsersListUserEmail =
                    itemView.findViewById(R.id.txtUsersListUserEmail);
        }
    }
}

