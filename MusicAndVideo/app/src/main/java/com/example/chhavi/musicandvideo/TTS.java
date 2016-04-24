package com.example.chhavi.musicandvideo;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Locale;

/**
 * Created by chhavi on 23/4/16.
 */
public class TTS extends AppCompatActivity implements TextToSpeech.OnInitListener {

    TextToSpeech tts;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tts = new TextToSpeech(getApplicationContext(), this);
    }

    @Override
    public void onInit(int status) {
        if(status == TextToSpeech.SUCCESS){

            int result = tts.setLanguage(Locale.US);
            if(result == TextToSpeech.LANG_MISSING_DATA ||
                    result == TextToSpeech.LANG_NOT_SUPPORTED){
                //display error message
            }else{
         /*       HashMap<String, String> params = new HashMap<String, String>();
                params.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "speech1");
                tts.speak("Checking Text to Speech",TextToSpeech.QUEUE_FLUSH, params);



                HashMap<String, String> newParams = new HashMap<String, String>();
                newParams.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "speech2");
                tts.speak("Checking Text to Speech part 2",TextToSpeech.QUEUE_ADD, newParams);
*/
                tts.setOnUtteranceCompletedListener(new TextToSpeech.OnUtteranceCompletedListener() {
                    @Override
                    public void onUtteranceCompleted(String utteranceId) {
                        if(utteranceId == "speech1"){

                        }else if(utteranceId == "speech2"){

                        }

                    }
                });
              //  speak("asdas")
            }


        }else{
            //display error message
        }

    }

    private void speak(String string){

    }
}
