package mx.wedevelop.came.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import mx.wedevelop.came.R;
import mx.wedevelop.came.model.Service;

/**
 * Created by Cristian Colorado <cristian@wedevelop.mx> on 6/12/16.
 */
public class ServiceDialog extends DialogFragment {

    public static final String SERVICE = "SERVICE";

    private Service service;

    public static ServiceDialog newInstance(Service service, ServiceDialogListener listener) {
        ServiceDialog f = new ServiceDialog();

        Bundle args = new Bundle();
        args.putParcelable(SERVICE, service);
        f.setArguments(args);
        f.setListener(listener);

        return f;
    }

    public static interface ServiceDialogListener {
        public void onPositiveAnswer(DialogInterface dialog, int id);
        public void onNegativeAnswer(DialogInterface dialog, int id);
    }

    private ServiceDialogListener listener;

    public void setListener(ServiceDialogListener listener) {
        this.listener = listener;
    }


    public ServiceDialog() {

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

//        try {
//            //If dialog is invoked from Fragment
//            listener = (ServiceDialogListener) getTargetFragment();
//            if(listener == null) {
//                //Othewise it should be invoked from Activity
//                listener = (ServiceDialogListener) getActivity();
//            }
//        } catch(ClassCastException e) {
//            throw new ClassCastException("Calling Fragment must implement SimpleDialogListener");
//        }
        service = getArguments().getParcelable(SERVICE);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext(), R.style.CameAlertDialog);

        View view = getActivity().getLayoutInflater().inflate(R.layout.service_dialog_fmt, null);

        ImageView serviceIcon = (ImageView) view.findViewById(R.id.service_icon);
        serviceIcon.setImageBitmap(service.getImage());
        TextView serviceQuestion = (TextView) view.findViewById(R.id.service_question);
        serviceQuestion.setText(getResources().getString(R.string.service_dialog_question, service.getName()));
        TextView serviceInstruction = (TextView) view.findViewById(R.id.service_instructions);
        serviceInstruction.setText(service.getInstructions());


        dialogBuilder.setTitle(service.getName());
        dialogBuilder.setView(view)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        service.setSelected(!service.isSelected());
                        listener.onPositiveAnswer(dialog, id);
                    }
                })
                .setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onNegativeAnswer(dialog, id);
                    }
                });

        return dialogBuilder.create();
    }
}
