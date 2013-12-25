package ws.base.mylibonline;




import ws.base.modeles.Book;
import ws.base.mylibonline.utils.Params;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

/**
 * Activity that displays a particular news article onscreen.
 *
 * This activity is started only when the screen is not large enough for a two-pane layout, in
 * which case this separate activity is shown in order to display the news article. This activity
 * kills itself if the display is reconfigured into a shape that allows a two-pane layout, since
 * in that case the news article will be displayed by the {@link Home} and this
 * Activity therefore becomes unnecessary.
 */
public class ArticleActivity extends FragmentActivity {
	private String TAG_LOCAL = "ArticleActivity - ";
	private boolean fgDebugLocal = true;
    private int bookIndex = 0;
    

    /**
     * Sets up the activity.
     *
     * Setting up the activity means reading the category/article index from the Intent that
     * fired this Activity and loading it onto the UI. We also detect if there has been a
     * screen configuration change (in particular, a rotation) that makes this activity
     * unnecessary, in which case we do the honorable thing and get out of the way.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	bookIndex = getIntent().getExtras().getInt("bookIndex", 0);
    	if (Params.TAG_FG_DEBUG && fgDebugLocal){Log.i(Params.TAG_GEN, TAG_LOCAL + "bookIndex = " + bookIndex);};
        // If we are in two-pane layout mode, this activity is no longer necessary
        if (getResources().getBoolean(R.bool.has_two_panes)) {
            finish();
            return;
        }
        
        // Place an ArticleFragment as our content pane
        ArticleFragment articleFragment = new ArticleFragment();
        getSupportFragmentManager().beginTransaction().add(android.R.id.content, articleFragment).commit();
        if (Params.TAG_FG_DEBUG && fgDebugLocal){Log.i(Params.TAG_GEN, TAG_LOCAL + "bookIndex = " + bookIndex);};
        Book book = BaseActivity.listBook.get(bookIndex);
        book.afficheData();
        articleFragment.displayArticle(book);
        
    }
    
    
}
