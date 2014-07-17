package com.patricklove.game_8;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends ActionBarActivity {

    private SoundPool pool;
    private AudioManager man;
    private int EIGHT_ID = 0;
    private int streamNum = 0;
    private TimerTask currentRemove8;
    private Timer time = new Timer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        man = (AudioManager) getSystemService(MainActivity.AUDIO_SERVICE);
        EIGHT_ID = pool.load(this, R.raw.eight, 1);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    public void buttonPressed(View v){
        View view = findViewById(R.id.textView);
        view.setVisibility(View.VISIBLE);
        playSound();
        if(currentRemove8 != null){
            currentRemove8.cancel();
        }
        currentRemove8 = getRemove8();
        time.schedule(currentRemove8, 500);
    }

    private void playSound() {
        pool.stop(streamNum);
        float orgVolume = man.getStreamVolume(AudioManager.STREAM_MUSIC);
        float maxVolume = man.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        float volume = orgVolume/maxVolume;
        streamNum = pool.play(EIGHT_ID, volume, volume, 1, 0, 1);
    }

    private TimerTask getRemove8(){
        return new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        View v = findViewById(R.id.textView);
                        v.setVisibility(View.INVISIBLE);
                    }
                });
            }
        };
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_main, container, false);
        }
    }

}
