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
import android.widget.LinearLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;
import android.net.Uri;
import android.animation.ObjectAnimator;
import android.view.animation.LinearInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.View;
import org.jetbrains.kotlin.*;
import okhttp3.*;
import okio.*;
import com.goodiebag.protractorview.*;
import com.rabtman.wsmanager.*;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;

public class DialogDialogFragmentActivity extends DialogFragment {
	
	private String title = "";
	private double goup = 0;
	
	private LinearLayout back;
	private ImageView img2;
	private ImageView img1;
	private TextView te2;
	private TextView te3;
	
	private Intent intent = new Intent();
	private ObjectAnimator animate = new ObjectAnimator();
	
	@NonNull
	@Override
	public View onCreateView(@NonNull LayoutInflater _inflater, @Nullable ViewGroup _container, @Nullable Bundle _savedInstanceState) {
		View _view = _inflater.inflate(R.layout.dialog_dialog_fragment, _container, false);
		initialize(_savedInstanceState, _view);
		initializeLogic();
		return _view;
	}
	
	private void initialize(Bundle _savedInstanceState, View _view) {
		back = _view.findViewById(R.id.back);
		img2 = _view.findViewById(R.id.img2);
		img1 = _view.findViewById(R.id.img1);
		te2 = _view.findViewById(R.id.te2);
		te3 = _view.findViewById(R.id.te3);
		
		img2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				getDialog().dismiss();
			}
		});
		
		img1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				goup++;
				if (goup > 4) {
					img1.setVisibility(View.INVISIBLE);
					te3.setVisibility(View.INVISIBLE);
					te2.setText("Tommys lehrreiche Lehrfilme YT(Ger)\n\nhttps://t.me/VoRoEE\nThis app is not related to Samsung in any kind!");
				}
			}
		});
	}
	
	private void initializeLogic() {
		Dialog dialog = getDialog();
		  if (dialog != null) { 
			  int width = ViewGroup.LayoutParams.MATCH_PARENT;
			  int height = ViewGroup.LayoutParams.MATCH_PARENT; 
			  dialog.getWindow().setLayout(width, height);
			dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); 
			   }
		back.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)25, 0xFF252525));
		animate.setTarget(back);
		animate.setPropertyName("alpha");
		animate.setInterpolator(new AccelerateInterpolator());
		animate.setFloatValues((float)(0));
		animate.setFloatValues((float)(0.2d), (float)(1));
		animate.setDuration((int)(300));
		animate.start();
	}
	
	@Override
	public void onStart() {
		super.onStart();
		Dialog dialog = getDialog();
		  if (dialog != null) { 
			  int width = ViewGroup.LayoutParams.MATCH_PARENT;
			  int height = ViewGroup.LayoutParams.MATCH_PARENT; 
			  dialog.getWindow().setLayout(width, height);
			dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); 
			   }
	}
}