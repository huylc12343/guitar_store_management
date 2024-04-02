package com.example.g2pedal.BottomNavBar.UserNav;

import static com.example.g2pedal.R.layout.item_usernav;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;
import com.example.g2pedal.R;
public class UserAdapter extends ArrayAdapter<String> {

    public UserAdapter(Context context, List<String> userList) {
        super(context, 0, userList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        String userName = getItem(position);
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        convertView = layoutInflater.inflate(item_usernav,parent,false);
        TextView userNameTextView = convertView.findViewById(R.id.txtTextView);
        userNameTextView.setText(userName);

        return convertView;
    }
}