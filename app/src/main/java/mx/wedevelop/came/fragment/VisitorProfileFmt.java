package mx.wedevelop.came.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import mx.wedevelop.came.R;
import mx.wedevelop.came.model.Service;
import mx.wedevelop.came.model.Visitor;
import mx.wedevelop.came.utils.Utils;

/**
 * Created by Cristian Colorado <cristian@wedevelop.mx> on 6/12/16.
 */
public class VisitorProfileFmt extends Fragment {
    public static final String VISITOR = "VISITOR";

    public static VisitorProfileFmt newInstance(Visitor visitor) {
        VisitorProfileFmt visitorProfileFmt = new VisitorProfileFmt();

        Bundle bundle = new Bundle();
        bundle.putParcelable(VisitorProfileFmt.VISITOR, visitor);
        visitorProfileFmt.setArguments(bundle);
        return visitorProfileFmt;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.visitor_profile_fmt, container, false);


        Visitor visitor = getArguments().getParcelable(VISITOR);

        TextView name = (TextView) rootView.findViewById(R.id.visitor_name);
        name.setText(visitor.getDisplayName());

        TextView alias = (TextView) rootView.findViewById(R.id.visitor_alias);
        alias.setText(visitor.getAlias());

        TextView location = (TextView) rootView.findViewById(R.id.visitor_location);
        location.setText(visitor.getLocation());

        TextView birtdate = (TextView) rootView.findViewById(R.id.visitor_birthdate);
        birtdate.setText(Utils.formatDate(visitor.getBirthdate()));

        TextView gender = (TextView) rootView.findViewById(R.id.visitor_gender);
        gender.setText(visitor.getGender());

        TextView status = (TextView) rootView.findViewById(R.id.visitor_status);
        status.setText(visitor.getStatus());

        TextView checkin = (TextView) rootView.findViewById(R.id.visitor_checkin);
        checkin.setText("Dic 2, 2016");

        FloatingActionButton edit = (FloatingActionButton) rootView.findViewById(R.id.fab);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Pronto podras editar el perfil del visitante desde este lugar!", Toast.LENGTH_LONG).show();
            }
        });

        return rootView;
    }
}
