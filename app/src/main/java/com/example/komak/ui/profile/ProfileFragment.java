package com.example.komak.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.komak.DBHelper;
import com.example.komak.MainActivity;
import com.example.komak.R;
import com.example.komak.Session;
import com.example.komak.databinding.FragmentProfileBinding;
import com.example.komak.ui.changepass.ChangePass;
import com.example.komak.ui.login.Login;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private FragmentProfileBinding binding;
    Session session;
    DBHelper db;
    private String emaiil;
    private String namee;
    private String lastnamee;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
      //  View root = binding.getRoot();

        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        session = new Session(getActivity());
        db = new DBHelper(getActivity());


        Cursor cursor = db.getuserdata(session.getemail());

        TextView name = (TextView) root.findViewById(R.id.profilename);
        TextView email = (TextView) root.findViewById(R.id.profileemail);

        //loop tp get user data that we want
        while (cursor.moveToNext()) {
            int index;

            index = cursor.getColumnIndexOrThrow("email");
            emaiil = cursor.getString(index);


            index = cursor.getColumnIndexOrThrow("firstname");
            namee = cursor.getString(index);

            index = cursor.getColumnIndexOrThrow("lastname");
            lastnamee = cursor.getString(index);

        }
        //setting values we got from loop into those textviews
        name.setText(namee+" "+lastnamee);
        email.setText(emaiil);

        Button changepass = (Button) root.findViewById(R.id.change);
        changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go = new Intent(getActivity(), ChangePass.class);
                startActivity(go);
            }
        });

        Button logout = (Button) root.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                session.DestroySession();
                Intent go1 = new Intent(getActivity(), Login.class);
                startActivity(go1);
                getActivity().finish();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}