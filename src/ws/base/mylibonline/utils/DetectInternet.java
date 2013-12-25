package ws.base.mylibonline.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class DetectInternet {
	private String TAG_LOCAL = "DetectInternet - ";
	private boolean fgDebugLocal = false;
    
	private Context context;
    
	public DetectInternet(Context context){
		this.context = context;
	}

	public boolean isConnectingToInternet(){
		ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null){ 
				for (int i = 0; i < info.length; i++){ 
					if (info[i].getState() == NetworkInfo.State.CONNECTED){
						if (Params.TAG_FG_DEBUG && fgDebugLocal){Log.i(Params.TAG_GEN, TAG_LOCAL + "ConnectivityManager connected ");};
						return true;
					}
				}
			}
		}
		
		return false;
	}
}