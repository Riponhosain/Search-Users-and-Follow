package com.example.follow.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.follow.Adapter.UserAdapter;
import com.example.follow.Model.User;
import com.example.follow.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment {


   private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<User> musers;

    EditText search_bar;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
     View view = inflater.inflate(R.layout.fragment_search, container, false);


        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        search_bar = view.findViewById(R.id.search_bar);

        musers = new ArrayList<>();
        userAdapter = new UserAdapter(getContext(), musers);
        recyclerView.setAdapter(userAdapter);

//        search_bar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                String searchText = search_bar.getText().toString();
//
//            }
//        });


       readUsers();

       search_bar.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {

           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {

            searchUsers(s.toString().toLowerCase());
           }

           @Override
           public void afterTextChanged(Editable s) {

           }
       });

        return view;
    }

private void searchUsers (String s){
    Query query = FirebaseDatabase.getInstance().getReference("Users").orderByChild("username")
            .startAt(s)
            .endAt(s+ "\uf8ff");

    query.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            musers.clear();

            for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                User user = snapshot.getValue(User.class);
                musers.add(user);
            }
            userAdapter.notifyDataSetChanged();
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });
}

private void readUsers (){
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
    reference.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (search_bar.getText().toString().equals("")){
                musers.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                     User user = snapshot.getValue(User.class);
                     musers.add(user);
                }
                userAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });
}

}
