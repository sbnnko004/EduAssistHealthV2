package za.co.eduassistgo.edu_assisthealth;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.CardView;
import android.widget.ScrollView;
import android.widget.TextView;


public class Tab01Home extends Fragment {
    public Tab01Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.tab01_home, container, false);

        return rootView;
    }
}
