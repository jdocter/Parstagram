package com.example.jdocter.parstagram;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;


public class CreateFragment extends Fragment {

    private ImageView ivPostImage;
    private Button btnShare;
    private EditText etCaption;
    private Bitmap bitmapPostImage;
    private EditText etLocation;

    interface Callback {

        /**
         * To be called when a post was successfully created.
         */
        void onPostCreated();
    }

    /**
     * It's a "mask" to our activity so that way we can update it, and not be tightly coupled to it.
     */
    private Callback callback;

    // private static final String imagePath = "/Users/jdocter/Downloads/twitter-logo.png";

    // photo declarations
    public String photoFileName = "photo.jpg";
    static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 12345;
    public final String APP_TAG = "MyCustomApp";
    public File photoFile;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Callback) {
            callback = (Callback) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        onLaunchCamera();
        return inflater.inflate(R.layout.fragment_create, parent, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
        ivPostImage = view.findViewById(R.id.ivCreatePostImage);
        btnShare = view.findViewById(R.id.btnShare);
        etCaption = view.findViewById(R.id.etCaption);
        etLocation = view.findViewById(R.id.etLocation);


        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String caption = etCaption.getText().toString();
                final String location = etLocation.getText().toString();
                final ParseUser user = ParseUser.getCurrentUser();
                final File file;

                // try converting bitmap to parsefile
                try {
                    file = getImageFile(bitmapPostImage);
                    final ParseFile parseFile = new ParseFile(file);
                    // create post
                    createPost(caption, parseFile, user,location);
                } catch (IOException e) {
                    Log.e("MainActivity","Bit map could not be converted to ParseFile");
                    e.printStackTrace();
                }
            }
        });

    }

    // resizes bitmap and writes to file
    public File getImageFile(Bitmap bitmap) throws IOException {
        // Configure byte output stream
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        // Compress the image further
        bitmap.compress(Bitmap.CompressFormat.JPEG, 40, bytes);
        // Create a new file for the resized bitmap (`getPhotoFileUri` defined above)
        File resizedUri = getPhotoFileUri(photoFileName + "_resized");
        File resizedFile = new File(resizedUri.getPath());
        resizedFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(resizedFile);
        // Write the bytes of the bitmap to file
        fos.write(bytes.toByteArray());
        fos.close();
        return resizedFile;
    }

    public void onLaunchCamera() {
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Create a File reference to access to future access
        photoFile = getPhotoFileUri(photoFileName);

        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        Uri fileProvider = FileProvider.getUriForFile(getActivity(), "com.codepath.fileprovider", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }

    // Returns the File for a photo stored on disk given the fileName
    public File getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES), APP_TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
            Log.d(APP_TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);

        return file;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // by this point we have the camera photo on disk
                bitmapPostImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                // RESIZE BITMAP, see section below
                BitmapScaler.scaleToFitWidth(bitmapPostImage, 300);
                BitmapScaler.scaleToFitHeight(bitmapPostImage, 100);

                // Load the taken image into a preview
                ivPostImage.setImageBitmap(bitmapPostImage);
            } else { // Result was a failure
                Toast.makeText(getActivity(), "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }
    }



    // TODO
    private void createPost(String caption, ParseFile image, ParseUser user, String location) {
        final com.example.jdocter.parstagram.model.Post newPost = new com.example.jdocter.parstagram.model.Post();
        newPost.setDescription(caption);
        //newPost.setImage(image);
        newPost.setUser(user);
        newPost.setImage(image);
        newPost.setLocationKey(location);

        newPost.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e==null) {
                    Log.d("MainActivity", "Post success");
                    callback.onPostCreated();
                } else {
                    Log.e("MainActivity", "Post Failure");
                    e.printStackTrace();
                }
            }
        });
    }
}
