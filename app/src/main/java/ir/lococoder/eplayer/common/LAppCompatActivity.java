package ir.lococoder.eplayer.common;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;
import ir.lococoder.eplayer.system.LBase;


public  class LAppCompatActivity extends AppCompatActivity {


// //set fonts
//  @Override
//  protected void attachBaseContext(Context newBase) {
//    super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
//  }

  @Override
  public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
    super.onCreate(savedInstanceState, persistentState);
  }

  @Override
  protected void onResume() {
    super.onResume();

    LBase.setCurrentActivity(this);
  }




  public static class Founder {
    private final Activity activity;
    private int[] features;
    private boolean noTitlebar;
    private boolean noActionbar;
    private boolean fullscreen;
    private int layoutId;
    private Object ui;

    public Founder(Activity activity) {
      this.activity = activity;
    }

    public Founder requestFeatures(int... features) {
      this.features = features;
      return this;
    }

    public Founder noTitlebar() {
      this.noTitlebar = true;
      return this;
    }

    public Founder noActionbar() {
      this.noActionbar = true;
      return this;
    }
    public Founder setFonts() {

      this.noActionbar = true;
      return this;
    }

    public Founder fullscreen() {
      this.fullscreen = true;
      return this;
    }

    public Founder contentView(@LayoutRes int layoutResID) {
      this.layoutId = layoutResID;
      return this;
    }

    public Founder extractUi(Object ui) {
      this.ui = ui;
      return this;
    }

    public Founder build() {
      for (int feature: this.features) {
        activity.getWindow().requestFeature(feature);
      }

      if (noTitlebar) {
        activity.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
      }

      if (fullscreen) {
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
      }

      if (noActionbar) {
        activity.getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

        {
          ActionBar actionBar = activity.getActionBar();
          if (actionBar != null) {
            actionBar.hide();
          }
        }

        if (activity instanceof AppCompatActivity) {
          AppCompatActivity castedActivity = (AppCompatActivity) activity;
          androidx.appcompat.app.ActionBar actionBar = castedActivity.getSupportActionBar();
          if (actionBar != null) {
            actionBar.hide();
          }
        }
      }

      activity.setContentView(layoutId);

      // reflect ui elements
      {
        Class clazz = ui.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
          field.setAccessible(true);
          String name = field.getName();
          Class type = field.getType();
          if (name.contains("$")) {
            continue;
          }

          int id = LBase.get().getResources().getIdentifier(name, "id", LBase.get().getPackageName());
          try {
            field.set(ui, activity.findViewById(id));
          } catch (IllegalAccessException e) {
            e.printStackTrace();
          }
        }
      }

      return this;
    }
  }

}
