package mx.wedevelop.came.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import mx.wedevelop.came.R;
import mx.wedevelop.came.model.Visitor;

/**
 * Created by root on 5/12/16.
 */
public class VisitorCard extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.visitor_card_fmt, container, false);
        return rootView;
    }

    public void updateUI(Visitor v) {
        TextView nameTextView = (TextView) getView().findViewById(R.id.visitor_name);
        nameTextView.setText(v.getDisplayName());
        TextView locationTextView = (TextView) getView().findViewById(R.id.visitor_location);
        locationTextView.setText(v.getLocation());
        ImageView profileImageView = (ImageView) getView().findViewById(R.id.visitor_picture);
        profileImageView.setImageBitmap(v.getImage());
    }
}
