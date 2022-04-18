package com.bn.voro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.*;
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
import java.util.ArrayList;
import java.util.HashMap;
import android.widget.LinearLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;
import android.net.Uri;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.app.Activity;
import android.content.SharedPreferences;
import android.animation.ObjectAnimator;
import android.view.animation.LinearInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.View;
import java.text.DecimalFormat;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.jetbrains.kotlin.*;
import okhttp3.*;
import okio.*;
import com.goodiebag.protractorview.*;
import com.rabtman.wsmanager.*;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;

public class MainActivity extends AppCompatActivity {
	
	private String title = "";
	
	private ArrayList<HashMap<String, Object>> lstmap = new ArrayList<>();
	
	private LinearLayout linear1;
	private LinearLayout linear6;
	private ImageView imageview1;
	private LinearLayout linear3;
	private ImageView imageview4;
	private TextView textview2;
	private LinearLayout linear4;
	private ImageView imageview3;
	private LinearLayout linear7;
	private ImageView imageview2;
	private TextView textview1;
	private RadioButton radiobutton1;
	private RadioButton radiobutton2;
	private RadioButton radiobutton3;
	private TextView textview3;
	private TextView textview4;
	
	private Intent intent = new Intent();
	private AlertDialog.Builder dialog;
	private BluetoothConnect ble;
	private BluetoothConnect.BluetoothConnectionListener _ble_bluetooth_connection_listener;
	private SharedPreferences pref;
	private RequestNetwork net;
	private RequestNetwork.RequestListener _net_request_listener;
	private ObjectAnimator anima = new ObjectAnimator();
	private AudioManager am;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.main);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		linear1 = findViewById(R.id.linear1);
		linear6 = findViewById(R.id.linear6);
		imageview1 = findViewById(R.id.imageview1);
		linear3 = findViewById(R.id.linear3);
		imageview4 = findViewById(R.id.imageview4);
		textview2 = findViewById(R.id.textview2);
		linear4 = findViewById(R.id.linear4);
		imageview3 = findViewById(R.id.imageview3);
		linear7 = findViewById(R.id.linear7);
		imageview2 = findViewById(R.id.imageview2);
		textview1 = findViewById(R.id.textview1);
		radiobutton1 = findViewById(R.id.radiobutton1);
		radiobutton2 = findViewById(R.id.radiobutton2);
		radiobutton3 = findViewById(R.id.radiobutton3);
		textview3 = findViewById(R.id.textview3);
		textview4 = findViewById(R.id.textview4);
		dialog = new AlertDialog.Builder(this);
		ble = new BluetoothConnect(this);
		pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
		net = new RequestNetwork(this);
		
		imageview3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				anima.setTarget(imageview3);
				anima.setPropertyName("scaleX");
				anima.setFloatValues((float)(1));
				anima.setFloatValues((float)(1), (float)(2));
				anima.setDuration((int)(250));
				anima.start();
				intent.setClass(getApplicationContext(), SetActivity.class);
				startActivity(intent);
				anima.setFloatValues((float)(1));
			}
		});
		
		imageview2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				anima.setTarget(imageview2);
				anima.setPropertyName("scaleX");
				anima.setFloatValues((float)(1));
				anima.setFloatValues((float)(1), (float)(2));
				anima.setDuration((int)(250));
				anima.start();
				DialogDialogFragmentActivity dialog = new DialogDialogFragmentActivity();
				dialog.show(getSupportFragmentManager(),"1");
				anima.setFloatValues((float)(1));
			}
		});
		
		radiobutton1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				radiobutton2.setChecked(false);
				radiobutton3.setChecked(false);
				pref.edit().putString("lam", "ble").commit();
			}
		});
		
		radiobutton2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				radiobutton1.setChecked(false);
				radiobutton3.setChecked(false);
				pref.edit().putString("lam", "srv").commit();
			}
		});
		
		radiobutton3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				radiobutton1.setChecked(false);
				radiobutton2.setChecked(false);
				pref.edit().putString("lam", "wfd").commit();
			}
		});
		
		textview3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				textview3.setScaleX((float)(0.8d));
				textview3.setScaleY((float)(0.8d));
				if (radiobutton1.isChecked()) {
					intent.putExtra("cm", "ble");
				}
				if (radiobutton2.isChecked()) {
					intent.putExtra("cm", "srv");
				}
				if (radiobutton3.isChecked()) {
					intent.putExtra("cm", "wfd");
				}
				intent.setClass(getApplicationContext(), RcvActivity.class);
				startActivity(intent);
				finish();
			}
		});
		
		textview4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				textview4.setScaleX((float)(0.8d));
				textview4.setScaleY((float)(0.8d));
				if (radiobutton1.isChecked()) {
					intent.putExtra("cm", "ble");
					intent.setClass(getApplicationContext(), CtlActivity.class);
					startActivity(intent);
					finish();
				}
				if (radiobutton2.isChecked()) {
					if (FunkikwareUtil.isConnected(getApplicationContext())) {
						intent.putExtra("cm", "srv");
						intent.setClass(getApplicationContext(), CtlActivity.class);
						startActivity(intent);
						finish();
					}
					else {
						Context context = getApplicationContext();
						CharSequence text = "No connection";
						int duration = Toast.LENGTH_SHORT;
						
						Toast toast = Toast.makeText(context, text, duration);
						toast.show();
						textview4.setScaleX((float)(1));
						textview4.setScaleY((float)(1));
					}
				}
				if (radiobutton3.isChecked()) {
					intent.putExtra("cm", "wfd");
					intent.setClass(getApplicationContext(), CtlActivity.class);
					startActivity(intent);
					finish();
				}
			}
		});
		
		_ble_bluetooth_connection_listener = new BluetoothConnect.BluetoothConnectionListener() {
			@Override
			public void onConnected(String _param1, HashMap<String, Object> _param2) {
				final String _tag = _param1;
				final HashMap<String, Object> _deviceData = _param2;
				
			}
			
			@Override
			public void onDataReceived(String _param1, byte[] _param2, int _param3) {
				final String _tag = _param1;
				final String _data = new String(_param2, 0, _param3);
				
			}
			
			@Override
			public void onDataSent(String _param1, byte[] _param2) {
				final String _tag = _param1;
				final String _data = new String(_param2);
				
			}
			
			@Override
			public void onConnectionError(String _param1, String _param2, String _param3) {
				final String _tag = _param1;
				final String _connectionState = _param2;
				final String _errorMessage = _param3;
				
			}
			
			@Override
			public void onConnectionStopped(String _param1) {
				final String _tag = _param1;
				
			}
		};
		
		_net_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				final String _tag = _param1;
				final String _response = _param2;
				final HashMap<String, Object> _responseHeaders = _param3;
				if ((_response.length() > 10) && _response.contains("{")) {
					lstmap = new Gson().fromJson("[".concat(_response.concat("]")), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
					if (!lstmap.get((int)0).get("Info").toString().equals("none")) {
						if (lstmap.get((int)0).get("Info").toString().equals("info")) {
							textview2.setVisibility(View.VISIBLE);
							textview2.setText(lstmap.get((int)0).get("Text").toString());
						}
						if (lstmap.get((int)0).get("Info").toString().equals("warn")) {
							textview2.setVisibility(View.VISIBLE);
							imageview4.setVisibility(View.VISIBLE);
							textview2.setTextColor(0xFFFF9800);
							textview2.setText(lstmap.get((int)0).get("Text").toString());
						}
					}
					if (lstmap.get((int)0).get("Status").toString().equals("MT")) {
						pref.edit().putString("stat", "m").commit();
						radiobutton2.setEnabled(false);
						radiobutton2.setText("Server [under maintenance]");
					}
					if (lstmap.get((int)0).get("Status").toString().equals("NORMAL")) {
						pref.edit().putString("stat", "0").commit();
					}
					if (!lstmap.get((int)0).get("Version").toString().equals("1.0")) {
						dialog.setTitle("UPDATE");
						dialog.setMessage("There is a newer version available!\nPlease download the update to increase performance and maintain security.");
						dialog.setPositiveButton("I understood", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface _dialog, int _which) {
								
							}
						});
						dialog.create().show();
					}
				}
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				
			}
		};
	}
	
	private void initializeLogic() {
		am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		textview2.setVisibility(View.INVISIBLE);
		imageview4.setVisibility(View.INVISIBLE);
		textview2.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c) { this.setStroke(a, b); this.setColor(c); return this; } }.getIns((int)10, 0xFFFFFFFF, Color.TRANSPARENT));
		_RoundCorner(imageview2, "#0381FE", "#0381FE", 20, 0, 0, 20);
		_RoundCorner(imageview3, "#0381FE", "#0381FE", 0, 20, 20, 0);
		_UltraStyle(textview3, true, 70, 0, "#FFE7644A");
		_UltraStyle(textview4, true, 70, 0, "#FFE7644A");
		dialog = new AlertDialog.Builder(this,AlertDialog.THEME_DEVICE_DEFAULT_DARK);
		if (!pref.contains("fset")) {
			pref.edit().putString("fset", "lts").commit();
		}
		if (pref.getString("vt", "").equals("")) {
			pref.edit().putString("vt", String.valueOf(am.getStreamMaxVolume(AudioManager.STREAM_MUSIC))).commit();
		}
		if (pref.getString("vod", "").equals("")) {
			pref.edit().putString("vod", "1").commit();
		}
		if (!pref.contains("bop")) {
			pref.edit().putString("bop", "1").commit();
			Intent callSettingIntent= new Intent(android.provider.Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS);
			       startActivity(callSettingIntent);
				Toast toast = Toast.makeText(getApplicationContext(), "Disable battery optimization for the best experience!", Toast.LENGTH_LONG);
				toast.show();
		}
		if (pref.getString("stat", "").equals("m")) {
			radiobutton2.setEnabled(false);
			radiobutton2.setText("Server [under maintenance]");
		}
		net.startRequestNetwork(RequestNetworkController.GET, "http://tho-onserv.tk/app-voro/callback.php", "", _net_request_listener);
		if (getIntent().hasExtra("fail")) {
			dialog.setTitle("Warning");
			dialog.setMessage("An error has occurred!\nReason:\n".concat(getIntent().getStringExtra("fail")));
			dialog.setPositiveButton("Close ", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface _dialog, int _which) {
					finishAffinity();
				}
			});
			dialog.create().show();
		}
		if (pref.getString("fset", "").equals("exp")) {
			radiobutton3.setEnabled(true);
			radiobutton3.setAlpha((float)(1));
			imageview4.setVisibility(View.VISIBLE);
		}
		if (pref.getString("lam", "").equals("wfd")) {
			radiobutton3.setChecked(true);
		}
		else {
			if (pref.getString("lam", "").equals("srv")) {
				radiobutton2.setChecked(true);
			}
			else {
				if (!ble.isBluetoothEnabled()) {
					radiobutton1.setEnabled(false);
					radiobutton2.setChecked(true);
					radiobutton1.setText("Bluetooth (unsupported)");
				}
				else {
					radiobutton1.setChecked(true);
				}
			}
		}
	}
	
	public void _RoundCorner(final View _view, final String _bg, final String _br, final double _tl, final double _tr, final double _brr, final double _bl) {
		android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
		gd.setColor(Color.parseColor(_bg));
		gd.setStroke(2, Color.parseColor(_br));
		
		gd.setCornerRadii(new float[]{(int)_tl,(int)_tl,(int)_tr,(int)_tr,(int)_brr,(int)_brr,(int)_bl,(int)_bl});
		
		_view.setBackground(gd);
	}
	
	
	public void _UltraStyle(final View _view, final boolean _ripple, final double _radius, final double _shadow, final String _color) {
		if (_ripple) {
			android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
			gd.setColor(Color.parseColor(_color));
			gd.setCornerRadius((int)_radius);
			_view.setElevation((int)_shadow);
			android.content.res.ColorStateList clrb = new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{Color.parseColor("#9e9e9e")});
			android.graphics.drawable.RippleDrawable ripdrb = new android.graphics.drawable.RippleDrawable(clrb , gd, null);
			_view.setClickable(true);
			_view.setBackground(ripdrb);
		}
		else {
			android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
			gd.setColor(Color.parseColor(_color));
			gd.setCornerRadius((int)_radius);
			_view.setBackground(gd);
			_view.setElevation((int)_shadow);
		}
	}
	
	
	public void _cmd() {
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