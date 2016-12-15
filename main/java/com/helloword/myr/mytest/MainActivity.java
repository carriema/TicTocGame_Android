package com.helloword.myr.mytest;


        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.text.Layout;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.TextView;
        import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    int put = 0;
    int[] gameState = {2,2,2,2,2,2,2,2,2};
    boolean gameEnd = false;
    protected void clickFunction(View view) {
        clean();
        ((ImageView) findViewById(R.id.imageView)).animate().alpha(0f).setDuration(1000);
        ((LinearLayout) findViewById(R.id.layout)).setTranslationX(-1000f);

    }
    protected void clean() {
        ((ImageView) findViewById(R.id.imageView0)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView1)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView2)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView3)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView4)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView5)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView6)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView7)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView8)).setImageResource(0);
        put = 0;
        for (int i = 0; i < gameState.length; i++) {
            gameState[i] = 2;
        }
        gameEnd = false;
    }
    protected boolean isEnd() {
        for (int i = 0; i < gameState.length; i++) {
            if (gameState[i] != 2) return false;
        }
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout layout = (LinearLayout) findViewById(R.id.layout);
        layout.setTranslationX(-1000f);
    }
    protected void select(View view) {
        Log.i("this", "clicked");

        ImageView image = (ImageView) view;
            //0 means not play, 1 means yellow, 0 means red.
//        Log.i("tag", image.getTag().toString());
//        Log.i("gameState", gameState[Integer.parseInt(image.getTag().toString())]+"");
            int tappedPlace = Integer.parseInt(image.getTag().toString());
//        Log.i("tappedPlace", gameState[tappedPlace] +"");
            if (gameState[tappedPlace] != 2) {
//            Log.i("gameState=2", "here");
                Toast.makeText(MainActivity.this, "Cannot put in this place", Toast.LENGTH_SHORT).show();
                return;
            } else {
//            Log.i("gameState!=2", "here");
                gameState[tappedPlace] = put;
//            Log.i("gameState",gameState[tappedPlace]+"");
                image.setTranslationY(-1000f);
                if (put == 0) {
                    image.setImageResource(R.drawable.red);
                    put = 1;
                } else {
                    image.setImageResource(R.drawable.yellow);
                    put = 0;
                }
                image.animate().translationYBy(1000f).rotation(3600).setDuration(300);
            }
            Log.i("win", win(tappedPlace, gameState[tappedPlace]) + "");
            gameEnd = win(tappedPlace, gameState[tappedPlace]);
        if (gameEnd || isEnd()) {
            ImageView background = (ImageView) findViewById(R.id.imageView);
            background.animate().alpha(1f).setDuration(1000);
            TextView text = (TextView) findViewById(R.id.textView);
            text.setText("Winner is " + (put == 0 ? "Yello" : "Red"));
            LinearLayout layout = (LinearLayout) findViewById(R.id.layout);
            layout.animate().translationXBy(1000f).setDuration(1000);
            return;
        }

    }
    protected boolean win(int tapped, int num) {
        int row = tapped / 3;
        int col = tapped % 3;
        System.out.println(col +", " + row + "/ " + num);
        boolean winCol = true;
        boolean winRow = true;
        boolean winZheng = true;
        boolean winFan = true;
        for (int i = 0; i < 3; i++) {
            winRow = winRow && gameState[row * 3 + i] == num;
            winCol = winCol && gameState[i * 3 + col] == num;
        }
        Log.i("winRow", winRow+"");
        Log.i("winCOl", winCol+"");
        if (col == row || col + row == 2) {
            for (int i = 0; i < 3; i++) {
                winZheng = winZheng && gameState[i * 3 + i] == num;
                winFan = winFan && gameState[i * 3 + (2 - i)] == num;
            }
        } else {
            winZheng = false;
            winFan = false;
        }
        Log.i("winZheng",winZheng+"");
        Log.i("winFan",winFan +"");
        return winCol || winRow || winZheng || winFan;
    }

}