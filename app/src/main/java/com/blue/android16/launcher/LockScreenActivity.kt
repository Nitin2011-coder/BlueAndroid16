package com.blue.android16.launcher
import android.net.Uri
import android.os.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*
class LockScreenActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lockscreen)
        val prefs = getSharedPreferences("blue16_settings", MODE_PRIVATE)
        val customPin = prefs.getString("custom_pin","1234")
        val wallUri = prefs.getString("lock_wallpaper_uri",null)
        val wallView = findViewById<ImageView>(R.id.lock_wallpaper)
        if(wallUri!= null){ try{ wallView.setImageURI(Uri.parse(wallUri)) }catch(e:Exception){} }

        val clock = findViewById<TextView>(R.id.lock_clock)
        Timer().scheduleAtFixedRate(object:TimerTask(){ override fun run(){ runOnUiThread{
            clock.text = SimpleDateFormat("hh:mm:ss", Locale.getDefault()).format(Date())
        }}},0,1000)
        findViewById<ImageView>(R.id.fingerprint_icon).setOnClickListener{
            (getSystemService(VIBRATOR_SERVICE) as Vibrator).vibrate(100)
            Toast.makeText(this,"Unlocked",Toast.LENGTH_SHORT).show(); finish()
        }
        findViewById<Button>(R.id.btn_unlock).setOnClickListener{
            val entered = findViewById<EditText>(R.id.pin_input).text.toString()
            if(entered == customPin) finish() else Toast.makeText(this,"Wrong PIN",Toast.LENGTH_SHORT).show()
        }
    }
}
