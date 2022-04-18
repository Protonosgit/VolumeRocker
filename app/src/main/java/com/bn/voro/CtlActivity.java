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
import java.util.HashMap;
import java.util.ArrayList;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import com.google.android.material.button.*;
import android.widget.TextView;
import android.content.Intent;
import android.net.Uri;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.app.Activity;
import android.content.SharedPreferences;
import java.util.Timer;
import java.util.TimerTask;
import android.widget.AdapterView;
import android.view.View;
import java.text.DecimalFormat;
import org.jetbrains.kotlin.*;
import okhttp3.*;
import okio.*;
import com.goodiebag.protractorview.*;
import com.rabtman.wsmanager.*;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;
import com.rabtman.wsmanager.WsManager;
import com.rabtman.wsmanager.listener.WsStatusListener;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okio.ByteString;

public class CtlActivity extends AppCompatActivity {
	
	private Timer _timer = new Timer();
	
	private String ble_name = "";
	private double ctm = 0;
	private HashMap<String, Object> serv_push = new HashMap<>();
	private double sid = 0;
	private boolean connected = false;
	private HashMap<String, Object> ctlmap = new HashMap<>();
	private String token = "";
	private boolean Bcon = false;
	private String servsend = "";
	private boolean Scon = false;
	private double Svol = 0;
	private double progress = 0;
	private double pkeep = 0;
	
	private ArrayList<HashMap<String, Object>> blelist = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> sermap = new ArrayList<>();
	
	private LinearLayout linear1;
	private LinearLayout linear2;
	private ScrollView vscroll1;
	private LinearLayout linear5;
	private ImageView imageview1;
	private LinearLayout scrolli;
	private LinearLayout ble_frame;
	private LinearLayout serv_frame;
	private LinearLayout linear4;
	private ListView listview1;
	private MaterialButton bt1;
	private TextView textview1;
	private MaterialButton bt2;
	private TextView textview3;
	private ProtractorView protractorView;
	private TextView indicator;
	
	private Intent intent = new Intent();
    private WsManager wsManager;
	private BluetoothConnect ble;
	private BluetoothConnect.BluetoothConnectionListener _ble_bluetooth_connection_listener;
	private AlertDialog.Builder dialog;
	private SharedPreferences pref;
	private RequestNetwork serv;
	private RequestNetwork.RequestListener _serv_request_listener;
	private TimerTask timer;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.ctl);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		linear1 = findViewById(R.id.linear1);
		linear2 = findViewById(R.id.linear2);
		vscroll1 = findViewById(R.id.vscroll1);
		linear5 = findViewById(R.id.linear5);
		imageview1 = findViewById(R.id.imageview1);
		scrolli = findViewById(R.id.scrolli);
		ble_frame = findViewById(R.id.ble_frame);
		serv_frame = findViewById(R.id.serv_frame);
		linear4 = findViewById(R.id.linear4);
		listview1 = findViewById(R.id.listview1);
		bt1 = findViewById(R.id.bt1);
		textview1 = findViewById(R.id.textview1);
		bt2 = findViewById(R.id.bt2);
		textview3 = findViewById(R.id.textview3);
		protractorView = findViewById(R.id.protractorView);
		indicator = findViewById(R.id.indicator);
		ble = new BluetoothConnect(this);
		dialog = new AlertDialog.Builder(this);
		pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
		serv = new RequestNetwork(this);
		
		linear5.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				
			}
		});
		
		listview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				if (ble.isBluetoothActivated()) {
					if (blelist.get((int)_position).get("address").toString().equals("dnl")) {
						dialog.setTitle("Your device is not in this list?");
						dialog.setMessage("× connect to your device over bluetooth settings (new devices need to be registered)\n× double check the bluetooth name of your target");
						dialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface _dialog, int _which) {
								intent.setAction(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS);
								startActivity(intent);
							}
						});
						dialog.create().show();
					}
					else {
						ble.startConnection(_ble_bluetooth_connection_listener, blelist.get((int)_position).get("address").toString(), "ble_voro");
						ble_name = blelist.get((int)_position).get("name").toString();
						textview1.setText("Trying to connect");
						textview1.setTextColor(0xFFFFCA28);
						Context context = getApplicationContext();
						CharSequence text = "please wait...";
						int duration = Toast.LENGTH_LONG;
						
						Toast toast = Toast.makeText(context, text, duration);
						toast.show();
					}
				}
			}
		});
		
		bt1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (bt1.getText().toString().equals("Search")) {
					if (ble.isBluetoothActivated()) {
						imageview1.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)15, (int)6, 0xFF00E676, Color.TRANSPARENT));
						blelist.clear();
						ble.getPairedDevices(blelist);
						ctlmap = new HashMap<>();
						ctlmap.put("name", "Your device is not listed?");
						ctlmap.put("address", "dnl");
						blelist.add(ctlmap);
						listview1.setVisibility(View.VISIBLE);
						listview1.setAdapter(new Listview1Adapter(blelist));
						((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
					}
					else {
						imageview1.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)15, (int)6, 0xFFF5F5F5, Color.TRANSPARENT));
						Context context = getApplicationContext();
						CharSequence text = "Turn on bluetooth first!";
						int duration = Toast.LENGTH_SHORT;
						
						Toast toast = Toast.makeText(context, text, duration);
						toast.show();
						ble.activateBluetooth();
					}
				}
				else {
					blelist.clear();
					ble.stopConnection(_ble_bluetooth_connection_listener, "ble_voro");
					listview1.setAdapter(new Listview1Adapter(blelist));
					bt1.setText("Search");
					((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
				}
			}
		});
		
		_ble_bluetooth_connection_listener = new BluetoothConnect.BluetoothConnectionListener() {
			@Override
			public void onConnected(String _param1, HashMap<String, Object> _param2) {
				final String _tag = _param1;
				final HashMap<String, Object> _deviceData = _param2;
				listview1.setVisibility(View.GONE);
				Context context = getApplicationContext();
				CharSequence text = "Device connected successfully";
				int duration = Toast.LENGTH_SHORT;
				
				Toast toast = Toast.makeText(context, text, duration);
				toast.show();
				bt1.setText("Stop");
				linear5.setVisibility(View.VISIBLE);
				Bcon = true;
				textview1.setText("Target: ".concat(ble_name));
				textview1.setTextColor(0xFF66BB6A);
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
				textview1.setText("Not connected");
				textview1.setTextColor(0xFFFFFFFF);
				linear5.setVisibility(View.GONE);
				Bcon = false;
			}
			
			@Override
			public void onConnectionStopped(String _param1) {
				final String _tag = _param1;
				textview1.setText("Not connected");
				textview1.setTextColor(0xFFEF5350);
				linear5.setVisibility(View.GONE);
				Bcon = false;
			}
		};
		
		_serv_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				final String _tag = _param1;
				final String _response = _param2;
				final HashMap<String, Object> _responseHeaders = _param3;
				
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				
			}
		};
	}
	
	private void initializeLogic() {
		setTitle("Controll");
		linear5.setVisibility(View.GONE);
		ble_frame.setVisibility(View.GONE);
		serv_frame.setVisibility(View.GONE);
		listview1.setVisibility(View.GONE);
		bt1.setBackgroundColor(0xFFE7644A);
		bt2.setBackgroundColor(0xFFE7644A);
		protractorView.setOnProtractorViewChangeListener(new ProtractorView.OnProtractorViewChangeListener() {
			           @Override
			           public void onProgressChanged(ProtractorView pv, int progress, boolean b) {
							   pkeep = progress;
							   if (pref.getString("fset", "").equals("exp")) {
									   _progchamp(progress);
								}
				           	//protractorView's getters can be accessed using pv instance.
				           }
			
			           @Override
			           public void onStartTrackingTouch(ProtractorView pv) {
							   _progchamp(pkeep);
				
				           }
			
			           @Override
			           public void onStopTrackingTouch(ProtractorView pv) {
							   _progchamp(pkeep);
				
				           }
			       });
		connected = false;
		dialog = new AlertDialog.Builder(this,AlertDialog.THEME_DEVICE_DEFAULT_DARK);
		pkeep = 0;
		if (getIntent().getStringExtra("cm").equals("ble")) {
			ble_frame.setVisibility(View.VISIBLE);
			ctm = 0;
			imageview1.setImageResource(R.drawable.ic_bluetooth);
			listview1.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)30, 0xFF3E3E3E));
			imageview1.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)15, (int)6, 0xFFF5F5F5, Color.TRANSPARENT));
			_blem();
		}
		if (getIntent().getStringExtra("cm").equals("srv")) {
			serv_frame.setVisibility(View.VISIBLE);
			ctm = 1;
			imageview1.setImageResource(R.drawable.ic_serv);
			imageview1.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)15, (int)6, 0xFFF5F5F5, Color.TRANSPARENT));
			_servm();
		}
		if (getIntent().getStringExtra("cm").equals("wfd")) {
			ctm = 2;
			imageview1.setImageResource(R.drawable.ic_wfd);
			imageview1.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)15, (int)6, 0xFFF5F5F5, Color.TRANSPARENT));
			_wfdm();
		}
	}
	
	@Override
	public void onBackPressed() {
		if (!(Bcon && Scon)) {
			dialog.setTitle("Warning");
			dialog.setMessage("Do you want to quit this session?");
			dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface _dialog, int _which) {
					ble.stopConnection(_ble_bluetooth_connection_listener, "ble_voro_cmd");
					if (getIntent().getStringExtra("cm").equals("srv")) {
						
					}
					intent.setClass(getApplicationContext(), MainActivity.class);
					startActivity(intent);
					finish();
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
			intent.setClass(getApplicationContext(), MainActivity.class);
			startActivity(intent);
			finish();
		}
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		ble.stopConnection(_ble_bluetooth_connection_listener, "ble_voro");
	}
	
	public void _blem() {
		if (!ble.isBluetoothActivated()) {
			ble.activateBluetooth();
		}
		else {
			imageview1.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)15, (int)6, 0xFF00E676, Color.TRANSPARENT));
		}
	}
	
	
	public void _servm() {
		OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
		                      .pingInterval(2, TimeUnit.SECONDS)
		                      .retryOnConnectionFailure(true)
		                      .build();
		WsManager wsManager = new WsManager.Builder(this)
		                .wsUrl("wss://ws-server.titanfxl.repl.co")
		                .needReconnect(true)
		                .client(okHttpClient)
		                .build();
		wsManager.startConnect();
		wsManager.setWsStatusListener(new WsStatusListener() {
			            @Override
			            public void onOpen(Response response) {
				                super.onOpen(response);
								imageview1.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c) { this.setStroke(a, b); this.setColor(c); return this; } }.getIns((int)8, 0xFF00E676, Color.TRANSPARENT));
								
				            }
			
			            @Override
			            public void onMessage(String text) {
				                super.onMessage(text);
								_responseManager(text);
								
				            }
			
			            @Override
			            public void onReconnect() {
				                super.onReconnect();
								
				            }
			
			            @Override
			            public void onClosed(int code, String reason) {
				                super.onClosed(code, reason);
				            }
			
			            @Override
			            public void onFailure(Throwable t, Response response) {
				                super.onFailure(t, response);
								intent.setClass(getApplicationContext(), MainActivity.class);
								intent.putExtra("fail", "f");
								startActivity(intent);
								finish();
								
				            }
			        });
		bt2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				servsend = "";
				wsManager.sendMessage(servsend);
			}
		});
	}
	
	
	public void _wfdm() {
		intent.setClass(getApplicationContext(), DebugActivity.class);
		intent.putExtra("error", "Feature missing\nStartup failed");
		startActivity(intent);
		finish();
	}
	
	
	public void _progchamp(final double _prog) {
		if (getIntent().getStringExtra("cm").equals("ble")) {
			if (Bcon) {
				ble.sendData(_ble_bluetooth_connection_listener, new DecimalFormat("0.0").format((((_prog / 180) * 100) * Double.parseDouble(pref.getString("vt", ""))) / 100), "ble_voro");
			}
		}
		if (getIntent().getStringExtra("cm").equals("srv")) {
			Svol = (((_prog / 180) * 100) * Double.parseDouble(pref.getString("vt", ""))) / 100;
		}
		if (getIntent().getStringExtra("cm").equals("wfd")) {
			Svol = (((_prog / 180) * 100) * Double.parseDouble(pref.getString("vt", ""))) / 100;
		}
		indicator.setText(String.valueOf((long)((_prog / 180) * 100)).concat(" %"));
		if ("".equals("exp")) {
			
		}
	}
	
	
	public void _responseManager(final String _input) {
		
	}
	
	public class Listview1Adapter extends BaseAdapter {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public Listview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public int getCount() {
			return _data.size();
		}
		
		@Override
		public HashMap<String, Object> getItem(int _index) {
			return _data.get(_index);
		}
		
		@Override
		public long getItemId(int _index) {
			return _index;
		}
		
		@Override
		public View getView(final int _position, View _v, ViewGroup _container) {
			LayoutInflater _inflater = getLayoutInflater();
			View _view = _v;
			if (_view == null) {
				_view = _inflater.inflate(R.layout.ble_devices, null);
			}
			
			final LinearLayout linear1 = _view.findViewById(R.id.linear1);
			final TextView textview1 = _view.findViewById(R.id.textview1);
			
			textview1.setText(blelist.get((int)_position).get("name").toString());
			if (blelist.get((int)_position).get("address").toString().equals("dnl")) {
				textview1.setTextSize((int)22);
				textview1.setTextColor(0xFF0381FE);
			}
			
			return _view;
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