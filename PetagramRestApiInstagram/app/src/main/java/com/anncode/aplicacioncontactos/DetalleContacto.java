package com.anncode.aplicacioncontactos;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.anncode.recyclerviewfragments.R;
import com.squareup.picasso.Picasso;

public class DetalleContacto extends AppCompatActivity {

    private static final String KEY_EXTRA_URL = "url";
    private static final String KEY_EXTRA_LIKES = "like";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_contacto_foto);

        Bundle extras = getIntent().getExtras();
        String url   = extras.getString(KEY_EXTRA_URL);
        int likes    = extras.getInt(KEY_EXTRA_LIKES);


        TextView tvLikesDetalle = (TextView) findViewById(R.id.tvLikesDetalle);
        if (tvLikesDetalle != null) {
            tvLikesDetalle.setText(String.valueOf(likes));
        }

        ImageView imgFotoDetalle = (ImageView) findViewById(R.id.imgFotoDetalle);
        Picasso.with(this)
                .load(url)
                .placeholder(R.drawable.shock_rave_bonus_icon)
                .into(imgFotoDetalle);

    }
}
