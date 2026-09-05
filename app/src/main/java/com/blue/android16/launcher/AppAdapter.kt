package com.blue.android16.launcher
import android.content.pm.ResolveInfo
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
class AppAdapter(private val apps: List<ResolveInfo>): RecyclerView.Adapter<AppAdapter.VH>(){
    class VH(v: View): RecyclerView.ViewHolder(v){
        val icon: ImageView = v.findViewById(R.id.app_icon)
        val name: TextView = v.findViewById(R.id.app_name)
    }
    override fun onCreateViewHolder(p: ViewGroup, t: Int): VH {
        return VH(LayoutInflater.from(p.context).inflate(R.layout.item_app, p, false))
    }
    override fun onBindViewHolder(h: VH, pos: Int){
        val info = apps[pos]
        h.name.text = info.loadLabel(h.itemView.context.packageManager).toString()
        h.icon.setImageDrawable(info.loadIcon(h.itemView.context.packageManager))
        h.itemView.setOnClickListener {
            val launch = h.itemView.context.packageManager.getLaunchIntentForPackage(info.activityInfo.packageName)
            h.itemView.context.startActivity(launch)
        }
    }
    override fun getItemCount() = apps.size
}
