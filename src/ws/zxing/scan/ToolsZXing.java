package ws.zxing.scan;

import ws.base.mylibonline.utils.Params;
import android.util.Log;

public class ToolsZXing {
	private static String TAG_LOCAL = "ToolsZXing - ";
	private static boolean fgDebugLocal = true;
	
	public static String getEAN(String str){
		if (Params.TAG_FG_DEBUG && fgDebugLocal){Log.i(Params.TAG_GEN, TAG_LOCAL + "strISBN = " + str);};
		String strEAN = "";
		
		return strEAN;
	}

}
