package mx.wedevelop.came;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import mx.wedevelop.came.model.Visitor;
import mx.wedevelop.came.rest.ServiceGenerator;
import mx.wedevelop.came.rest.VisitorClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewVisitorActivity extends AppCompatActivity implements View.OnClickListener {
    static final int REQUEST_IMAGE_CAPTURE = 4000;
    static final int REQUEST_IMAGE_CROP = 4001;

    private Visitor visitor = new Visitor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_visitor);

        ImageButton button = (ImageButton) findViewById(R.id.camera_button);
        button.setOnClickListener(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.camera_button:
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
                break;
            case R.id.fab:
                readVisitorForm();
                submitVisitor();
                break;
        }
    }

    private void readVisitorForm() {
        visitor.setFirstName(readFormInput(R.id.visitor_name));
        visitor.setLastName(readFormInput(R.id.visitor_last_name));
        visitor.setSecondSurename(readFormInput(R.id.visitor_second_surename));
        visitor.setAlias(readFormInput(R.id.visitor_alias));
        visitor.setCountry(readFormInput(R.id.visitor_country));
        visitor.setState(readFormInput(R.id.visitor_state));
        visitor.setTown(readFormInput(R.id.visitor_town));
    }

    private String readFormInput(int id) {
        TextInputEditText field = (TextInputEditText) findViewById(id);
        return field.getText().toString();
    }

    private void submitVisitor() {
        VisitorClient client = ServiceGenerator.createService(VisitorClient.class);

        Call<Visitor> call = client.saveVisitor(visitor);

        call.enqueue(new Callback<Visitor>() {
            @Override
            public void onResponse(Call<Visitor> call, Response<Visitor> response) {
                Log.i(NewVisitorActivity.class.getName(), response.body().toString());
                visitorSaved();
            }

            @Override
            public void onFailure(Call<Visitor> call, Throwable t) {
                Log.i(NewVisitorActivity.class.getName(), t.toString());
            }
        });
    }

    private void visitorSaved() {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Uri pictureURI = data.getData();
            performCrop(pictureURI);

        } else if(requestCode == REQUEST_IMAGE_CROP && resultCode == RESULT_OK) {

            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            //Save Picture
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();
            String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
            Log.d(NewVisitorActivity.class.getName(), encoded);

            visitor.setPictureDataURI("data:image/jpeg;base64," + encoded);

            //Set image
            ImageView imageView = (ImageView) findViewById(R.id.visitor_picture);
            RoundedBitmapDrawable dr = RoundedBitmapDrawableFactory.create(getResources(), imageBitmap);
            dr.setCornerRadius(Math.max(imageBitmap.getWidth(), imageBitmap.getHeight()) / 2.0f);
            imageView.setImageDrawable(dr);


            //Make section invisible
            RelativeLayout cameraLayout = (RelativeLayout) findViewById(R.id.camera_layout);
            cameraLayout.setVisibility(View.GONE);
            RelativeLayout pictureLayout = (RelativeLayout) findViewById(R.id.picture_layout);
            pictureLayout.setVisibility(View.VISIBLE);

        }
    }

    void performCrop(Uri pictureDataUri) {
        // take care of exceptions
        try {
            //call the standard crop action intent (the user device may not support it)
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            //indicate image type and Uri
            cropIntent.setDataAndType(pictureDataUri, "image/*");
            //set crop properties
            cropIntent.putExtra("crop", "true");
            //indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            //indicate output X and Y
            cropIntent.putExtra("outputX", 256);
            cropIntent.putExtra("outputY", 256);
            //retrieve data on return
            cropIntent.putExtra("return-data", true);
            //start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, REQUEST_IMAGE_CROP);
        } catch (ActivityNotFoundException anfe) {
            Toast toast = Toast.makeText(this, "This device doesn't support the crop action!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
