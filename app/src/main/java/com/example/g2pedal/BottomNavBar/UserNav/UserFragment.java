package com.example.g2pedal.BottomNavBar.UserNav;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.g2pedal.R;

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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        listView = (ListView) view.findViewById(R.id.userNavListview);
        userList = createUserList();

        adapter = new UserAdapter(requireContext(), userList);

        listView.setAdapter(adapter);
        avatar = (ImageView) view.findViewById(R.id.userAvatarFunc);
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                showPopupMenu(v);
            }
        });
//        listView = view.findViewById(R.id.userListView);
//        adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, userList);
//        listView.setAdapter(adapter);

        return view;
//        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    private List<String> createUserList() {
        List<String> userList = new ArrayList<>();
        userList.add("Thông tin người dùng");
        userList.add("Đổi mật khẩu");
        return userList;
    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(getContext(), view);
        popupMenu.inflate(R.menu.popup_menu); // R.menu.popup_menu là tệp menu bạn đã tạo

        // Xử lý sự kiện khi một mục được chọn
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Xử lý các hành động tương ứng với từng mục được chọn
                int id = item.getItemId();
                if (id == R.id.selectIMG) {
                    openIMGPicker();
                } else if (id == R.id.shootIMG) {
                    takeIMGfunc();
                } else if (id == R.id.watchIMG) {
                    watchIMGfunc();
                }
                return true;
            }
        });

        // Hiển thị PopupMenu
        popupMenu.show();
    }
    private void openIMGPicker(){
        Toast.makeText(getContext(), "OpenGalery", Toast.LENGTH_SHORT);
    }
    private void takeIMGfunc(){
        Toast.makeText(getContext(),"Take IMG func",Toast.LENGTH_SHORT);
    }
    private void watchIMGfunc(){
        Toast.makeText(getContext(),"watchIMG",Toast.LENGTH_SHORT);
    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
//        super.onActivityResult(requestCode,resultCode,data);
//        Uri uri = data.getData();
//        avatar.setImageURI(uri);
//    }
}