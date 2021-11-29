package com.example.guessinggames

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class NumberGameAdapter :  RecyclerView.Adapter<NumberGameAdapter.GameViewHolder>() {
    var textNumberGameList : ArrayList<NumberGameText> = ArrayList()

    fun setGameList(numberGameTextArrayList: ArrayList<NumberGameText>) {
        this.textNumberGameList = numberGameTextArrayList
        notifyDataSetChanged()
    }

    //I think the viewholder = cell
    class GameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var tvGame : TextView = itemView.findViewById(R.id.tvGameText)

        fun bind(numberGameText: NumberGameText){
            tvGame.text = numberGameText.text
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        var view: View = LayoutInflater.from(parent.context).inflate(R.layout.num_game_recyclerview, parent, false)
        return GameViewHolder(view)
    }
    // get text or any thing inside the cell(I think here called view holder)
    //holder : used to access the viwes inside the viewholder (ex: textmimage)
    // position: the current position of current index of particular view
    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        // itemview: view that contains all of our single view inside gamerecycleview.xml (in my case: it contain just text)
        holder.itemView.apply {
            holder.bind(textNumberGameList[position])
        }
    }

    override fun getItemCount() = textNumberGameList.size
    // or we can write the func with body and return as usual :
    //override fun getItemCount(){return game.size}

    //}
}
