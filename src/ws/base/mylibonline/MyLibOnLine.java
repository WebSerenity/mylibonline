package ws.base.mylibonline;



import ws.base.mylibonline.utils.Params;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

public class MyLibOnLine extends Activity {
	private String TAG_LOCAL = "MyLibOnLine - ";
	private boolean fgDebugLocal = true;
	
	private ImageView splash;
	private Animation anim;
	private ProgressDialog progressDialog;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash);
        if (Params.TAG_FG_DEBUG && fgDebugLocal){Log.i(Params.TAG_GEN, TAG_LOCAL + "Start splash");};
        Intent intent = new Intent(MyLibOnLine.this,Home.class);
        startActivity(intent);
        finish();
        
        /*
        splash = (ImageView) findViewById(R.id.ivSplash);
        anim = AnimationUtils.loadAnimation(this, R.anim.splash_anim);
        splash.setAnimation(anim);
        splash.startAnimation(anim);
        
       
        anim.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// nothing todo
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// nothing todo
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				if (Params.TAG_FG_DEBUG){Log.i(Params.TAG_GEN, TAG_LOCAL + "End anim");};

				Intent intent = new Intent(MyLibOnLine.this,Home.class);
		        startActivity(intent);
		        finish();

			}
		});
        */
    }
    
    
    
    
}