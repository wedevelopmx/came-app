package mx.wedevelop.came.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import mx.wedevelop.came.R;
import mx.wedevelop.came.model.Service;
import mx.wedevelop.came.model.Visitor;

/**
 * Created by root on 5/12/16.
 */
public class ServiceAdapter extends ArrayAdapter<Service> {
    public ServiceAdapter(Activity context, List<Service> serviceList) {
        super(context, 0, serviceList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null)
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.service_selection_grid_item, parent, false);

        // Get the {@link AndroidFlavor} object located at this position in the list
        Service currentService = (Service) getItem(position);

        ImageView imageView = (ImageView) listItemView.findViewById(R.id.service_icon);
        imageView.setImageBitmap(currentService.getImage());

        TextView textView = (TextView) listItemView.findViewById(R.id.service_name);
        textView.setText(currentService.getName());

        if(currentService.isSelected())
            listItemView.setBackgroundColor(getContext().getResources().getColor(R.color.colorAccent));
        else
            listItemView.setBackgroundColor(getContext().getResources().getColor(R.color.colorPrimary));


        return listItemView;
    }
}
