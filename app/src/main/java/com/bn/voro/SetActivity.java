package com.bn.voro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.*;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.appbar.AppBarLayout;
import android.app.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.webkit.*;
import android.animation.*;
import android.view.animation.*;
import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.text.*;
import org.json.*;
import android.widget.ScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.app.Activity;
import android.content.SharedPreferences;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.CompoundButton;
import android.text.Editable;
import android.text.TextWatcher;
import org.jetbrains.kotlin.*;
import okhttp3.*;
import okio.*;
import com.goodiebag.protractorview.*;
import com.rabtman.wsmanager.*;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;

public class SetActivity extends AppCompatActivity {
	
	private Toolbar _toolbar;
	private AppBarLayout _app_bar;
	private CoordinatorLayout _coordinator;
	
	private ScrollView scroller;
	private LinearLayout ground;
	private LinearLayout frame1;
	private LinearLayout frame2;
	private LinearLayout frame3;
	private LinearLayout frame4;
	private LinearLayout frame5;
	private LinearLayout frame6;
	private LinearLayout frameb;
	private TextView ti1;
	private LinearLayout ins1;
	private TextView te1;
	private EditText edi1;
	private ImageView img1;
	private TextView ti2;
	private LinearLayout ins2;
	private TextView te2;
	private TextView te3;
	private ImageView img2;
	private TextView ti3;
	private LinearLayout ins3;
	private Switch sw1;
	private TextView textview5;
	private LinearLayout linear2;
	private Switch sw3;
	private Switch sw2;
	private TextView textview3;
	private TextView textview4;
	private TextView textview6;
	private TextView textview2;
	private TextView textview1;
	
	private SharedPreferences pref;
	private AlertDialog.Builder dialog;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.set);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		_app_bar = findViewById(R.id._app_bar);
		_coordinator = findViewById(R.id._coordinator);
		_toolbar = findViewById(R.id._toolbar);
		setSupportActionBar(_toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _v) {
				onBackPressed();
			}
		});
		scroller = findViewById(R.id.scroller);
		ground = findViewById(R.id.ground);
		frame1 = findViewById(R.id.frame1);
		frame2 = findViewById(R.id.frame2);
		frame3 = findViewById(R.id.frame3);
		frame4 = findViewById(R.id.frame4);
		frame5 = findViewById(R.id.frame5);
		frame6 = findViewById(R.id.frame6);
		frameb = findViewById(R.id.frameb);
		ti1 = findViewById(R.id.ti1);
		ins1 = findViewById(R.id.ins1);
		te1 = findViewById(R.id.te1);
		edi1 = findViewById(R.id.edi1);
		img1 = findViewById(R.id.img1);
		ti2 = findViewById(R.id.ti2);
		ins2 = findViewById(R.id.ins2);
		te2 = findViewById(R.id.te2);
		te3 = findViewById(R.id.te3);
		img2 = findViewById(R.id.img2);
		ti3 = findViewById(R.id.ti3);
		ins3 = findViewById(R.id.ins3);
		sw1 = findViewById(R.id.sw1);
		textview5 = findViewById(R.id.textview5);
		linear2 = findViewById(R.id.linear2);
		sw3 = findViewById(R.id.sw3);
		sw2 = findViewById(R.id.sw2);
		textview3 = findViewById(R.id.textview3);
		textview4 = findViewById(R.id.textview4);
		textview6 = findViewById(R.id.textview6);
		textview2 = findViewById(R.id.textview2);
		textview1 = findViewById(R.id.textview1);
		pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
		dialog = new AlertDialog.Builder(this);
		
		frame5.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (pref.getString("fset", "").equals("exp")) {
					dialog.setTitle("Stable mode");
					dialog.setMessage("Standard featureset will be restored\n\nProcceed?");
					dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface _dialog, int _which) {
							pref.edit().putString("fset", "lts").commit();
								Toast toast = Toast.makeText(getApplicationContext(), "Please restart", Toast.LENGTH_SHORT);
								toast.show();
							finishAffinity();
						}
					});
					dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface _dialog, int _which) {
							
						}
					});
					dialog.create().show();
				}
				else {
					dialog.setTitle("Experimental mode");
					dialog.setMessage("Activating this mode unlocks features which are not implemented or tested yet!\nBugs and errors may occur \n\nProcceed?");
					dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface _dialog, int _which) {
							pref.edit().putString("fset", "exp").commit();
								Toast toast = Toast.makeText(getApplicationContext(), "Please restart", Toast.LENGTH_SHORT);
								toast.show();
							finishAffinity();
						}
					});
					dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface _dialog, int _which) {
							
						}
					});
					dialog.create().show();
				}
			}
		});
		
		frame6.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				dialog.setTitle("Delete all data");
				dialog.setMessage("Saved data including settings will be set to default.\nThis is irreversible!");
				dialog.setPositiveButton("Cancle", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						
					}
				});
				dialog.setNegativeButton("RESET", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						pref.edit().remove("fset").commit();
						pref.edit().remove("vod").commit();
						pref.edit().remove("lia").commit();
						pref.edit().remove("beke").commit();
						pref.edit().remove("sat").commit();
						pref.edit().remove("ttok").commit();
						pref.edit().remove("vt").commit();
						pref.edit().remove("efr").commit();
						pref.edit().remove("stat").commit();
						pref.edit().remove("lam").commit();
							Toast toast = Toast.makeText(getApplicationContext(), "Please restart", Toast.LENGTH_SHORT);
							toast.show();
						finishAffinity();
					}
				});
				dialog.create().show();
			}
		});
		
		edi1.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				if (edi1.getText().toString().equals(pref.getString("vt", ""))) {
					img1.setVisibility(View.GONE);
				}
				else {
					img1.setVisibility(View.VISIBLE);
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				
			}
			
			@Override
			public void afterTextChanged(Editable _param1) {
				
			}
		});
		
		img1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (edi1.getText().toString().equals("")) {
					((EditText)edi1).setError("This can't be empty");
				}
				else {
					if ((Double.parseDouble(edi1.getText().toString()) < 9999) && (Double.parseDouble(edi1.getText().toString()) > 0.01d)) {
						pref.edit().putString("vt", edi1.getText().toString()).commit();
						Context context = getApplicationContext();
						CharSequence text = "Threshold was set";
						int duration = Toast.LENGTH_SHORT;
						
						Toast toast = Toast.makeText(context, text, duration);
						toast.show();
					}
					else {
						Context context = getApplicationContext();
						CharSequence text = "Out of range";
						int duration = Toast.LENGTH_SHORT;
						
						Toast toast = Toast.makeText(context, text, duration);
						toast.show();
						edi1.setText(pref.getString("vt", ""));
					}
				}
			}
		});
		
		img2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				PopupMenu popup = new PopupMenu(SetActivity.this, img2);
				Menu menu = popup.getMenu();
				menu.add("÷2");
				menu.add("÷3");
				menu.add("÷5");
				menu.add("÷10");
				menu.add("÷20");
				menu.add("÷100");
				menu.add("÷Off");
				popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){
					@Override
					public boolean onMenuItemClick(MenuItem item){
						switch (item.getTitle().toString()){
							case "÷2":
							pref.edit().putString("vod", "2").commit();
							te3.setText("2");
							break;
							case "÷3":
							pref.edit().putString("vod", "3").commit();
							te3.setText("3");
							break;
							case "÷5":
							pref.edit().putString("vod", "5").commit();
							te3.setText("5");
							break;
							case "÷10":
							pref.edit().putString("vod", "10").commit();
							te3.setText("10");
							break;
							case "÷20":
							pref.edit().putString("vod", "20").commit();
							te3.setText("20");
							break;
							case "÷100":
							pref.edit().putString("vod", "100").commit();
							te3.setText("100");
							break;
							case "÷Off":
							pref.edit().putString("vod", "1").commit();
							te3.setText("1");
							break;}
						return true;
					}
				});
				popup.show();
			}
		});
		
		sw1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton _param1, boolean _param2) {
				final boolean _isChecked = _param2;
				if (_isChecked) {
					pref.edit().putString("lia", "true").commit();
				}
				else {
					pref.edit().putString("lia", "false").commit();
				}
			}
		});
		
		sw3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton _param1, boolean _param2) {
				final boolean _isChecked = _param2;
				if (_isChecked) {
					pref.edit().putString("beke", "true").commit();
				}
				else {
					pref.edit().putString("beke", "false").commit();
				}
			}
		});
		
		sw2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton _param1, boolean _param2) {
				final boolean _isChecked = _param2;
				if (_isChecked) {
					pref.edit().putString("sat", "true").commit();
				}
				else {
					pref.edit().putString("sat", "false").commit();
					pref.edit().remove("ttok").commit();
				}
			}
		});
	}
	
	private void initializeLogic() {
		setTitle("Settings");
		_ImeOptionAction(edi1);
		dialog = new AlertDialog.Builder(this,AlertDialog.THEME_DEVICE_DEFAULT_DARK);
		frame2.setVisibility(View.GONE);
		edi1.setText(pref.getString("vt", ""));
		te3.setText(pref.getString("vod", ""));
		textview4.setVisibility(View.INVISIBLE);
		if (pref.getString("sat", "").equals("true")) {
			sw1.setChecked(true);
		}
		if (pref.getString("sat", "").equals("true")) {
			sw2.setChecked(true);
		}
		if (pref.getString("beke", "").equals("true")) {
			sw3.setChecked(true);
		}
		if (pref.getString("fset", "").equals("exp")) {
			textview4.setVisibility(View.VISIBLE);
			frame5.setVisibility(View.VISIBLE);
		}
		edi1.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)3, 0xFFBDBDBD, Color.TRANSPARENT));
		frame1.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)35, 0xFF252525));
		frame2.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)35, 0xFF252525));
		frame3.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)35, 0xFF252525));
		frame4.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)35, 0xFF252525));
		frame5.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)35, 0xFF252525));
		frame6.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)35, 0xFF252525));
		frameb.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)35, 0xFF012B4E));
	}
	
	public void _ImeOptionAction(final TextView _edittext) {
		_edittext.setOnEditorActionListener(new DoneOnEditorActionListener());
	}
	class DoneOnEditorActionListener implements TextView.OnEditorActionListener {
		    @Override
		    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
			
			        if (actionId == android.view.inputmethod.EditorInfo.IME_ACTION_SEND) {
				   
				img1.performClick();
				         return true;
				        }
			        
			return false;
			    }
	}
	
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	@Deprecated
	public int getLocationX(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}
	
	@Deprecated
	public int getLocationY(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}
	
	@Deprecated
	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}
	
	@Deprecated
	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
			_result.add((double)_arr.keyAt(_iIdx));
		}
		return _result;
	}
	
	@Deprecated
	public float getDip(int _input) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels() {
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels() {
		return getResources().getDisplayMetrics().heightPixels;
	}
}