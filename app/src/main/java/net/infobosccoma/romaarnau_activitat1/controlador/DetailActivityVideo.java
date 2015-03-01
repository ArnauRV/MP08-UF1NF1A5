package net.infobosccoma.romaarnau_activitat1.controlador;

/**
 * Created by Arnau on 13/02/2015.
 */

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import net.infobosccoma.romaarnau_activitat1.R;
import net.infobosccoma.romaarnau_activitat1.model.Cursos;

public class DetailActivityVideo extends YouTubeBaseActivity implements
        YouTubePlayer.OnInitializedListener {

    private static Cursos dades;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView infTextView;

        setContentView(R.layout.activity_detail_videos);

        dades = (Cursos)getIntent().getExtras().getSerializable("cursos");

        infTextView = (TextView) findViewById(R.id.tvTitolVideo);
        infTextView.setText(dades.getNom());

        infTextView = (TextView) findViewById(R.id.tvDescripcioVideo);
        infTextView.setText(dades.getDescripcio());

        YouTubePlayerView youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_player);
        youTubeView.initialize(DeveloperKey.DEVELOPER_KEY, this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player,
                                        boolean wasRestored) {
        if (!wasRestored) {
            player.cueVideo(dades.getVideo());
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(this, "Oh no! " + youTubeInitializationResult.toString(),
                Toast.LENGTH_LONG).show();
    }
}

