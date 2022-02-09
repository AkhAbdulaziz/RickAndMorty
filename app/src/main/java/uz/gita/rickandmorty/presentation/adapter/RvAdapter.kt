package uz.gita.rickandmorty.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.gita.rickandmorty.R
import uz.gita.rickandmorty.data.remote.responses.SingleCharacterResponse

class RvAdapter(private val list: List<SingleCharacterResponse>) :
    RecyclerView.Adapter<RvAdapter.VH>() {

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        private val name = view.findViewById<TextView>(R.id.nameItem)
        private val txtLifeCondition = view.findViewById<TextView>(R.id.txtLifeCondition)
        private val imgLifeCondition = view.findViewById<ImageView>(R.id.imgLifeCondition)
        private val location = view.findViewById<TextView>(R.id.txtLocation)
        private val imageHero = view.findViewById<ImageView>(R.id.imgItem)

        init {

        }

        fun bind() {
            val data = list[absoluteAdapterPosition]
            name.text = data.name
            txtLifeCondition.text = "${data.status} - ${data.species}"
            when (data.status) {
                "Alive" -> imgLifeCondition.setImageResource(R.drawable.life_status_alive_img)
                "Dead" -> imgLifeCondition.setImageResource(R.drawable.life_status_dead_img)
                "Unknown" -> imgLifeCondition.setImageResource(R.drawable.life_status_unknown_img)
            }
            location.text = data.location!!.name
            Glide.with(imageHero.context)
                .load(data.image)
                .placeholder(R.drawable.ic_place_holder)
                .error(R.drawable.ic_error)
                .into(imageHero)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
        VH(LayoutInflater.from(parent.context).inflate(R.layout.item_heros, parent, false))

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind()

    override fun getItemCount(): Int = list.size
}