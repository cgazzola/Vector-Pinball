package com.dozingcatsoftware.bouncy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static com.dozingcatsoftware.bouncy.R.id.scoreView;


/**
 * Created by devmachine on 4/29/17.
 */

public class TopScoreActivity extends Activity {


    ArrayList <Long> highScores;
    ListView listView;
    ArrayAdapter<String> adapter;
    Boolean hasScores;
    int numZeros = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topscores);

        hasScores = getIntent().getExtras().getBoolean("hasScores");
        TextView displayInfo = (TextView) findViewById(R.id.displayInfo);


        if(hasScores == true){
            highScores = (ArrayList<Long>) getIntent().getSerializableExtra("highScores");

            numZeros = countNullScores(highScores);

            displayInfo.setText(R.string.has_board_scores);
            int size = highScores.size() - numZeros;
            int ranking = 1;
            String[] stringHighScores = new String[size];
            for(int i=0; i<size; i++){
                stringHighScores[i] = Integer.toString(ranking) + ".) " + Long.toString(highScores.get(i));
                ranking++;
            }

            listView = (ListView) findViewById(R.id.list_view);
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stringHighScores);
            listView.setAdapter(adapter);

        }

        else{

            displayInfo.setText(R.string.no_board_scores);

        }



    }


    public static Intent startForLevel(Context context, List <Long> highScores, Boolean hasScores) {
        Intent topScoreIntent = new Intent(context, TopScoreActivity.class);
        if(hasScores){
            ArrayList<Long> scores = (ArrayList<Long>) highScores;
            topScoreIntent.putExtra("highScores", scores);
            topScoreIntent.putExtra("hasScores", hasScores);
        }
        else{
            topScoreIntent.putExtra("hasScores", hasScores);
        }
        context.startActivity(topScoreIntent);
        return topScoreIntent;
    }

    public static int countNullScores(ArrayList <Long> scores){

        int numZeros =0;
        for(int i=0; i<scores.size(); i++){
            if(scores.get(i) == 0){
                numZeros++;
            }
        }

        return numZeros;
    }
}