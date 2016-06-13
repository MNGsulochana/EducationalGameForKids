package com.example.educationalgameforkids;
import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.Delayed;

import javax.security.auth.Destroyable;

import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.speech.tts.TextToSpeech.OnUtteranceCompletedListener;
import android.support.v7.app.ActionBarActivity;
import android.text.format.Time;
import android.util.Base64;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.SoundEffectConstants;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.R.bool;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.AssetManager.AssetInputStream;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.media.audiofx.AudioEffect.Descriptor;
import android.opengl.Visibility;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore.Audio;
import android.provider.OpenableColumns;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;


public class MainActivity extends ActionBarActivity {
	Calendar calendar;
	int hour,minute;
	AssetManager assetManager;
	CustomDialogFragment cf;
	LinearLayout l1, l2, l3;
	FrameLayout f1;
	Button b1, b2, b3, b4;
	Gallery ga;
	GalleryAdapter gadapter;
	TextToSpeech tts;
	TextView t1, t2, t3;
	ImageView i1, i2, i3, i4, i5, i6;
	int pos,pos1;
	int cnt = 1;
	int counter = 0;
	int count = 1;
	int w1, h1;
	int width, height;
	Spinner s;
	String kid_name,s1;
	ArrayAdapter<String> aa;
	Bitmap bmp;
	private String TAG="SUCHI";
	float initialX,initialY;

//HERE I ADDED PLANET ARRAY AND ADD ELEMENT IN ALPHABET ARRAY
	int[] welcome={R.drawable.welcome};
	int[] galleryposition = {R.drawable.gallery1, R.drawable.gallery2, R.drawable.colorspage, R.drawable.shapespage,
			R.drawable.monthspage, R.drawable.dayspage, R.drawable.fruitspage, R.drawable.vegetablespage, R.drawable.animalspage,
			R.drawable.bodypartspage, R.drawable.vehiclespage,R.drawable.planet_13,R.drawable.numberquiz, R.drawable.gallery4};
	int[] alphabets = {R.drawable.gallery1, R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.dog, R.drawable.e, R.drawable.f,
			R.drawable.g, R.drawable.h, R.drawable.i, R.drawable.j, R.drawable.k, R.drawable.l,
			R.drawable.m, R.drawable.n, R.drawable.o, R.drawable.p, R.drawable.q, R.drawable.r,
			R.drawable.s, R.drawable.t, R.drawable.u, R.drawable.v, R.drawable.w,
			R.drawable.x, R.drawable.y, R.drawable.z};
	int[] numbers = {R.drawable.gallery2, R.drawable.one, R.drawable.two, R.drawable.three, R.drawable.four, R.drawable.five,
			R.drawable.six, R.drawable.seven, R.drawable.eight, R.drawable.nine, R.drawable.ten};
	int[] colors = {R.drawable.colorspage, R.drawable.red, R.drawable.orange, R.drawable.blue, R.drawable.green, R.drawable.yellow, R.drawable.pink,
			R.drawable.violet, R.drawable.brown, R.drawable.grey, R.drawable.white, R.drawable.black,
			R.drawable.indigo};
	int[] shapes = {R.drawable.shapespage, R.drawable.circle, R.drawable.line, R.drawable.square, R.drawable.triangle, R.drawable.rectangle,
			R.drawable.pentagon, R.drawable.hexagon, R.drawable.octagon, R.drawable.heart, R.drawable.oval,
			R.drawable.arrow, R.drawable.star};
	int[] months = {R.drawable.monthspage, R.drawable.january, R.drawable.february, R.drawable.march, R.drawable.april,
			R.drawable.may, R.drawable.june, R.drawable.july, R.drawable.august, R.drawable.september,
			R.drawable.october, R.drawable.november, R.drawable.december};
	int[] days = {R.drawable.dayspage, R.drawable.sunday, R.drawable.monday, R.drawable.tuesday, R.drawable.wednesday,
			R.drawable.thurs, R.drawable.fri, R.drawable.saturday};
	int[] fruits = {R.drawable.fruitspage, R.drawable.apricot, R.drawable.apple, R.drawable.aubergine, R.drawable.avocado,
			R.drawable.banana, R.drawable.blackberry, R.drawable.blueberry, R.drawable.cherry, R.drawable.coconut,
			R.drawable.cranberry, R.drawable.grapes, R.drawable.grapefruit, R.drawable.kiwi,
			R.drawable.lemon, R.drawable.lime, R.drawable.mandarin1, R.drawable.mango, R.drawable.melon,
			R.drawable.orangefruit, R.drawable.papaya, R.drawable.peach, R.drawable.pear, R.drawable.pineapple,
			R.drawable.plum, R.drawable.pomegranate, R.drawable.pomelo, R.drawable.quince, R.drawable.raspberry,
			R.drawable.strawberry, R.drawable.watermelon};
	int[] vegetables = {R.drawable.vegetablespage, R.drawable.asparagus1, R.drawable.beans, R.drawable.beetroot, R.drawable.bittergourd,
			R.drawable.brinjal2, R.drawable.broccoli, R.drawable.cabbage, R.drawable.capsicum,
			R.drawable.carrot, R.drawable.cauliflower1, R.drawable.celery1, R.drawable.chilli, R.drawable.corn,
			R.drawable.cucumber, R.drawable.eggplant, R.drawable.garlic, R.drawable.ginger,
			R.drawable.greenpea, R.drawable.ladyfinger2, R.drawable.mushrooms, R.drawable.onion1,
			R.drawable.potato1, R.drawable.pumpkin1, R.drawable.raddish, R.drawable.spinach,
			R.drawable.squash, R.drawable.sweetpotato, R.drawable.tomato};
	int[] animals = {R.drawable.animalspage, R.drawable.armadillo, R.drawable.bat, R.drawable.bear, R.drawable.bird, R.drawable.butterfly,
			R.drawable.cat, R.drawable.chicken2, R.drawable.cow, R.drawable.crocodile, R.drawable.doggg, R.drawable.duck,
			R.drawable.elephant, R.drawable.fish, R.drawable.fly, R.drawable.fox, R.drawable.frog, R.drawable.horse,
			R.drawable.jaguar, R.drawable.lamb, R.drawable.lion, R.drawable.lizard, R.drawable.monkey,
			R.drawable.mouse1, R.drawable.pandabear, R.drawable.parrot, R.drawable.polarbear,
			R.drawable.pony, R.drawable.rabbit, R.drawable.shark, R.drawable.sheep1, R.drawable.snail, R.drawable.snake,
			R.drawable.spider, R.drawable.squirrel3, R.drawable.tiger, R.drawable.tortoise2,
			R.drawable.wolf, R.drawable.zebra1};
	int[] bodyparts = {R.drawable.bodypartspage, R.drawable.eye, R.drawable.head, R.drawable.mouth, R.drawable.ear, R.drawable.hair,
			R.drawable.arm, R.drawable.hand2, R.drawable.finger, R.drawable.thumb, R.drawable.leg,
			R.drawable.foot1, R.drawable.nose1, R.drawable.eyebrow, R.drawable.eyelashes,
			R.drawable.forehead, R.drawable.lips1, R.drawable.teeth, R.drawable.tongue,
			R.drawable.neck, R.drawable.chest, R.drawable.stomach, R.drawable.back,
			R.drawable.shoulders, R.drawable.knees};
	int[] vehicles = {R.drawable.vehiclespage, R.drawable.bus, R.drawable.bicycle, R.drawable.car, R.drawable.helicopter, R.drawable.motorcycle, R.drawable.plane,
			R.drawable.ship, R.drawable.tractor, R.drawable.train, R.drawable.truck, R.drawable.tram};
	int[] planets={R.drawable.planet_13,R.drawable.mercury,R.drawable.venus,R.drawable.earth,R.drawable.mars,
			       R.drawable.jupiter,R.drawable.saturn,R.drawable.uranus_1,R.drawable.neptune,R.drawable.pluto_1};
	int[] numberquiz = {R.drawable.numberquiz, R.drawable.cirlcle1, R.drawable.cirlcle2, R.drawable.cirlcle3, R.drawable.cirlcle4,
			R.drawable.cirlcle5, R.drawable.cirlcle6, R.drawable.cirlcle7, R.drawable.cirlcle8, R.drawable.cirlcle9,
			R.drawable.cirlcle10};

	String[] gallerypositiontext = {"Alphabets", "Numbers", "Colors", "shapes", "Months", "Days", "fruits", "vegetables", "animals",
			"Human Body parts", "Vehicles", "Planets","NumberQuiz", "Letter Quiz"};
	String[] alphabetstext = {"Alphabets", "apple", "ball", "cat", "dog", "egg", "fish", "guitar", "horse", "insect", "jam",
			"kangaroo", "ladybird", "monkey", "nurse", "octopus", "pig", "queen", "rainbow", "sun", "tiger",
			"umbrella", "violin", "window", "x ray", "yacht", "zebra"};
	String[] numberstext = {"Numbers","one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten"};
	String[] colorstext = {"Colors", "Red", "Orange", "Blue", "Green", "Yellow", "pink", "Violet", "Brown", "Grey", "White",
			"Black", "indigo"};
	String[] shapestext = {"Shapes", "Circle", "Line", "Square", "Triangle", "Rectangle", "Pentagon", "Hexagon", "Octagon", "Heart",
			"Oval", "Arrow", "Star"};
	String[] monthstext = {"Months", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	String[] daystext = {"Days", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
	String[] fruitstext = {"Fruits", "apricot", "apple", "aubergine", "avocado", "banana", "blackberry", "blueberry", "cherry", "coconut", "cranberry", "grapes", "grapefruit", "kiwi",
			"lemon", "lime", "mandarin", "mango", "melon", "orange", "papaya", "peach", "pear", "pineapple", "plum", "pomegranate", "pomelo", "quince", "raspberry", "strawberry", "watermelon"};
	String[] vegetablestext = {"vegetables", "asparagus", "beans", "beetroot", "bittergourd", "brinjal", "broccoli", "cabbage", "capsicum", "carrot", "cauliflower", "celery", "chilli", "corn", "cucumber",
			"eggplant", "garlic", "ginger", "greenpea", "ladyfinger", "mushroom", "onion", "potato", "pumpkin", "radish", "spinach", "squash", "sweetpotato", "tomato"};
	String[] animalstext = {"Animals", "armadillo", "bat", "bear", "bird", "butterfly", "cat", "chicken", "cow", "crocodile", "dog", "duck", "elephant", "fish", "fly", "fox", "frog", "horse", "jaguar", "lamb", "lion"
			, "lizard", "monkey", "mouse", "pandabear", "parrot", "polarbear", "pony", "rabbit", "shark", "sheep", "snail", "snake", "spider", "squirrel", "tiger", "tortoise",
			"wolf", "zebra"};
	String[] bodypartstext = {"Human Body Parts", "eye", "head", "mouth", "ear", "hair", "arm", "hand", "finger", "thumb", "leg", "foot", "nose", "eyebrow", "eyelashes", "forehead", "lips", "teeth", "tongue",
			"neck", "chest", "stomach", "back", "shoulders", "knees"};
	String[] vehiclestext = {"Vehicles", "bus", "bicycle", "car", "helicopter", "motorcycle", "plane", "ship", "tractor", "train", "truck", "tram"};
	String[] planetstext={"Planets","Mercury","Venus","Earth","Mars","Jupiter","Saturn","Uranus","Neptune","Pluto"};
	String[] numberquiztext = {"Number Quiz", "Count number of Circles", "count number of circles", "count number of Stars", "count number of circles",
			"count number of circles", "count number of circles", "count number of circles", "count number of stars", "count number of triangles", "count number of circles"};
	/**
	 * ATTENTION: This was auto-generated to implement the App Indexing API.
	 * See https://g.co/AppIndexing/AndroidStudio for more information.
	 */
	private GoogleApiClient client;


	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if (tts != null) {
			tts.stop();
			tts.shutdown();
		}

		super.onBackPressed();
	}

//HERE I AM GETTNG THE NAME WHAT USERR ENTERS AND BASED ON THAT TEXT IT WILL SPEAK...THIS METHOD IS IN CUSTOMDIALOG FRAGMENT

public void calname(String name, Bitmap bmp)
{
	getTime();
	if(hour>=12&&hour<=24)
	{
		s1="hi "+name+" good evening welcome";
	}
	else if(hour>0&&hour<=12)
	{
		s1="hi "+name+"good morning welcome";
	}
	//String s1="hi "+name+" welcome";
    kid_name=name;
	SharedPreferences sp=getSharedPreferences("kiddetail",0);
	SharedPreferences.Editor et=sp.edit();
	et.putString("kid_name",kid_name);
	et.putString("image",encodeTobase64(bmp));
	et.commit();
	i1.setImageBitmap(bmp);
	speak(s1);
}

	private String encodeTobase64(Bitmap bmp) {
		Bitmap imag=bmp;
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		imag.compress(Bitmap.CompressFormat.PNG,100,baos);
		byte[] b=baos.toByteArray();
		String image= Base64.encodeToString(b,Base64.DEFAULT);
		return image;
	}
	public  void getTime()
	{
		calendar=Calendar.getInstance();
		hour=calendar.get(Calendar.HOUR);
		Log.d("SUCHANDRA",""+hour);
		minute=calendar.get(Calendar.MINUTE);
	}

//HERE WE ARE GETTING THE DIALOG FRAGMENT ONE TIME ONLY..WHILE LAUNCHING APPLICATION FIRST TIME

public boolean firstTime()
{
	SharedPreferences sp = getPreferences(Context.MODE_PRIVATE);

	boolean  before = sp.getBoolean("RAN BEFORE", false);

	if (!before)
	{
		SharedPreferences.Editor et = sp.edit();
		et.putBoolean("RAN BEFORE", true);
		et.commit();
	}

	return !before;
}
	@TargetApi(Build.VERSION_CODES.KITKAT)
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);



		//form here we are calling the customdialog fragment
		if(firstTime())
		{
		    CustomDialogFragment cf=new CustomDialogFragment();
			cf.show(getSupportFragmentManager(), "hi");
		}
		final AssetManager assetManager = getAssets();

//INITIALIZE SPINNER

		s = (Spinner) findViewById(R.id.spinner1);

		//aa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, gallerypositiontext);
		//s.setAdapter(aa);

		width = getWindow().getWindowManager().getDefaultDisplay().getWidth();
		height = getWindow().getWindowManager().getDefaultDisplay().getHeight();
		w1 = getWindow().getWindowManager().getDefaultDisplay().getWidth();
		h1 = getWindow().getWindowManager().getDefaultDisplay().getHeight();
		l1 = (LinearLayout) findViewById(R.id.linear1);
		l3 = (LinearLayout) findViewById(R.id.linear3);
		l2 = (LinearLayout) findViewById(R.id.linear2);
		f1= (FrameLayout) findViewById(R.id.framelayout);
		ga = (Gallery) findViewById(R.id.gallery1);
		t1 = (TextView) findViewById(R.id.textView1);
		t2 = (TextView) findViewById(R.id.textView2);
		t3 = (TextView) findViewById(R.id.textView3);
		i1 = (ImageView) findViewById(R.id.imageView1);
		i2 = (ImageView) findViewById(R.id.imageView2);
		i3 = (ImageView) findViewById(R.id.imageView3);
		i5 = (ImageView) findViewById(R.id.imageView5);
		i4 = (ImageView) findViewById(R.id.imageView4);
		i6 = (ImageView) findViewById(R.id.imageView6);
		b1 = (Button) findViewById(R.id.button1);
		b2 = (Button) findViewById(R.id.button2);
		b3 = (Button) findViewById(R.id.button3);
		b4 = (Button) findViewById(R.id.button4);
		t1.setText("");
		t2.setText("");
		t2.setTextSize(170);
		t3.setText("Palle Technologies");
		t2.setVisibility(View.GONE);
		gadapter = new GalleryAdapter();
		ga.setAdapter(gadapter);
		l1.setVisibility(View.GONE);
		l3.setVisibility(View.GONE);
		i6.setVisibility(View.GONE);
		SharedPreferences sp=getSharedPreferences("kiddetail",0);
		String nam=sp.getString("kid_name","");
		String imagepath=sp.getString("image","");

		if(!(imagepath.equals("")))
		{
			bmp=decodebase64(imagepath);
			i1.setImageBitmap(bmp);
		}
		getTime();
		if(!(nam.equals(""))) {
			if(hour>=12&&hour<=24)
			{
				String name="hi "+nam+" good evening welcome";
				speak(name);
			}
			else if(hour>0&&hour<=12)
			{
				String name="hi "+nam+"good morning welcome";
				speak(name);
			}

			//speak(name);
		}


		//GALLERY CLICKS
		ga.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, final View view,
									int position, long id) {
				// TODO Auto-generated method stub

				pos1=position;
				pos = position;
				counter = 0;
				count = 1;
				cnt = 1;
				b1.setText("");
				b2.setText("");
				b3.setText("");
				b4.setText("");
				t1.setTextSize(30);
				l1.setVisibility(View.GONE);
				l3.setVisibility(View.GONE);
				i5.setVisibility(View.GONE);
				b1.setVisibility(View.GONE);
				b2.setVisibility(View.GONE);
				b3.setVisibility(View.GONE);
				b4.setVisibility(View.GONE);
				i1.setImageResource(galleryposition[position]);
				t1.setText(gallerypositiontext[position]);
				/*s.setAdapter(aa);
				s.setSelection(position);
				aa.notifyDataSetChanged();*/
				t2.setVisibility(View.GONE);
				speak(t1.getText().toString());

//HERE I AM SETTING SPINNER DROP DOWN LIST BASED ON GALLERY CLICKS	AND I AM CALLING THIS METHOD OUT OF ONCREATE METHOD

				if(position!=-1)
				{
					spinnerselect(position);
				}
//HERE I AM MAKING SPINNER IS VISIBLE
				Log.d(TAG,"pos:"+pos);
				if (pos != 1) {
					s.setVisibility(View.VISIBLE);
					t2.setVisibility(View.GONE);
					i1.setVisibility(View.VISIBLE);
				}
				if (pos1 == 1) {
					s.setVisibility(View.VISIBLE);
					t2.setText("");
				}
				if (pos == 8) {
					s.setVisibility(View.VISIBLE);
					i6.setVisibility(View.VISIBLE);
				}
				if (pos != 8) {
					s.setVisibility(View.VISIBLE);
					i6.setVisibility(View.GONE);
				}
//HERE I HAD CHANGE NUMBERS AS 11,12 AS 12,13


				if (pos != 12) {
					s.setVisibility(View.VISIBLE);
					l1.setVisibility(View.GONE);
					l3.setVisibility(View.GONE);
					i1.setVisibility(View.VISIBLE);
					i4.setVisibility(View.VISIBLE);
					i3.setVisibility(View.VISIBLE);
					i2.setVisibility(View.VISIBLE);
				}

				if (pos != 13) {
					s.setVisibility(View.VISIBLE);
					l1.setVisibility(View.GONE);
					l3.setVisibility(View.GONE);
					i1.setVisibility(View.VISIBLE);
					i4.setVisibility(View.VISIBLE);
					i3.setVisibility(View.VISIBLE);
					i2.setVisibility(View.VISIBLE);
				}
				if (pos == 13) {

					i4.setVisibility(View.GONE);
					i3.setVisibility(View.VISIBLE);
					i2.setVisibility(View.GONE);
					i5.setVisibility(View.VISIBLE);
					b1.setVisibility(View.VISIBLE);
					b2.setVisibility(View.VISIBLE);
					b3.setVisibility(View.VISIBLE);
					b4.setVisibility(View.VISIBLE);
				}
				if (pos == 12) {

					i4.setVisibility(View.GONE);
					i3.setVisibility(View.VISIBLE);
					i2.setVisibility(View.GONE);
					i5.setVisibility(View.VISIBLE);
					b1.setVisibility(View.VISIBLE);
					b2.setVisibility(View.VISIBLE);
					b3.setVisibility(View.VISIBLE);
					b4.setVisibility(View.VISIBLE);

				}
//HERE I MADE SPINNER IS INVISIBLE

				if(pos==12||pos==13)
				{
					s.setVisibility(View.GONE);
				}
			}
		});

//SPINNER CLICKS

		s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				//if (position == 0) {
				int counter1 = position;
				counter = counter1;
				if(pos1==0) {

					if(counter1==alphabets.length)
					{
						counter1=1;
					}
					t2.setVisibility(View.GONE);
					i1.setVisibility(View.VISIBLE);
					i1.setImageResource(alphabets[counter1]);
					t1.setText(alphabetstext[counter1]);
					speak(t1.getText().toString());
					counter1++;
				}
				if(pos1==1) {
					//int counter1 = position;
					t2.setVisibility(View.GONE);
					i1.setVisibility(View.VISIBLE);
					i1.setImageResource(numbers[counter1]);
					t1.setText(numberstext[counter1]);
					speak(t1.getText().toString());
					counter1++;
				}
				if(pos1==2) {
					//int counter1 = position;
					t2.setVisibility(View.GONE);
					i1.setVisibility(View.VISIBLE);
					i1.setImageResource(colors[counter1]);
					t1.setText(colorstext[counter1]);
					speak(t1.getText().toString());
					counter1++;
				}
				if(pos1==3) {
					//int counter1 = position;
					t2.setVisibility(View.GONE);
					i1.setVisibility(View.VISIBLE);
					i1.setImageResource(shapes[counter1]);
					t1.setText(shapestext[counter1]);
					speak(t1.getText().toString());
					counter1++;
				}
				if(pos1==4) {
					//int counter1 = position;
					t2.setVisibility(View.GONE);
					i1.setVisibility(View.VISIBLE);
					i1.setImageResource(months[counter1]);
					t1.setText(monthstext[counter1]);
					speak(t1.getText().toString());
					counter1++;
				}
				if(pos1==5) {
					//int counter1 = position;
					t2.setVisibility(View.GONE);
					i1.setVisibility(View.VISIBLE);
					i1.setImageResource(days[counter1]);
					t1.setText(daystext[counter1]);
					speak(t1.getText().toString());
					counter1++;
				}
				if(pos1==6) {
					//int counter1 = position;
					t2.setVisibility(View.GONE);
					i1.setVisibility(View.VISIBLE);
					i1.setImageResource(fruits[counter1]);
					t1.setText(fruitstext[counter1]);
					speak(t1.getText().toString());
					counter1++;
				}
				if(pos1==7) {
					//int counter1 = position;
					t2.setVisibility(View.GONE);
					i1.setVisibility(View.VISIBLE);
					i1.setImageResource(vegetables[counter1]);
					t1.setText(vegetablestext[counter1]);
					speak(t1.getText().toString());
					counter1++;
				}
				if(pos1==8) {
					//int counter1 = position;
					t2.setVisibility(View.GONE);
					i1.setVisibility(View.VISIBLE);
					i1.setImageResource(animals[counter1]);
					t1.setText(animalstext[counter1]);
					speak(t1.getText().toString());
					counter1++;
				}
				if(pos1==9) {
					//int counter1 = position;
					t2.setVisibility(View.GONE);
					i1.setVisibility(View.VISIBLE);
					i1.setImageResource(bodyparts[counter1]);
					t1.setText(bodypartstext[counter1]);
					speak(t1.getText().toString());
					counter1++;
				}
				if(pos1==10) {
					//int counter1 = position;
					t2.setVisibility(View.GONE);
					i1.setVisibility(View.VISIBLE);
					i1.setImageResource(vehicles[counter1]);
					t1.setText(vehiclestext[counter1]);
					speak(t1.getText().toString());
					counter1++;
				}
				if(pos1==11) {
					//int counter1 = position;
					t2.setVisibility(View.GONE);
					i1.setVisibility(View.VISIBLE);
					i1.setImageResource(planets[counter1]);
					t1.setText(planetstext[counter1]);
					speak(t1.getText().toString());
					counter1++;
				}

			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		//IMAGEVIEW USED TO DISPLAY PREVIOUS ITEM ON CLICK(i2 is previous item)

		i2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (pos == 0) {

					if (counter == 0) {
						counter = alphabets.length;
					}
					if (counter == 1) {
						counter = alphabets.length;
					}
					counter--;
					t2.setVisibility(View.GONE);
					i1.setVisibility(View.VISIBLE);
					i1.setImageResource(alphabets[counter]);
					t1.setText(alphabetstext[counter]);
					speak(t1.getText().toString());

				} else if (pos == 1) {

					if (counter == 0) {
						counter = 1000;
					}
					if (counter == 1) {
						counter = 1000;
					}

					counter--;
					i1.setVisibility(View.GONE);
					String c = Integer.toString(counter);
					t2.setVisibility(View.VISIBLE);
					t2.setText(c);
					speak(t2.getText().toString());

				} else if (pos == 2) {

					if (counter == 0) {
						counter = colors.length;
					}
					if (counter == 1) {
						counter = colors.length;
					}
					counter--;
					t2.setVisibility(View.GONE);
					i1.setVisibility(View.VISIBLE);
					i1.setImageResource(colors[counter]);
					t1.setText(colorstext[counter]);
					speak(t1.getText().toString());

				} else if (pos == 3) {

					if (counter == 0) {
						counter = shapes.length;
					}
					if (counter == 1) {
						counter = shapes.length;
					}
					counter--;
					t2.setVisibility(View.GONE);
					i1.setVisibility(View.VISIBLE);
					i1.setImageResource(shapes[counter]);
					t1.setText(shapestext[counter]);
					speak(t1.getText().toString());

				} else if (pos == 4) {

					if (counter == 0) {
						counter = months.length;
					}
					if (counter == 1) {
						counter = months.length;
					}
					counter--;
					t2.setVisibility(View.GONE);
					i1.setVisibility(View.VISIBLE);
					i1.setImageResource(months[counter]);
					t1.setText(monthstext[counter]);
					speak(t1.getText().toString());

				} else if (pos == 5) {

					if (counter == 0) {
						counter = days.length;
					}
					if (counter == 1) {
						counter = days.length;
					}
					counter--;
					t2.setVisibility(View.GONE);
					i1.setVisibility(View.VISIBLE);
					i1.setImageResource(days[counter]);
					t1.setText(daystext[counter]);
					speak(t1.getText().toString());

				} else if (pos == 6) {
					if (counter == 0) {
						counter = fruits.length;
					}
					if (counter == 1) {
						counter = fruits.length;
					}
					counter--;
					t2.setVisibility(View.GONE);
					i1.setVisibility(View.VISIBLE);
					i1.setImageResource(fruits[counter]);
					t1.setText(fruitstext[counter]);
					speak(t1.getText().toString());

				} else if (pos == 7) {

					if (counter == 0) {
						counter = vegetables.length;
					}
					if (counter == 1) {
						counter = vegetables.length;
					}
					counter--;
					t2.setVisibility(View.GONE);
					i1.setVisibility(View.VISIBLE);
					i1.setImageResource(vegetables[counter]);
					t1.setText(vegetablestext[counter]);
					speak(t1.getText().toString());

				} else if (pos == 8) {

					if (counter == 0) {
						counter = animals.length;
					}
					if (counter == 1) {
						counter = animals.length;
					}
					counter--;
					t2.setVisibility(View.GONE);
					i1.setVisibility(View.VISIBLE);
					i1.setImageResource(animals[counter]);
					t1.setText(animalstext[counter]);
					speak(t1.getText().toString());

				} else if (pos == 9) {

					if (counter == 0) {
						counter = bodyparts.length;
					}
					if (counter == 1) {
						counter = bodyparts.length;
					}
					counter--;
					t2.setVisibility(View.GONE);
					i1.setVisibility(View.VISIBLE);
					i1.setImageResource(bodyparts[counter]);
					t1.setText(bodypartstext[counter]);
					speak(t1.getText().toString());

				} else if (pos == 10) {

					if (counter == 0) {
						counter = vehicles.length;
					}
					if (counter == 1) {
						counter = vehicles.length;
					}
					counter--;
					t2.setVisibility(View.GONE);
					i1.setVisibility(View.VISIBLE);
					i1.setImageResource(vehicles[counter]);
					t1.setText(vehiclestext[counter]);
					speak(t1.getText().toString());
				}
				//i have to be add here

				else if (pos == 11) {

					if (counter == 0) {
						counter = planets.length;
					}
					if (counter == 1) {
						counter = planets.length;
					}
					counter--;
					t2.setVisibility(View.GONE);
					i1.setVisibility(View.VISIBLE);
					i1.setImageResource(planets[counter]);
					t1.setText(planetstext[counter]);
					speak(t1.getText().toString());
				}
			}
		});

//IMAGEVIEW USED TO DISPLAY NEXT ITEM ON CLICK(i4 is Next Item)

		i4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (pos == 0) {

					counter++;
					if (counter == alphabets.length) {
						counter = 1;
					}
					t2.setVisibility(View.GONE);
					i1.setVisibility(View.VISIBLE);
					i1.setImageResource(alphabets[counter]);
					t1.setText(alphabetstext[counter]);

					speak(t1.getText().toString());

					
						
					/*MyThread m=new MyThread();
					m.start();
					if(tts!=null){
						m.stop();;
					}*/


				} else if (pos == 1) {
					if (counter == 999) {
						counter = 0;
					}
					t1.setText("");
					counter++;
					i1.setVisibility(View.GONE);
					i5.setVisibility(View.GONE);
					l1.setVisibility(View.GONE);
					l3.setVisibility(View.GONE);
					String c = Integer.toString(counter);
					t2.setVisibility(View.VISIBLE);
					t2.setText(c);

					speak(t2.getText().toString());
				} else if (pos == 2) {
					counter++;
					if (counter == colors.length) {
						counter = 1;
					}
					t2.setVisibility(View.GONE);

					i1.setVisibility(View.VISIBLE);
					i1.setImageResource(colors[counter]);
					t1.setText(colorstext[counter]);
					speak(t1.getText().toString());


				} else if (pos == 3) {
					counter++;
					if (counter == shapes.length) {
						counter = 1;
					}
					t2.setVisibility(View.GONE);
					i1.setVisibility(View.VISIBLE);
					i1.setImageResource(shapes[counter]);
					t1.setText(shapestext[counter]);
					speak(t1.getText().toString());

				} else if (pos == 4) {
					counter++;
					if (counter == months.length) {
						counter = 1;
					}
					t2.setVisibility(View.GONE);
					i1.setVisibility(View.VISIBLE);
					i1.setImageResource(months[counter]);
					t1.setText(monthstext[counter]);
					speak(t1.getText().toString());

				} else if (pos == 5) {
					counter++;
					if (counter == days.length) {
						counter = 1;
					}
					t2.setVisibility(View.GONE);
					i1.setVisibility(View.VISIBLE);
					i1.setImageResource(days[counter]);
					t1.setText(daystext[counter]);
					speak(t1.getText().toString());

				} else if (pos == 6) {
					counter++;
					if (counter == fruits.length) {
						counter = 1;
					}
					t2.setVisibility(View.GONE);
					i1.setVisibility(View.VISIBLE);
					i1.setImageResource(fruits[counter]);
					t1.setText(fruitstext[counter]);
					speak(t1.getText().toString());

				} else if (pos == 7) {
					counter++;
					if (counter == vegetables.length) {
						counter = 1;
					}
					t2.setVisibility(View.GONE);
					i1.setVisibility(View.VISIBLE);
					i1.setImageResource(vegetables[counter]);
					t1.setText(vegetablestext[counter]);
					speak(t1.getText().toString());

				} else if (pos == 8) {
					counter++;
					if (counter == animals.length) {
						counter = 1;
					}
					t2.setVisibility(View.GONE);
					i1.setVisibility(View.VISIBLE);
					i1.setImageResource(animals[counter]);
					t1.setText(animalstext[counter]);
					speak(t1.getText().toString());

				} else if (pos == 9) {
					counter++;
					if (counter == bodyparts.length) {
						counter = 1;
					}
					t2.setVisibility(View.GONE);
					i1.setVisibility(View.VISIBLE);
					i1.setImageResource(bodyparts[counter]);
					t1.setText(bodypartstext[counter]);
					speak(t1.getText().toString());

				} else if (pos == 10) {
					counter++;
					if (counter == vehicles.length) {
						counter = 1;
					}
					t2.setVisibility(View.GONE);
					i1.setVisibility(View.VISIBLE);
					i1.setImageResource(vehicles[counter]);
					t1.setText(vehiclestext[counter]);
					speak(t1.getText().toString());

				}
				//here also i have to be add---pos==11
				else if (pos == 11) {
					counter++;
					if (counter == planets.length) {
						counter = 1;
					}
					t2.setVisibility(View.GONE);
					i1.setVisibility(View.VISIBLE);
					i1.setImageResource(planets[counter]);
					t1.setText(planetstext[counter]);
					speak(t1.getText().toString());

				}

			}
		});
//IMAGEVIEW USED TO PLAY(SPEAK) CURRENT IMAGE NAME (i3 is play button)

		i3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (pos == 0) {
					speak(t1.getText().toString());

				}
				if (pos == 1) {
					if (t2.getText().toString() == "") {
						speak(gallerypositiontext[pos]);

					}
					if (t2.getText().toString() != "") {
						speak(t2.getText().toString());
					}
				}
				if (pos == 2) {
					speak(t1.getText().toString());
				}
				if (pos == 3) {
					speak(t1.getText().toString());
				}
				if (pos == 4) {
					speak(t1.getText().toString());
				}
				if (pos == 5) {
					speak(t1.getText().toString());
				}
				if (pos == 6) {
					speak(t1.getText().toString());
				}
				if (pos == 7) {
					speak(t1.getText().toString());
				}
				if (pos == 8) {
					speak(t1.getText().toString());
				}
				if (pos == 9) {
					speak(t1.getText().toString());
				}
				if (pos == 10) {
					speak(t1.getText().toString());
				}
				//here i ahve to be add...pos==11
				if (pos == 11) {
					speak(t1.getText().toString());
				}


				//and change pos==11 as pos=12 and pos==12 as pos==13
				if (pos == 12) {
					speak(t1.getText().toString());

					if ((t1.getText().toString()).equals("NumberQuiz")) {
						counter = 1;
						i4.setVisibility(View.GONE);
						i3.setVisibility(View.VISIBLE);
						i2.setVisibility(View.GONE);
						i1.setVisibility(View.GONE);
						t2.setVisibility(View.GONE);
						l1.setVisibility(View.VISIBLE);
						l3.setVisibility(View.VISIBLE);
						t1.setTextSize(20);
						i5.setVisibility(View.VISIBLE);
						i5.setImageResource(numberquiz[counter]);
						t1.setText(numberquiztext[counter]);
						speak(t1.getText().toString());
						b1.setTextSize(30);
						b2.setTextSize(30);
						b3.setTextSize(30);
						b4.setTextSize(30);
						if (counter == 1) {
							b1.setText("4");
							b2.setText("1");
							b3.setText("3");
							b4.setText("2");
						}
					}
				}
				if (pos == 13) {
					speak(t1.getText().toString());
					if ((t1.getText().toString()).equals("Letter Quiz")) {
						counter = 1;
						i4.setVisibility(View.GONE);
						i3.setVisibility(View.VISIBLE);
						i2.setVisibility(View.GONE);
						i1.setVisibility(View.GONE);
						t2.setVisibility(View.GONE);
						l1.setVisibility(View.VISIBLE);
						l3.setVisibility(View.VISIBLE);
						t1.setTextSize(20);
						i5.setVisibility(View.VISIBLE);
						i5.setImageResource(alphabets[counter]);
						t1.setText(alphabetstext[counter]);
						speak(t1.getText().toString());
						b1.setTextSize(30);
						b2.setTextSize(30);
						b3.setTextSize(30);
						b4.setTextSize(30);
						if (counter == 1) {
							b1.setText("J");
							b2.setText("E");
							b3.setText("A");
							b4.setText("I");
						}

					}

				}
			}
		});

//FRAME LAYOUT WHICH CAN BE USED FOR SWIPING.THIS LISTENER CAN BE CALLED THE ONTOUCHEVENT()IN THE ACTIVITY OUTOF ONCREATE()

		i1.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				int action = event.getActionMasked();
				Log.d(TAG, String.valueOf(action));
				return true;
			}
		});


//IMAGEVIEW WHICH DISPLAYS IMAGES AND WHEN CLICKED SPEAKS CURRENT IMAGE NAME 

		/*i1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (pos == 0) {
					speak(t1.getText().toString());
				}
				if (pos == 1) {

					speak(gallerypositiontext[pos]);

				}
				if (pos == 2) {
					speak(t1.getText().toString());
				}
				if (pos == 3) {
					speak(t1.getText().toString());
				}
				if (pos == 4) {
					speak(t1.getText().toString());
				}
				if (pos == 5) {
					speak(t1.getText().toString());
				}
				if (pos == 6) {
					speak(t1.getText().toString());
				}
				if (pos == 7) {
					speak(t1.getText().toString());
				}
				if (pos == 8) {
					speak(t1.getText().toString());
				}
				if (pos == 9) {
					speak(t1.getText().toString());
				}
				if (pos == 10) {
					speak(t1.getText().toString());
				}

				//here i ahve to be add...pos==11
				if (pos == 11) {
					speak(t1.getText().toString());
				}

				//and change pos==11 as pos=12 and pos==12 as pos==13
				if (pos == 12) {
					counter++;
					if (counter == numberquiz.length) {
						counter = 1;
					}
					i4.setVisibility(View.GONE);
					i3.setVisibility(View.VISIBLE);
					i2.setVisibility(View.GONE);
					i1.setVisibility(View.GONE);
					t2.setVisibility(View.GONE);
					l1.setVisibility(View.VISIBLE);
					l3.setVisibility(View.VISIBLE);
					t1.setTextSize(20);
					i5.setVisibility(View.VISIBLE);
					i5.setImageResource(numberquiz[counter]);
					t1.setText(numberquiztext[counter]);
					speak(t1.getText().toString());
					b1.setTextSize(30);
					b2.setTextSize(30);
					b3.setTextSize(30);
					b4.setTextSize(30);
					if (counter == 1) {
						b1.setText("4");
						b2.setText("1");
						b3.setText("3");
						b4.setText("2");
					}
					if (counter == 5) {
						b1.setText("8");
						b2.setText("6");
						b3.setText("7");
						b4.setText("5");
					}
					if (counter == 9) {
						b1.setText("10");
						b2.setText("7");
						b3.setText("8");
						b4.setText("9");
					}


				}

				if (pos == 13) {
					alphabets[0] = R.drawable.gallery4;
					counter++;
					if (counter == alphabets.length) {
						counter = 1;
					}
					i4.setVisibility(View.GONE);
					i3.setVisibility(View.VISIBLE);
					i2.setVisibility(View.GONE);
					i1.setVisibility(View.GONE);
					t2.setVisibility(View.GONE);
					l1.setVisibility(View.VISIBLE);
					l3.setVisibility(View.VISIBLE);
					t1.setTextSize(20);
					i5.setVisibility(View.VISIBLE);
					i5.setImageResource(alphabets[counter]);
					t1.setText(alphabetstext[counter]);
					speak(t1.getText().toString());
					b1.setTextSize(30);
					b2.setTextSize(30);
					b3.setTextSize(30);
					b4.setTextSize(30);
					if (counter == 1) {
						b1.setText("J");
						b2.setText("E");
						b3.setText("A");
						b4.setText("I");
					}
					if (counter == 4) {
						b1.setText("F");
						b2.setText("K");
						b3.setText("B");
						b4.setText("D");
					}
					if (counter == 8) {
						b1.setText("H");
						b2.setText("J");
						b3.setText("O");
						b4.setText("P");
					}
					if (counter == 12) {
						b1.setText("D");
						b2.setText("L");
						b3.setText("U");
						b4.setText("N");
					}
					if (counter == 16) {
						b1.setText("P");
						b2.setText("S");
						b3.setText("E");
						b4.setText("W");
					}
					if (counter == 20) {
						b1.setText("U");
						b2.setText("T");
						b3.setText("A");
						b4.setText("R");
					}
					if (counter == 24) {
						b1.setText("X");
						b2.setText("S");
						b3.setText("T");
						b4.setText("R");
					}
					if (counter == 26) {
						b1.setText("Z");
						b2.setText("B");
						b3.setText("D");
						b4.setText("C");
					}

				}

			}
		});
*/
//TextView used in FrameLayout to display numbers from 1 to 1000(GALLERY ITEM POSITION 1-NUMBERS)
		t2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (t2.getText().toString() != "") {
					speak(t2.getText().toString());
				}
			}
		});

//IMAGEVIEW USED IN FRAMELAYOUT TO DISPLAY IMAGES FOR NUMBER QUIZ AND LETTER QUIZ(GALLERY ITEM POSITION 11 AND 12)

		i5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				speak(t1.getText().toString());
			}
		});

		i6.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				AudioPlayer ab = new AudioPlayer("bat.mp3", MainActivity.this);


			}
		});

//FOUR BUTTONS USED IN NUMBER QUIZ AND LETTER QUIZ(B1,B2,B3,B4)

//FIRST BUTTON
		b1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				//here i ahve to change the pos==11 as pos=12 and pos==12 as pos=13
				if (pos == 12) {
					if (count == 4) {
						count++;
						i5.setImageResource(numberquiz[count]);
						t1.setText(numberquiztext[count]);
						speak("right answer");
						b1.setText("7");
						b2.setText("6");
						b3.setText("8");
						b4.setText("5");
					}
				}
				if (pos == 12) {
					if (count == 10) {
						count = 1;
						i5.setImageResource(numberquiz[count]);
						t1.setText(numberquiztext[count]);
						speak("right answer");
						b1.setText("4");
						b2.setText("1");
						b3.setText("3");
						b4.setText("2");
					}
				}
				if (pos == 13) {
					if (cnt == 2) {
						cnt++;
						i5.setImageResource(alphabets[cnt]);
						t1.setText(alphabetstext[cnt]);
						speak(t1.getText().toString());
						b1.setText("Z");
						b2.setText("D");
						b3.setText("C");
						b4.setText("B");
					}
				}
				if (pos == 13) {
					if (cnt == 6) {
						cnt++;
						i5.setImageResource(alphabets[cnt]);
						t1.setText(alphabetstext[cnt]);
						speak(t1.getText().toString());
						b1.setText("R");
						b2.setText("I");
						b3.setText("G");
						b4.setText("H");
					}
				}
				if (pos == 13) {
					if (cnt == 8) {
						cnt++;
						i5.setImageResource(alphabets[cnt]);
						t1.setText(alphabetstext[cnt]);
						speak(t1.getText().toString());
						b1.setText("Q");
						b2.setText("T");
						b3.setText("J");
						b4.setText("I");
					}
				}
				if (pos == 13) {
					if (cnt == 11) {
						cnt++;
						i5.setImageResource(alphabets[cnt]);
						t1.setText(alphabetstext[cnt]);
						speak(t1.getText().toString());
						b1.setText("D");
						b2.setText("L");
						b3.setText("U");
						b4.setText("N");
					}
				}
				if (pos == 13) {
					if (cnt == 14) {
						cnt++;
						i5.setImageResource(alphabets[cnt]);
						t1.setText(alphabetstext[cnt]);
						speak(t1.getText().toString());
						b1.setText("C");
						b2.setText("P");
						b3.setText("U");
						b4.setText("O");
					}
				}
				if (pos == 13) {
					if (cnt == 16) {
						cnt++;
						i5.setImageResource(alphabets[cnt]);
						t1.setText(alphabetstext[cnt]);
						speak(t1.getText().toString());
						b1.setText("H");
						b2.setText("T");
						b3.setText("Q");
						b4.setText("U");
					}
				}
				if (pos == 13) {
					if (cnt == 18) {
						cnt++;
						i5.setImageResource(alphabets[cnt]);
						t1.setText(alphabetstext[cnt]);
						speak(t1.getText().toString());
						b1.setText("E");
						b2.setText("L");
						b3.setText("O");
						b4.setText("S");
					}
				}
				if (pos == 13) {
					if (cnt == 24) {
						cnt++;
						i5.setImageResource(alphabets[cnt]);
						t1.setText(alphabetstext[cnt]);
						speak(t1.getText().toString());
						b1.setText("Z");
						b2.setText("E");
						b3.setText("A");
						b4.setText("Y");
					}
				}

				if (pos == 13) {
					if (cnt == 26) {
						cnt = 1;
						i5.setImageResource(alphabets[cnt]);
						t1.setText(alphabetstext[cnt]);
						speak(t1.getText().toString());
						b1.setText("J");
						b2.setText("E");
						b3.setText("A");
						b4.setText("I");
					}
				}
			}
		});

//SECOND BUTTON
		b2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				//here i ahve to change the pos==11 as pos=12 and pos==12 as pos=13

				if (pos == 12) {
					if (count == 1) {
						count++;
						i5.setImageResource(numberquiz[count]);
						t1.setText(numberquiztext[count]);
						speak("right answer");
						b1.setText("3");
						b2.setText("4");
						b3.setText("1");
						b4.setText("2");
					}
				}
				if (pos == 12) {
					if (count == 6) {
						count++;
						i5.setImageResource(numberquiz[count]);
						t1.setText(numberquiztext[count]);
						speak("right answer");
						b1.setText("6");
						b2.setText("8");
						b3.setText("7");
						b4.setText("5");
					}
				}
				if (pos == 13) {
					if (cnt == 10) {
						cnt++;
						i5.setImageResource(alphabets[cnt]);
						t1.setText(alphabetstext[cnt]);
						speak(t1.getText().toString());
						b1.setText("K");
						b2.setText("D");
						b3.setText("S");
						b4.setText("R");
					}
				}
				if (pos == 13) {
					if (cnt == 12) {
						cnt++;
						i5.setImageResource(alphabets[cnt]);
						t1.setText(alphabetstext[cnt]);
						speak(t1.getText().toString());
						b1.setText("T");
						b2.setText("R");
						b3.setText("M");
						b4.setText("I");
					}
				}
				if (pos == 13) {
					if (cnt == 20) {
						cnt++;
						i5.setImageResource(alphabets[cnt]);
						t1.setText(alphabetstext[cnt]);
						speak(t1.getText().toString());
						b1.setText("J");
						b2.setText("I");
						b3.setText("G");
						b4.setText("U");
					}
				}
				if (pos == 13) {
					if (cnt == 22) {
						cnt++;
						i5.setImageResource(alphabets[cnt]);
						t1.setText(alphabetstext[cnt]);
						speak(t1.getText().toString());
						b1.setText("Z");
						b2.setText("X");
						b3.setText("W");
						b4.setText("V");
					}
				}

			}
		});

//THIRD BUTTON
		b3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				//here i ahve to change the pos==11 as pos=12 and pos==12 as pos=13
				if (pos == 12) {
					if (count == 3) {
						count++;
						i5.setImageResource(numberquiz[count]);
						t1.setText(numberquiztext[count]);
						speak("right answer");
						b1.setText("4");
						b2.setText("3");
						b3.setText("1");
						b4.setText("2");
					}
				}
				if (pos == 12) {
					if (count == 7) {
						count++;
						i5.setImageResource(numberquiz[count]);
						t1.setText(numberquiztext[count]);
						speak("right answer");
						b1.setText("9");
						b2.setText("7");
						b3.setText("6");
						b4.setText("8");
					}
				}
				if (pos == 12) {
					if (count == 9) {
						count++;
						i5.setImageResource(numberquiz[count]);
						t1.setText(numberquiztext[count]);
						speak("right answer");
						b1.setText("10");
						b2.setText("7");
						b3.setText("8");
						b4.setText("9");

					}
				}


				if (pos == 13) {
					if (cnt == 1) {
						cnt++;
						i5.setImageResource(alphabets[cnt]);
						t1.setText(alphabetstext[cnt]);
						speak(t1.getText().toString());
						b1.setText("B");
						b2.setText("H");
						b3.setText("L");
						b4.setText("N");
					}
				}
				if (pos == 13) {
					if (cnt == 3) {
						cnt++;
						i5.setImageResource(alphabets[cnt]);
						t1.setText(alphabetstext[cnt]);
						speak(t1.getText().toString());
						b1.setText("F");
						b2.setText("K");
						b3.setText("B");
						b4.setText("D");
					}
				}
				if (pos == 13) {
					if (cnt == 5) {
						cnt++;
						i5.setImageResource(alphabets[cnt]);
						t1.setText(alphabetstext[cnt]);
						speak(t1.getText().toString());
						b1.setText("F");
						b2.setText("C");
						b3.setText("E");
						b4.setText("P");
					}
				}
				if (pos == 13) {
					if (cnt == 7) {
						cnt++;
						i5.setImageResource(alphabets[cnt]);
						t1.setText(alphabetstext[cnt]);
						speak(t1.getText().toString());
						b1.setText("H");
						b2.setText("J");
						b3.setText("O");
						b4.setText("P");
					}
				}
				if (pos == 13) {
					if (cnt == 13) {
						cnt++;
						i5.setImageResource(alphabets[cnt]);
						t1.setText(alphabetstext[cnt]);
						speak(t1.getText().toString());
						b1.setText("N");
						b2.setText("B");
						b3.setText("U");
						b4.setText("E");
					}
				}
				if (pos == 13) {
					if (cnt == 17) {
						cnt++;
						i5.setImageResource(alphabets[cnt]);
						t1.setText(alphabetstext[cnt]);
						speak(t1.getText().toString());
						b1.setText("R");
						b2.setText("M");
						b3.setText("C");
						b4.setText("L");
					}
				}
				if (pos == 13) {
					if (cnt == 23) {
						cnt++;
						i5.setImageResource(alphabets[cnt]);
						t1.setText(alphabetstext[cnt]);
						speak(t1.getText().toString());
						b1.setText("X");
						b2.setText("S");
						b3.setText("T");
						b4.setText("R");
					}
				}
			}
		});

//FOURTH BUTTON
		b4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				//here i ahve to change the pos==11 as pos=12 and pos==12 as pos=13

				if (pos == 12) {
					if (count == 2) {
						count++;
						i5.setImageResource(numberquiz[count]);
						t1.setText(numberquiztext[count]);
						speak("right answer");
						b1.setText("2");
						b2.setText("1");
						b3.setText("3");
						b4.setText("4");

					}
				}
				if (pos == 12) {
					if (count == 5) {
						count++;
						i5.setImageResource(numberquiz[count]);
						t1.setText(numberquiztext[count]);
						speak("right answer");
						b1.setText("8");
						b2.setText("6");
						b3.setText("7");
						b4.setText("5");
					}
				}
				if (pos == 12) {
					if (count == 8) {
						count++;
						i5.setImageResource(numberquiz[count]);
						t1.setText(numberquiztext[count]);
						speak("right answer");
						b1.setText("10");
						b2.setText("8");
						b3.setText("9");
						b4.setText("7");

					}
				}


				if (pos == 13) {
					if (cnt == 4) {
						cnt++;
						i5.setImageResource(alphabets[cnt]);
						t1.setText(alphabetstext[cnt]);
						speak(t1.getText().toString());
						b1.setText("M");
						b2.setText("P");
						b3.setText("E");
						b4.setText("B");
					}
				}
				if (pos == 13) {
					if (cnt == 9) {
						cnt++;
						i5.setImageResource(alphabets[cnt]);
						t1.setText(alphabetstext[cnt]);
						speak(t1.getText().toString());
						b1.setText("G");
						b2.setText("J");
						b3.setText("K");
						b4.setText("I");
					}
				}
				if (pos == 13) {
					if (cnt == 15) {
						cnt++;
						i5.setImageResource(alphabets[cnt]);
						t1.setText(alphabetstext[cnt]);
						speak(t1.getText().toString());
						b1.setText("P");
						b2.setText("S");
						b3.setText("E");
						b4.setText("W");
					}
				}
				if (pos == 13) {
					if (cnt == 19) {
						cnt++;
						i5.setImageResource(alphabets[cnt]);
						t1.setText(alphabetstext[cnt]);
						speak(t1.getText().toString());
						b1.setText("U");
						b2.setText("T");
						b3.setText("A");
						b4.setText("R");
					}
				}
				if (pos == 13) {
					if (cnt == 21) {
						cnt++;
						i5.setImageResource(alphabets[cnt]);
						t1.setText(alphabetstext[cnt]);
						speak(t1.getText().toString());
						b1.setText("T");
						b2.setText("V");
						b3.setText("R");
						b4.setText("X");
					}
				}
				if (pos == 13) {
					if (cnt == 25) {
						cnt++;
						i5.setImageResource(alphabets[cnt]);
						t1.setText(alphabetstext[cnt]);
						speak(t1.getText().toString());
						b1.setText("Z");
						b2.setText("B");
						b3.setText("D");
						b4.setText("C");
					}
				}
			}
		});
		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
	}

	private Bitmap decodebase64(String imagepath) {
		byte[] decodeimage=Base64.decode(imagepath,0);
		return BitmapFactory.decodeByteArray(decodeimage,0,decodeimage.length);
	}

//HERE I AM DEFINING THIS METHOD AND SETTING SPINNER DROPDOWN LIST BASED ON GALLERY SELECTION

	public void spinnerselect(int position)
	{
		if(pos1 == 0){
			ArrayAdapter alphabet;
			alphabet = new ArrayAdapter<String>(getApplication(), android.R.layout.simple_spinner_dropdown_item, alphabetstext);
			s.setAdapter(alphabet);
			alphabet.notifyDataSetChanged();
		}
		if (pos1 == 1) {
			t2.setText("");
			ArrayAdapter numb;
			numb = new ArrayAdapter<String>(getApplication(), android.R.layout.simple_spinner_dropdown_item, numberstext);
			s.setAdapter(numb);
			numb.notifyDataSetChanged();
		}
		if(pos1 == 2){
			ArrayAdapter color;
			color = new ArrayAdapter<String>(getApplication(), android.R.layout.simple_spinner_dropdown_item, colorstext);
			s.setAdapter(color);
			color.notifyDataSetChanged();
		}
		if(pos1 == 3){
			ArrayAdapter shape;
			shape = new ArrayAdapter<String>(getApplication(), android.R.layout.simple_spinner_dropdown_item, shapestext);
			s.setAdapter(shape);
			shape.notifyDataSetChanged();
		}
		if(pos1 == 4){
			ArrayAdapter month;
			month = new ArrayAdapter<String>(getApplication(), android.R.layout.simple_spinner_dropdown_item, monthstext);
			s.setAdapter(month);
			month.notifyDataSetChanged();
		}
		if(pos1 == 5){
			ArrayAdapter day;
			day = new ArrayAdapter<String>(getApplication(), android.R.layout.simple_spinner_dropdown_item, daystext);
			s.setAdapter(day);
			day.notifyDataSetChanged();
		}
		if(pos1 == 6){
			ArrayAdapter fruit;
			fruit = new ArrayAdapter<String>(getApplication(), android.R.layout.simple_spinner_dropdown_item, fruitstext);
			s.setAdapter(fruit);
			fruit.notifyDataSetChanged();
		}
		if(pos1 == 7){
			ArrayAdapter vegetable;
			vegetable = new ArrayAdapter<String>(getApplication(), android.R.layout.simple_spinner_dropdown_item, vegetablestext);
			s.setAdapter(vegetable);
			vegetable.notifyDataSetChanged();
		}

		if(pos1 == 8){
			ArrayAdapter animal;
			animal = new ArrayAdapter<String>(getApplication(), android.R.layout.simple_spinner_dropdown_item, animalstext);
			s.setAdapter(animal);
			animal.notifyDataSetChanged();
		}

		if(pos1 == 9){
			ArrayAdapter bodypart;
			bodypart = new ArrayAdapter<String>(getApplication(), android.R.layout.simple_spinner_dropdown_item, bodypartstext);
			s.setAdapter(bodypart);
			bodypart.notifyDataSetChanged();
		}

		if(pos1 == 10){
			ArrayAdapter vehicle;
			vehicle = new ArrayAdapter<String>(getApplication(), android.R.layout.simple_spinner_dropdown_item, vehiclestext);
			s.setAdapter(vehicle);
			vehicle.notifyDataSetChanged();
		}

		if(pos1 == 11){
			ArrayAdapter planet;
			planet = new ArrayAdapter<String>(getApplication(), android.R.layout.simple_spinner_dropdown_item, planetstext);
			s.setAdapter(planet);
			planet.notifyDataSetChanged();
		}


	}


	/*//WE OVERRIDE THE ONTOUCHEVENT() METHOD OF THE ACTIVITY TO DETECT VARIOUS TOUCH EVENTS:
      //BY CALLING F1.SETONTOUCHLISTENER()..THIS ONTOUCHEVENT METHOD IS CALLED BY THIS LISTENER
      //THIS ONTOUCHEVENT()SHOULD BE OUTOF ONCREATE() AND WITHIN MAINACTIVITY*/

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getActionMasked();
		switch (action)
		{
			case MotionEvent.ACTION_DOWN:
				initialX=event.getX();
				initialY=event.getY();
				Log.d(TAG,"action down");
				break;
			case MotionEvent.ACTION_MOVE:
				Log.d(TAG,"action move");
				break;
			case MotionEvent.ACTION_UP:
				float finalX = event.getX();
				float finalY = event.getY();

				Log.d(TAG, "Action was UP");

				if (initialX < finalX) {
					Log.d(TAG, "Left to Right swipe performed");

					if (pos == 0) {

						if (counter == 0) {
							counter = alphabets.length;
						}
						if (counter == 1) {
							counter = alphabets.length;
						}
						counter--;

						t2.setVisibility(View.GONE);
						i1.setVisibility(View.VISIBLE);
						i1.setImageResource(alphabets[counter]);
						t1.setText(alphabetstext[counter]);
						speak(t1.getText().toString());

					} else if (pos == 1) {

						if (counter == 0) {
							counter = 1000;
						}
						if (counter == 1) {
							counter = 1000;
						}

						counter--;
						i1.setVisibility(View.GONE);
						String c = Integer.toString(counter);
						t2.setVisibility(View.VISIBLE);
						t2.setText(c);
						speak(t2.getText().toString());

					} else if (pos == 2) {

						if (counter == 0) {
							counter = colors.length;
						}
						if (counter == 1) {
							counter = colors.length;
						}
						counter--;

						t2.setVisibility(View.GONE);
						i1.setVisibility(View.VISIBLE);
						i1.setImageResource(colors[counter]);
						t1.setText(colorstext[counter]);
						speak(t1.getText().toString());

					} else if (pos == 3) {

						if (counter == 0) {
							counter = shapes.length;
						}
						if (counter == 1) {
							counter = shapes.length;
						}
						counter--;
						t2.setVisibility(View.GONE);
						i1.setVisibility(View.VISIBLE);
						i1.setImageResource(shapes[counter]);
						t1.setText(shapestext[counter]);
						speak(t1.getText().toString());

					} else if (pos == 4) {

						if (counter == 0) {
							counter = months.length;
						}
						if (counter == 1) {
							counter = months.length;
						}
						counter--;
						t2.setVisibility(View.GONE);
						i1.setVisibility(View.VISIBLE);
						i1.setImageResource(months[counter]);
						t1.setText(monthstext[counter]);
						speak(t1.getText().toString());

					} else if (pos == 5) {

						if (counter == 0) {
							counter = days.length;
						}
						if (counter == 1) {
							counter = days.length;
						}
						counter--;
						t2.setVisibility(View.GONE);
						i1.setVisibility(View.VISIBLE);
						i1.setImageResource(days[counter]);
						t1.setText(daystext[counter]);
						speak(t1.getText().toString());

					} else if (pos == 6) {

						if (counter == 0) {
							counter = fruits.length;
						}
						if (counter == 1) {
							counter = fruits.length;
						}
						counter--;
						t2.setVisibility(View.GONE);
						i1.setVisibility(View.VISIBLE);
						i1.setImageResource(fruits[counter]);
						t1.setText(fruitstext[counter]);
						speak(t1.getText().toString());

					} else if (pos == 7) {

						if (counter == 0) {
							counter = vegetables.length;
						}
						if (counter == 1) {
							counter = vegetables.length;
						}
						counter--;
						t2.setVisibility(View.GONE);
						i1.setVisibility(View.VISIBLE);
						i1.setImageResource(vegetables[counter]);
						t1.setText(vegetablestext[counter]);
						speak(t1.getText().toString());

					} else if (pos == 8) {

						if (counter == 0) {
							counter = animals.length;
						}
						if (counter == 1) {
							counter = animals.length;
						}
						counter--;
						t2.setVisibility(View.GONE);
						i1.setVisibility(View.VISIBLE);
						i1.setImageResource(animals[counter]);
						t1.setText(animalstext[counter]);
						speak(t1.getText().toString());

					} else if (pos == 9) {

						if (counter == 0) {
							counter = bodyparts.length;
						}
						if (counter == 1) {
							counter = bodyparts.length;
						}
						counter--;
						t2.setVisibility(View.GONE);
						i1.setVisibility(View.VISIBLE);
						i1.setImageResource(bodyparts[counter]);
						t1.setText(bodypartstext[counter]);
						speak(t1.getText().toString());

					} else if (pos == 10) {

						if (counter == 0) {
							counter = vehicles.length;
						}
						if (counter == 1) {
							counter = vehicles.length;
						}
						counter--;
						t2.setVisibility(View.GONE);
						i1.setVisibility(View.VISIBLE);
						i1.setImageResource(vehicles[counter]);
						t1.setText(vehiclestext[counter]);
						speak(t1.getText().toString());
					}
					//here i have to add else if(pos==11)..copy paste code
					else if (pos == 11) {

						if (counter == 0) {
							counter = planets.length;
						}
						if (counter == 1) {
							counter = planets.length;
						}
						counter--;
						t2.setVisibility(View.GONE);
						i1.setVisibility(View.VISIBLE);
						i1.setImageResource(planets[counter]);
						t1.setText(planetstext[counter]);
						speak(t1.getText().toString());
					}

				}

				if (initialX > finalX) {
					Log.d(TAG, "Right to Left swipe performed");

					if (pos == 0) {

						counter++;
						if (counter == alphabets.length) {
							counter = 1;
						}
						t2.setVisibility(View.GONE);
						i1.setImageResource(alphabets[counter]);
						t1.setText(alphabetstext[counter]);
						speak(t1.getText().toString());

					//MyThread m=new MyThread();
					//m.start();
					//if(tts!=null){
					//	m.stop();;
					//}


					}else if (pos == 1) {
						if (counter == 999) {
							counter = 0;
						}
						t1.setText("");
						counter++;
						i1.setVisibility(View.GONE);
						i5.setVisibility(View.GONE);
						l1.setVisibility(View.GONE);
						l3.setVisibility(View.GONE);
						String c = Integer.toString(counter);
						t2.setVisibility(View.VISIBLE);
						t2.setText(c);

						speak(t2.getText().toString());
					} else if (pos == 2) {
						counter++;
						if (counter == colors.length) {
							counter = 1;
						}
						t2.setVisibility(View.GONE);

						i1.setVisibility(View.VISIBLE);
						i1.setImageResource(colors[counter]);
						t1.setText(colorstext[counter]);
						speak(t1.getText().toString());


					} else if (pos == 3) {
						counter++;
						if (counter == shapes.length) {
							counter = 1;
						}
						t2.setVisibility(View.GONE);
						i1.setVisibility(View.VISIBLE);
						i1.setImageResource(shapes[counter]);
						t1.setText(shapestext[counter]);
						speak(t1.getText().toString());

					} else if (pos == 4) {
						counter++;
						if (counter == months.length) {
							counter = 1;
						}
						t2.setVisibility(View.GONE);
						i1.setVisibility(View.VISIBLE);
						i1.setImageResource(months[counter]);
						t1.setText(monthstext[counter]);
						speak(t1.getText().toString());

					} else if (pos == 5) {
						counter++;
						if (counter == days.length) {
							counter = 1;
						}
						t2.setVisibility(View.GONE);
						i1.setVisibility(View.VISIBLE);
						i1.setImageResource(days[counter]);
						t1.setText(daystext[counter]);
						speak(t1.getText().toString());

					} else if (pos == 6) {
						counter++;
						if (counter == fruits.length) {
							counter = 1;
						}
						t2.setVisibility(View.GONE);
						i1.setVisibility(View.VISIBLE);
						i1.setImageResource(fruits[counter]);
						t1.setText(fruitstext[counter]);
						speak(t1.getText().toString());

					} else if (pos == 7) {
						counter++;
						if (counter == vegetables.length) {
							counter = 1;
						}
						t2.setVisibility(View.GONE);
						i1.setVisibility(View.VISIBLE);
						i1.setImageResource(vegetables[counter]);
						t1.setText(vegetablestext[counter]);
						speak(t1.getText().toString());

					} else if (pos == 8) {
						counter++;
						if (counter == animals.length) {
							counter = 1;
						}
						t2.setVisibility(View.GONE);
						i1.setVisibility(View.VISIBLE);
						i1.setImageResource(animals[counter]);
						t1.setText(animalstext[counter]);
						speak(t1.getText().toString());

					} else if (pos == 9) {
						counter++;
						if (counter == bodyparts.length) {
							counter = 1;
						}
						t2.setVisibility(View.GONE);
						i1.setVisibility(View.VISIBLE);
						i1.setImageResource(bodyparts[counter]);
						t1.setText(bodypartstext[counter]);
						speak(t1.getText().toString());

					} else if (pos == 10) {
						counter++;
						if (counter == vehicles.length) {
							counter = 1;
						}
						t2.setVisibility(View.GONE);
						i1.setVisibility(View.VISIBLE);
						i1.setImageResource(vehicles[counter]);
						t1.setText(vehiclestext[counter]);
						speak(t1.getText().toString());

					}
					//here i have to add else if(pos==11)..copy paste code
					else if (pos == 11) {
						counter++;
						if (counter == planets.length) {
							counter = 1;
						}
						t2.setVisibility(View.GONE);
						i1.setVisibility(View.VISIBLE);
						i1.setImageResource(planets[counter]);
						t1.setText(planetstext[counter]);
						speak(t1.getText().toString());

					}

				}

				if (initialY < finalY) {
					Log.d(TAG, "Up to Down swipe performed");
				}

				if (initialY > finalY) {
					Log.d(TAG, "Down to Up swipe performed");
				}

				break;

			case MotionEvent.ACTION_CANCEL:
				Log.d(TAG,"Action was CANCEL");
				break;

			case MotionEvent.ACTION_OUTSIDE:
				Log.d(TAG, "Movement occurred outside bounds of current screen element");
				break;
		}

		return super.onTouchEvent(event);

	}



	@Override
	public void onStart() {
		super.onStart();

		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		client.connect();
		Action viewAction = Action.newAction(
				Action.TYPE_VIEW, // TODO: choose an action type.
				"Main Page", // TODO: Define a title for the content shown.
				// TODO: If you have web page content that matches this app activity's content,
				// make sure this auto-generated web page URL is correct.
				// Otherwise, set the URL to null.
				Uri.parse("http://host/path"),
				// TODO: Make sure this auto-generated app URL is correct.
				Uri.parse("android-app://com.example.educationalgameforkids/http/host/path")
		);
		AppIndex.AppIndexApi.start(client, viewAction);
	}

	@Override
	public void onStop() {
		super.onStop();

		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		Action viewAction = Action.newAction(
				Action.TYPE_VIEW, // TODO: choose an action type.
				"Main Page", // TODO: Define a title for the content shown.
				// TODO: If you have web page content that matches this app activity's content,
				// make sure this auto-generated web page URL is correct.
				// Otherwise, set the URL to null.
				Uri.parse("http://host/path"),
				// TODO: Make sure this auto-generated app URL is correct.
				Uri.parse("android-app://com.example.educationalgameforkids/http/host/path")
		);
		AppIndex.AppIndexApi.end(client, viewAction);
		client.disconnect();
	}


	private class GalleryAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return galleryposition.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return galleryposition[position];
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ImageView i1 = new ImageView(getApplicationContext());
			int w = (width / 5) * 2;
			int h = (height / 16) * 3;
			int w1 = (width / 10) * 8;
			int h1 = (height / 16) * 8;
			i1.setLayoutParams(new Gallery.LayoutParams(w, h));
			i1.setBackgroundResource(android.R.drawable.gallery_thumb);
			i1.setImageResource(galleryposition[position]);
			return i1;
		}

	}


	@Override
	protected void onDestroy() {

		// TODO Auto-generated method stub
		if (tts != null) {
			tts.stop();
			tts.shutdown();
		}

		super.onDestroy();
	}

	public void speak(final String text) {
		tts = new TextToSpeech(this, new OnInitListener() {

			@Override
			public void onInit(int status) {
				// TODO Auto-generated method stub
				if (status != TextToSpeech.ERROR) {
					tts.setLanguage(Locale.US);
					tts.setSpeechRate((float) 1.0);

				}
				if (tts.isSpeaking() == true) {
					if (i4.isPressed() == true) {
						tts = null;
					} else
						i4.setClickable(true);
				}
				tts.speak(text, TextToSpeech.QUEUE_ADD, null);


			}
		});

	}

	class MyThread extends Thread {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			speak(t1.getText().toString());
			if (i4.isPressed() == true) {

			}
			super.run();
		}
	}

}

class AudioPlayer{
	
	private static final ParcelFileDescriptor mp3 = null;
	String fileName;
    Context contex;
	MediaPlayer mp;
	
	
	String[] name={"bear.mp3"};
	AssetFileDescriptor descricptor=new AssetFileDescriptor(mp3, 1, 1);
public AudioPlayer(String name,Context context){
		fileName=name;
		context=context;
	 	playAudio();
	}
	public void playAudio(){
		mp=new MediaPlayer();

  		try {
  			AssetFileDescriptor descricptor=new AssetFileDescriptor(mp3, 1, 1);
				AssetFileDescriptor descriptor=contex.getAssets().openFd("bear.mp3");
				mp.setDataSource(descriptor.getFileDescriptor(),descriptor.getStartOffset(),descriptor.getLength());
				descriptor.close();
				mp.prepare();
				mp.setLooping(true);
				mp.start();
				mp.setVolume(3, 3);
				
				
			} 
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (IllegalStateException e) {
				// TODO: handle exception
				e.printStackTrace();
			}catch (IllegalArgumentException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
		
		
	}
	public void stop(){
		mp.stop();
	}
}


