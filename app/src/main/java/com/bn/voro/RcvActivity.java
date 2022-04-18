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
import android.widget.ImageView;
import com.google.android.material.button.*;
import android.widget.TextView;
import android.widget.EditText;
import android.content.Intent;
import android.net.Uri;
import java.util.Timer;
import java.util.TimerTask;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.View;
import android.text.Editable;
import android.text.TextWatcher;
import com.google.gson.Gson;
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

public class RcvActivity extends AppCompatActivity {
	
	private Timer _timer = new Timer();
	
	private HashMap<String, Object> pull_map = new HashMap<>();
	private double recieved_sig = 0;
	private boolean servcn = false;
	private String jbuilder = "";
	private boolean Bcon = false;
	private String servsend = "";
	
	private ArrayList<HashMap<String, Object>> recall = new ArrayList<>();
	
	private LinearLayout linear1;
	private LinearLayout linear2;
	private LinearLayout ble_frame;
	private LinearLayout serv_frame;
	private ImageView imageview1;
	private MaterialButton bt1;
	private TextView textview1;
	private MaterialButton bt2;
	private EditText edittext1;
	private TextView textview5;
	
	private Intent intent = new Intent();
	private BluetoothConnect ble;
	private BluetoothConnect.BluetoothConnectionListener _ble_bluetooth_connection_listener;
	private TimerTask timer;
	private AlertDialog.Builder dialog;
	private RequestNetwork net;
	private RequestNetwork.RequestListener _net_request_listener;
	private SharedPreferences pref;
	private RequestNetwork voget;
	private RequestNetwork.RequestListener _voget_request_listener;
	private TimerTask timer2;
	private Notification notifyer;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.rcv);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		linear1 = findViewById(R.id.linear1);
		linear2 = findViewById(R.id.linear2);
		ble_frame = findViewById(R.id.ble_frame);
		serv_frame = findViewById(R.id.serv_frame);
		imageview1 = findViewById(R.id.imageview1);
		bt1 = findViewById(R.id.bt1);
		textview1 = findViewById(R.id.textview1);
		bt2 = findViewById(R.id.bt2);
		edittext1 = findViewById(R.id.edittext1);
		textview5 = findViewById(R.id.textview5);
		ble = new BluetoothConnect(this);
		dialog = new AlertDialog.Builder(this);
		net = new RequestNetwork(this);
		pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
		voget = new RequestNetwork(this);
		
		bt1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (bt1.getText().toString().equals("Stop")) {
					ble.stopConnection(_ble_bluetooth_connection_listener, "ble_voro");
					bt1.setText("Start");
					textview1.setText("Service stopped");
				}
				else {
					if (ble.isBluetoothActivated()) {
						ble.readyConnection(_ble_bluetooth_connection_listener, "ble_voro");
						bt1.setText("Stop");
						textview1.setText("Waiting for connection");
						imageview1.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)15, (int)6, 0xFF00E676, Color.TRANSPARENT));
					}
					else {
						imageview1.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)15, (int)6, 0xFFFAFAFA, Color.TRANSPARENT));
						ble.activateBluetooth();
					}
				}
			}
		});
		
		bt2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (bt2.getText().toString().equals("Connect")) {
					if (FunkikwareUtil.isConnected(getApplicationContext())) {
						if (edittext1.getText().toString().length() == 6) {
							pref.edit().putString("ttok", edittext1.getText().toString()).commit();
							pull_map = new HashMap<>();
							pull_map.put("tok", "null");
							jbuilder = new Gson().toJson(pull_map);
							pull_map.clear();
						}
						else {
							((EditText)edittext1).setError("Invalid length");
						}
					}
					else {
						Context context = getApplicationContext();
						CharSequence text = "No connection";
						int duration = Toast.LENGTH_SHORT;
						
						Toast toast = Toast.makeText(context, text, duration);
						toast.show();
					}
				}
				else {
					bt2.setText("Connect");
					edittext1.setEnabled(true);
					servcn = false;
				}
			}
		});
		
		edittext1.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				if (edittext1.getText().toString().length() > 6) {
					edittext1.setText(edittext1.getText().toString().substring((int)(0), (int)(6)));
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				
			}
			
			@Override
			public void afterTextChanged(Editable _param1) {
				
			}
		});
		
		_ble_bluetooth_connection_listener = new BluetoothConnect.BluetoothConnectionListener() {
			@Override
			public void onConnected(String _param1, HashMap<String, Object> _param2) {
				final String _tag = _param1;
				final HashMap<String, Object> _deviceData = _param2;
				Context context = getApplicationContext();
				CharSequence text = "Device connected successfully";
				int duration = Toast.LENGTH_LONG;
				
				Toast toast = Toast.makeText(context, text, duration);
				toast.show();
				textview1.setText("Connected");
				moveTaskToBack(true);
				Bcon = true;
			}
			
			@Override
			public void onDataReceived(String _param1, byte[] _param2, int _param3) {
				final String _tag = _param1;
				final String _data = new String(_param2, 0, _param3);
				if (_tag.contains("ble_voro")) {
					_VolumeControll(Double.parseDouble(_data.replace(",", ".")));
					recieved_sig = Double.parseDouble(_data.replace(",", "."));
				}
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
				bt1.setText("Start");
				textview1.setText("Service stopped");
				Bcon = false;
				ble.readyConnection(_ble_bluetooth_connection_listener, "ble_voro");
			}
			
			@Override
			public void onConnectionStopped(String _param1) {
				final String _tag = _param1;
				bt1.setText("Start");
				textview1.setText("Service stopped");
				Bcon = false;
				ble.readyConnection(_ble_bluetooth_connection_listener, "ble_voro");
			}
		};
		
		_net_request_listener = new RequestNetwork.RequestListener() {
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
		
		_voget_request_listener = new RequestNetwork.RequestListener() {
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
		setTitle("Reciever");
		ble_frame.setVisibility(View.GONE);
		serv_frame.setVisibility(View.GONE);
		textview5.setVisibility(View.GONE);
		bt1.setBackgroundColor(0xFFE7644A);
		bt2.setBackgroundColor(0xFFE7644A);
		if (getIntent().getStringExtra("cm").equals("ble")) {
			_blem();
			imageview1.setImageResource(R.drawable.ic_bluetooth);
			ble_frame.setVisibility(View.VISIBLE);
			recieved_sig = -3;
			imageview1.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)15, (int)6, 0xFFF5F5F5, Color.TRANSPARENT));
		}
		if (getIntent().getStringExtra("cm").equals("srv")) {
			_servm();
			imageview1.setImageResource(R.drawable.ic_serv);
			serv_frame.setVisibility(View.VISIBLE);
			imageview1.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)15, (int)6, 0xFFF5F5F5, Color.TRANSPARENT));
		}
		if (getIntent().getStringExtra("cm").equals("wfd")) {
			_wfdm();
			imageview1.setImageResource(R.drawable.ic_wfd);
			imageview1.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)15, (int)6, 0xFFF5F5F5, Color.TRANSPARENT));
		}
		if (pref.getString("lia", "").equals("true")) {
			
		}
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		ble.stopConnection(_ble_bluetooth_connection_listener, "ble_voro");
	}
	
	@Override
	public void onBackPressed() {
		if (textview1.getText().toString().equals("Connected")) {
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
	
	public void _VolumeControll(final double _num) {
		AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		
		audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, (int)_num, 0);
	}
	
	
	public void _blem() {
		if (ble.isBluetoothActivated()) {
			timer = new TimerTask() {
				@Override
				public void run() {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							imageview1.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)15, (int)6, 0xFF00E676, Color.TRANSPARENT));
							ble.readyConnection(_ble_bluetooth_connection_listener, "ble_voro");
						}
					});
				}
			};
			_timer.schedule(timer, (int)(800));
		}
		else {
			ble.activateBluetooth();
			textview1.setText("Service stopped");
			bt1.setText("Start");
		}
	}
	
	
	public void _wfdm() {
		intent.setClass(getApplicationContext(), MainActivity.class);
		startActivity(intent);
		finish();
	}
	
	
	public void _servm() {
		if (pref.contains("ttok") && pref.getString("sat", "").equals("true")) {
			edittext1.setText(pref.getString("ttok", ""));
		}
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
								imageview1.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)15, (int)6, 0xFF00E676, Color.TRANSPARENT));
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
								
				            }
			        });
		bt2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				servsend = "hello";
				wsManager.sendMessage(servsend);
			}
		});
	}
	
	
	public void _ImeOptionAction(final TextView _edittext) {
		_edittext.setOnEditorActionListener(new DoneOnEditorActionListener());
	}
	class DoneOnEditorActionListener implements TextView.OnEditorActionListener {
		    @Override
		    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
			        if (actionId == android.view.inputmethod.EditorInfo.IME_ACTION_SEND) {
				   
				bt2.performClick();
				         return true;
				        }
			        
			return false;
			    }
	}
	
	
	public void _responseManager(final String _input) {
		
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