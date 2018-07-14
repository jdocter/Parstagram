package com.example.jdocter.parstagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.parse.ParseException;
import com.parse.ParseUser;


public class SelfFragment extends Fragment {
    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.

    private ImageView ivSettings;
    private TextView tvSelfUser;
    private ImageView ivSelfProfile;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_self, parent, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);

        ParseUser user = ParseUser.getCurrentUser();

        ivSettings = view.findViewById(R.id.ivSettings);
        tvSelfUser = view.findViewById(R.id.tvSelfUser);
        ivSelfProfile = view.findViewById(R.id.ivSelfProfile);

        Log.d("SelfFragment", user.getUsername());
        tvSelfUser.setText(user.getUsername());
        String profileUrl = null;
        try {
            profileUrl = user.fetch().getParseFile("profilePicture").getUrl();
        } catch (ParseException e) {
            e.printStackTrace();
        }


        // load image using glide
        Glide.with(getActivity())
                .load(profileUrl)
                .apply(new RequestOptions().transform(new CircleTransform(getActivity())))
                .into(ivSelfProfile);

        ivSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),SettingsActivity.class);
                startActivity(intent);
            }
        });
    }
}
