package com.rubik.platzigram.Fragments;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rubik.platzigram.Adapter.PictureAdapterRecyclerView;
import com.rubik.platzigram.Model.Picture;
import com.rubik.platzigram.R;
import com.rubik.platzigram.post.PostActivity;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private FloatingActionButton fabAgregar;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private String currentPhotoPath = "";

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            // Inflate the layout for this fragment
            View view = inflater.inflate(R.layout.fragment_home, container, false);
            showToolbar("Home", false, view);

            //Hace referencia a recycler view que está en el layout
            RecyclerView pictureRecycler = (RecyclerView) view.findViewById(R.id.pictureRecycler);

            //Se coloca un LinearLayoutManager al recyclerview
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            pictureRecycler.setLayoutManager(linearLayoutManager);

            //Adapter
            ArrayList<Picture> listaImagenes = construirListaImagenes();
            Log.d("onCreateView: ", Integer.toString(listaImagenes.size()));
            PictureAdapterRecyclerView pictureAdapterRecyclerView = new PictureAdapterRecyclerView(listaImagenes, R.layout.cardview_picture, getActivity());

            //Se le pasa el adapter al recyclerview
            pictureRecycler.setAdapter(pictureAdapterRecyclerView);

            //Click del floating button
            fabAgregar = (FloatingActionButton) view.findViewById(R.id.fabAgregar);
            fabAgregar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tomarFoto();
                }
            });


            return view;
        } catch (Exception ex) {
            Log.d("Error onCreate", ex.toString());
            return null;
        }


    }

    private void tomarFoto() {
        Intent intentTakePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentTakePicture.resolveActivity(getActivity().getPackageManager()) != null) {

            File photofile = null;
            try {
                photofile = crearArchivoImagen();
            } catch (Exception e) {
                e.printStackTrace();
            }
            //Si el archivo se creo correctamente
            if (photofile != null) {
                Uri photoURI = FileProvider.getUriForFile(getContext(), "com.rubik.platzigram",photofile);
                intentTakePicture.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(intentTakePicture, REQUEST_IMAGE_CAPTURE);
            }

        }
    }

    private File crearArchivoImagen() throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File photo = File.createTempFile(imageFileName, ".jpg", storageDir);
        currentPhotoPath =  photo.getAbsolutePath();
        return photo;


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == getActivity().RESULT_OK) {
            Log.d("HomeFragment", "Camera OK!");
            Intent i = new Intent(getActivity(), PostActivity.class);
            i.putExtra("PHOTO_PATH_TEMP", currentPhotoPath);
            startActivity(i);

                   }
    }

    public ArrayList<Picture> construirListaImagenes() {
        ArrayList<Picture> pictures = new ArrayList<>();
        pictures.add(new Picture("http://www.novalandtours.com/images/guide/guilin.jpg", "Ardani Ramírez", "4 días", "3"));
        pictures.add(new Picture("http://www.novalandtours.com/images/guide/guilin.jpg", "Danilo Itzep", "3 días", "6"));
        pictures.add(new Picture("http://www.novalandtours.com/images/guide/guilin.jpg", "Silvia Mendoza", "1 días", "1"));
        pictures.add(new Picture("http://www.novalandtours.com/images/guide/guilin.jpg", "Angel Orozco", "2 días", "0"));
        return pictures;
    }

    //Muestra toolbar
    public void showToolbar(String tittle, boolean showUpButton, View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        //((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(tittle);
        //((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(showUpButton);
    }

}
