package com.example.g2pedal.BottomNavBar.UserNav;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.g2pedal.DAO.UserDAO;
import com.example.g2pedal.DTO.UserDTO;
import com.example.g2pedal.R;
import com.example.g2pedal.ui.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ListView listView;
    private UserAdapter adapter;
    private List<String> userList;
    private ImageView avatar ;
    private Button btnLogOut;
    private TextView fullname,phonetv,mail;

    public UserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserFragment newInstance(String param1, String param2) {
        UserFragment fragment = new UserFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        UserDAO userDAO = new UserDAO();
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        listView = view.findViewById(R.id.userNavListview);
        userList = createUserList();
        btnLogOut = view.findViewById(R.id.btnLogout);
        // lấy Uid từ bundle
        String uid = getArguments().getString("uid");

        fullname = view.findViewById(R.id.userFragUserFullname);
        mail = view.findViewById(R.id.userFragMail);
        phonetv = view.findViewById(R.id.userFragPhone);

        //tạo list view hiển thị các hoạt động đổi mật khẩu, thông tin người dùng (chưa haoafn thành)
        adapter = new UserAdapter(requireContext(), userList);
        listView.setAdapter(adapter);
        avatar = view.findViewById(R.id.userAvatarFunc);

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut(); // Sign out
                Toast.makeText(getContext(), "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
                if (getActivity() != null) {
                    getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
                    getActivity().finish();
                }
            }
        });

        //load dữ liệu từ Firebase có chứa Uid được truyền từ MainActivity vào
        userDAO.getUserData(uid, new UserDAO.OnUserDataLoadedListener()  {
            @Override
            public void onUserDataLoaded(UserDTO user) {
                fullname.setText(user.getFullName());
                mail.setText(user.getMail());
                phonetv.setText(user.getPhone());
            }

            @Override
            public void onUserNotFound() {

            }

            @Override
            public void onDataLoadFailed(String errorMessage) {

            }
        });


        return view;

    }

    private List<String> createUserList() {
        List<String> userList = new ArrayList<>();
        userList.add("Thông tin người dùng");
        userList.add("Đổi mật khẩu");
        return userList;
    }

}