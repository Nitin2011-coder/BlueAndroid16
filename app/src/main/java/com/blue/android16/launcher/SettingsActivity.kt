package com.blue.android16.launcher
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
class SettingsActivity : AppCompatActivity(){
    private val PICK = 1001
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        val prefs = getSharedPreferences("blue16_settings", MODE_PRIVATE)
        val pinInput = findViewById<EditText>(R.id.pin_input)
        val preview = findViewById<ImageView>(R.id.wallpaper_preview)
        pinInput.setText(prefs.getString("custom_pin","1234"))
        prefs.getString("lock_wallpaper_uri",null)?.let{ preview.setImageURI(Uri.parse(it)) }

        findViewById<Button>(R.id.btn_save_pin).setOnClickListener{
            val newPin = pinInput.text.toString()
            if(newPin.length >=4){ prefs.edit().putString("custom_pin",newPin).apply()
                Toast.makeText(this,"PIN Saved: $newPin",Toast.LENGTH_SHORT).show()
            }else Toast.makeText(this,"Min 4 digits",Toast.LENGTH_SHORT).show()
        }
        findViewById<Button>(R.id.btn_change_wallpaper).setOnClickListener{
            val i = Intent(Intent.ACTION_OPEN_DOCUMENT); i.type="image/*"; i.addCategory(Intent.CATEGORY_OPENABLE)
            startActivityForResult(i,PICK)
        }
        findViewById<Button>(R.id.btn_reset).setOnClickListener{
            prefs.edit().remove("lock_wallpaper_uri").putString("custom_pin","1234").apply()
            preview.setImageResource(R.drawable.wallpaper_blue); pinInput.setText("1234")
            Toast.makeText(this,"Reset Done",Toast.LENGTH_SHORT).show()
        }
    }
    override fun onActivityResult(r:Int, res:Int, d:Intent?){
        super.onActivityResult(r,res,d)
        if(r==PICK && res==Activity.RESULT_OK){
            val uri = d?.data; if(uri!=null){
                try{ contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION) }catch(e:Exception){}
                getSharedPreferences("blue16_settings", MODE_PRIVATE).edit().putString("lock_wallpaper_uri",uri.toString()).apply()
                findViewById<ImageView>(R.id.wallpaper_preview).setImageURI(uri)
                Toast.makeText(this,"Wallpaper Changed!",Toast.LENGTH_SHORT).show()
            }
        }
    }
}
