package ir.lococoder.eplayer.system.fonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import java.util.regex.Pattern;

import androidx.appcompat.widget.AppCompatTextView;
import ir.lococoder.eplayer.common.Common;
import ir.lococoder.eplayer.system.Config;

import static ir.lococoder.eplayer.system.Config.LANG_FA;


public class Light extends AppCompatTextView {

  public Light(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    init();
  }

  public Light(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public Light(Context context) {
    super(context);
    init();
  }

  public void init() {
    if (Common.getSharedParams(getContext(), Config.SHARAED_PARAMS_language).equals(LANG_FA)){
      Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/IRANSansMobile_Light.ttf");
      setTypeface(tf ,1);
    }else {
      String text = getText().toString();
      Pattern patternAlphaNumericCheck = Pattern.compile("^[a-zA-Z0-9:.]*$");
      if (!patternAlphaNumericCheck.matcher(text).matches()) {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/IRANSansMobile_Light.ttf");
        setTypeface(tf, Typeface.NORMAL);
      }else {
      Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Montserrat-Light.ttf");
      setTypeface(tf, 1);
    }
  }}
}