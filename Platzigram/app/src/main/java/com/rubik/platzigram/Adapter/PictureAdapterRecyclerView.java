package com.rubik.platzigram.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rubik.platzigram.Model.Picture;
import com.rubik.platzigram.PictureDetailActivity;
import com.rubik.platzigram.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by lramirez on 18/07/2017.
 */

public class PictureAdapterRecyclerView extends RecyclerView.Adapter<PictureAdapterRecyclerView.PictureViewHolder> {
    //3. heredar RecyclerView.Adapter<PictureAdapterRecyclerView.PictureViewHolder>

    //5. instanciar lista de modelos
    private ArrayList<Picture> pictures;
    private int resource;
    private Activity activity;

    //6. Crear Constructor de la clase exterior Alt + Insert
    public PictureAdapterRecyclerView(ArrayList<Picture> pictures, int resource, Activity activity) {
        this.pictures = pictures;
        this.resource = resource;
        this.activity = activity;
    }

    //4. sobreEscribir los tres métodos
    @Override
    public PictureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //7. convierte el código xml del cardView en un objeto de java
        View view = LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);
        return new PictureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PictureViewHolder holder, int position) {
        //8. Se trabaja con la lista de objetos picture que se declaró en la parte superior
        //por cada elemento de la lista coloca los atributos a los objetos del holder.
        Picture picture = pictures.get(position);

        holder.usernameCard.setText(picture.getUserName());
        holder.likeNumberCard.setText(picture.getLikeNumber());
        holder.timeCard.setText(picture.getTime());
        //Imagen con picasso ¡No olvidad agregar permiso de internet en manifest!
        Picasso.with(activity).load(picture.getPicture()).into(holder.pictureCard);

        //10. Aplicar listener para onclick para disparar intent
            holder.pictureCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentDetailActivity = new Intent(activity, PictureDetailActivity.class);
                    //Valida si la version de android es >= lollipop
                    if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP){
                        //Coloca transision Explode para la salida
                        Explode exp = new Explode();
                        exp.setDuration(1000);
                        activity.getWindow().setExitTransition(exp);
                        activity.startActivity(intentDetailActivity,
                                //Ejecutar intent con transicion
                                ActivityOptionsCompat.makeSceneTransitionAnimation(activity,v,activity.getString(R.string.transitionname_picture)).toBundle());

                    }else{
                        //ejecutar intent sin transicion
                        activity.startActivity(intentDetailActivity);
                    }
                        }
            });
    }

    @Override
    public int getItemCount() {
        //9. Retornar la cantidad "count" de elementos que se mostrarán
        return pictures.size();
    }

    //1.Crear Clase inner
    public static class PictureViewHolder extends RecyclerView.ViewHolder{
        //2. Crear objeto para asociar con los controles de usuario en la vista
        private ImageView pictureCard;
        private TextView usernameCard;
        private TextView timeCard;
        private TextView likeNumberCard;

        public PictureViewHolder(View itemView) {
            super(itemView);
            //Asginar controles de usuario a objetos java
            pictureCard = (ImageView) itemView.findViewById(R.id.pictureCardImg);
            usernameCard = (TextView) itemView.findViewById(R.id.tvUserNameCard);
            timeCard = (TextView) itemView.findViewById(R.id.tvTimeCard);
            likeNumberCard = (TextView) itemView.findViewById(R.id.tvTimeCard);


        }
    }
}
