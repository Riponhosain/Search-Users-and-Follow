package com.example.follow.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.follow.FollowersActivity;
import com.example.follow.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ProfileFragment extends Fragment {


    TextView followers, following;
    FirebaseUser firebaseUser;
   // Button edit_profile;
    //CircleImageView image_profile;
    //TextView username,fullname;

    String profileid;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        SharedPreferences prefs = getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        profileid = prefs.getString("profileid","none");


        //edit_profile = view.findViewById(R.id.edit_profile);
        followers = view.findViewById(R.id.followers);
        following = view.findViewById(R.id.following);
       // image_profile = view.findViewById(R.id.image_profile);
     //   username = view.findViewById(R.id.username);
        //fullname = view.findViewById(R.id.fullname);



        getFollowers();
        userInfo();

//        if (profileid.equals(firebaseUser.getUid())){
//            edit_profile.setText("Edit Profile");
//        }else {
//            checkFollow();
//        }



//        edit_profile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String btn = edit_profile.getText().toString();
//
//                if (btn.equals("Edit Profile")){
//                    //go to edit profile
//                }else if (btn.equals("follow")){
//
//                    FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid())
//                            .child("following").child(profileid).setValue(true);
//
//                    FirebaseDatabase.getInstance().getReference().child("Follow").child(profileid)
//                            .child("followers").child(firebaseUser.getUid()).setValue(true);
//                }
//                else if (btn.equals("following")){
//
//                    FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid())
//                            .child("following").child(profileid).removeValue();
//
//                    FirebaseDatabase.getInstance().getReference().child("Follow").child(profileid)
//                            .child("followers").child(firebaseUser.getUid()).removeValue();
//                }
//            }
//        });


        followers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FollowersActivity.class);
                intent.putExtra("id",profileid);
                intent.putExtra("title", "followers");
                startActivity(intent);
            }
        });

        following.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FollowersActivity.class);
                intent.putExtra("id",profileid);
                intent.putExtra("title", "following");
                startActivity(intent);
            }
        });

        return view;
    }

    private void userInfo(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child("profiled");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (getContext() == null){
                    return;
                }
               // User user = dataSnapshot.getValue(User.class);

               // Glide.with(getContext()).asBitmap().load(user.getImageurl()).into(image_profile);
//                username.setText(user.getUsername());
//                fullname.setText(user.getFullname());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

//    private void checkFollow(){
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
//                .child("Follow").child(firebaseUser.getUid()).child("following");
//
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
////                if (dataSnapshot.child(profileid).exists()){
////                    edit_profile.setText("following");
////                }else {
////                    edit_profile.setText("follow");
////                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }

    private void getFollowers(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("Follow").child(firebaseUser.getUid()).child("followers");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                followers.setText(""+dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference()
                .child("Follow").child(firebaseUser.getUid()).child("following");

        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                following.setText(""+dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
