package edu.calpoly.android.lab2;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.inputmethodservice.Keyboard;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.w3c.dom.Text;

public class SimpleJokeList extends Activity{

	/**
	 * Contains the list Jokes the Activity will present to the user.
	 */
	protected ArrayList<Joke> m_arrJokeList;

	/**
	 * LinearLayout used for maintaining a list of Views that each display Jokes.
	 */
	protected LinearLayout m_vwJokeLayout;

	/**
	 * EditText used for entering text for a new Joke to be added to m_arrJokeList.
	 */
	protected EditText m_vwJokeEditText;

	/**
	 * Button used for creating and adding a new Joke to m_arrJokeList using the
	 * text entered in m_vwJokeEditText.
	 */
	protected Button m_vwJokeButton;


	/**
	 * Background Color values used for alternating between light and dark rows
	 * of Jokes.
	 */
	protected int m_nDarkColor;
	protected int m_nLightColor;
	protected int m_nTextColor;
	/**
	 * ATTENTION: This was auto-generated to implement the App Indexing API.
	 * See https://g.co/AppIndexing/AndroidStudio for more information.
	 */
	private GoogleApiClient client;
	static int count = 0;

	public static final String TAG = SimpleJokeList.class.getSimpleName();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initLayout();
		m_arrJokeList = new ArrayList<Joke>();
		String[] jokeList = this.getResources().getStringArray(R.array.jokeList);
		for (int i = 0; i < jokeList.length; i++) {
			addJoke(jokeList[i]);
		}
        initAddJokeListeners();
	}

	/**
	 * Method used to encapsulate the code that initializes and sets the Layout
	 * for this Activity.
	 */
	protected void initLayout() {
		m_vwJokeLayout = new LinearLayout(this);
		m_vwJokeLayout.setOrientation(LinearLayout.VERTICAL);
		m_nDarkColor = this.getResources().getColor(R.color.dark);
		m_nLightColor = this.getResources().getColor(R.color.light);
		m_nTextColor = this.getResources().getColor(R.color.text);
		ScrollView sView = new ScrollView(this);
		sView.addView(m_vwJokeLayout);

		LinearLayout viewGroup = new LinearLayout(this);
		viewGroup.setOrientation(LinearLayout.VERTICAL);
		LinearLayout addJokeButtonView = new LinearLayout(this);
		addJokeButtonView.setOrientation(LinearLayout.HORIZONTAL);
		m_vwJokeButton = new Button(this);
		m_vwJokeButton.setText("Add Joke");
        addJokeButtonView.addView(m_vwJokeButton);
		m_vwJokeEditText = new EditText(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		m_vwJokeEditText.setLayoutParams(layoutParams);
		m_vwJokeEditText.setInputType(InputType.TYPE_CLASS_TEXT);
        addJokeButtonView.addView(m_vwJokeEditText);
        viewGroup.addView(addJokeButtonView);
        viewGroup.addView(sView);
		setContentView(viewGroup);

	}

	/**
	 * Method used to encapsulate the code that initializes and sets the Event
	 * Listeners which will respond to requests to "Add" a new Joke to the
	 * list.
	 */
	protected void initAddJokeListeners() {
        m_vwJokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addJoke(m_vwJokeEditText.getText().toString());
                m_vwJokeEditText.setText("");
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(m_vwJokeEditText.getWindowToken(), 0);
            }
        });
        //EditText editText = new EditText(this);
        m_vwJokeEditText.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    if(event.getAction() == KeyEvent.ACTION_DOWN) {
                        addJoke(m_vwJokeEditText.getText().toString());
                        m_vwJokeEditText.setText("");
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(m_vwJokeEditText.getWindowToken(), 0);
                    }
                    return true;
                } else if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
                    if(event.getAction() == KeyEvent.ACTION_DOWN) {
                        addJoke(m_vwJokeEditText.getText().toString());
                        m_vwJokeEditText.setText("");
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(m_vwJokeEditText.getWindowToken(), 0);
                        return true;
                    }
                }
                return false;
            }
            });
    }

	/**
	 * Method used for encapsulating the logic necessary to properly initialize
	 * a new joke, add it to m_arrJokeList, and display it on screen.
	 *
	 * @param strJoke A string containing the text of the Joke to add.
	 */
	protected void addJoke(String strJoke) {

		Joke joke = new Joke();
		joke.setJoke(strJoke);
		m_arrJokeList.add(joke);
		Log.d(TAG, "addJoke() called with: " + "strJoke = [" + strJoke + "]");
		TextView tView = new TextView(this);
		tView.setText(strJoke);
		tView.setTextSize(16);
		tView.setTextColor(m_nTextColor);
        if(count % 2 == 0){
			tView.setBackgroundColor(m_nDarkColor);
		}
		else{
			tView.setBackgroundColor(m_nLightColor);
		}
		m_vwJokeLayout.addView(tView);
		count++;

	}

	@Override
	public void onStart() {
		super.onStart();

		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		//client.connect();
		Action viewAction = Action.newAction(
				Action.TYPE_VIEW, // TODO: choose an action type.
				"SimpleJokeList Page", // TODO: Define a title for the content shown.
				// TODO: If you have web page content that matches this app activity's content,
				// make sure this auto-generated web page URL is correct.
				// Otherwise, set the URL to null.
				Uri.parse("http://host/path"),
				// TODO: Make sure this auto-generated app URL is correct.
				Uri.parse("android-app://edu.calpoly.android.lab2/http/host/path")
		);
//		AppIndex.AppIndexApi.start(client, viewAction);
	}

	@Override
	public void onStop() {
		super.onStop();

		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		Action viewAction = Action.newAction(
				Action.TYPE_VIEW, // TODO: choose an action type.
				"SimpleJokeList Page", // TODO: Define a title for the content shown.
				// TODO: If you have web page content that matches this app activity's content,
				// make sure this auto-generated web page URL is correct.
				// Otherwise, set the URL to null.
				Uri.parse("http://host/path"),
				// TODO: Make sure this auto-generated app URL is correct.
				Uri.parse("android-app://edu.calpoly.android.lab2/http/host/path")
		);
//		AppIndex.AppIndexApi.end(client, viewAction);
		//client.disconnect();
	}
}