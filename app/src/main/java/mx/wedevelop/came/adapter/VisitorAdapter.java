package mx.wedevelop.came.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import mx.wedevelop.came.R;
import mx.wedevelop.came.model.Visitor;

/**
 * Created by root on 5/12/16.
 */
public class VisitorAdapter extends ArrayAdapter<Visitor> {
    public VisitorAdapter(Activity context, List<Visitor> visitorList) {
        super(context, 0, visitorList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null)
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.visitor_list_item, parent, false);

        // Get the {@link AndroidFlavor} object located at this position in the list
        Visitor currentVisitor = (Visitor) getItem(position);

        TextView visitorNameTextView = (TextView)listItemView.findViewById(R.id.visitor_name);
        visitorNameTextView.setText(currentVisitor.getDisplayName());

        TextView visitorLocationTextView = (TextView)listItemView.findViewById(R.id.visitor_location);
        visitorLocationTextView.setText(currentVisitor.getLocation());

        ImageView imageView = (ImageView) listItemView.findViewById(R.id.visitor_picture);

        //Make circular bitmap
        Bitmap image = currentVisitor.getImage();
        RoundedBitmapDrawable dr =
                RoundedBitmapDrawableFactory.create(getContext().getResources(), image);
        dr.setCornerRadius(Math.max(image.getWidth(), image.getHeight()) / 2.0f);
        imageView.setImageDrawable(dr);

        return listItemView;
    }
}
