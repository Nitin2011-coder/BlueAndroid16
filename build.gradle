package com.blue.android16.launcher
import android.content.Intent
import android.content.pm.ResolveInfo
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.timer

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val bigClock = findViewById<TextView>(R.id.big_clock)
        val dateText = findViewById<TextView>(R.id.date_text)
        Timer().scheduleAtFixedRate(object:TimerTask(){
            override fun run(){ runOnUiThread{
                bigClock.text = SimpleDateFormat("hh:mm", Locale.getDefault()).format(Date())
                dateText.text = SimpleDateFormat("EEE, d MMM", Locale.getDefault()).format(Date())
            }}
        },0,1000)

        val drawer = findViewById<View>(R.id.app_drawer)
        val drawerBehavior = BottomSheetBehavior.from(drawer)
        drawerBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        val control = findViewById<View>(R.id.control_center)
        val controlBehavior = BottomSheetBehavior.from(control)
        controlBehavior.state = BottomSheetBehavior.STATE_HIDDEN

        val root = findViewById<View>(R.id.home_root)
        var startY = 0f; var pointerCount = 1
        root.setOnTouchListener { _, event ->
            when(event.actionMasked){
                MotionEvent.ACTION_DOWN -> { startY = event.y; pointerCount = event.pointerCount }
                MotionEvent.ACTION_POINTER_DOWN -> { pointerCount = event.pointerCount }
                MotionEvent.ACTION_UP -> {
                    val diff = startY - event.y
                    if(diff > 150){
                        if(pointerCount >= 2){
                            loadApps(); drawerBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                            Toast.makeText(this,"Hidden Mode - Long press to hide apps not added yet",Toast.LENGTH_SHORT).show()
                        }else{
                            loadApps(); drawerBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                        }
                    }
                    if(startY < 150 && event.y - startY > 200){
                        controlBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                    }
                }
            }
            true
        }
        val gesture = GestureDetector(this, object:GestureDetector.SimpleOnGestureListener(){
            override fun onDoubleTap(e: MotionEvent): Boolean {
                startActivity(Intent(this@HomeActivity, LockScreenActivity::class.java)); return true
            }
        })
        findViewById<View>(R.id.double_tap_zone).setOnTouchListener { _, ev -> gesture.onTouchEvent(ev); true }
        findViewById<View>(R.id.settings_icon).setOnClickListener { startActivity(Intent(this, SettingsActivity::class.java)) }

        // Control center taps - Direct ON/OFF with tap
        findViewById<View>(R.id.btn_wifi).setOnClickListener { Toast.makeText(this,"WiFi Toggled",Toast.LENGTH_SHORT).show() }
        findViewById<View>(R.id.btn_data).setOnClickListener { Toast.makeText(this,"Data Toggled - Add code with ConnectivityManager",Toast.LENGTH_SHORT).show() }

        loadApps()
    }
    private fun loadApps(){
        Thread{
            val pm = packageManager
            val intent = Intent(Intent.ACTION_MAIN).apply { addCategory(Intent.CATEGORY_LAUNCHER) }
            val apps: List<ResolveInfo> = pm.queryIntentActivities(intent, 0)
            runOnUiThread{
                val rv = findViewById<RecyclerView>(R.id.apps_grid)
                rv.layoutManager = GridLayoutManager(this,4)
                rv.adapter = AppAdapter(apps)
            }
        }.start()
    }
}
