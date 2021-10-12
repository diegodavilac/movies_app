package dev.diegodc.moviesapp.features.dashboard.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.DifferCallback
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.diegodc.moviesapp.R
import dev.diegodc.moviesapp.features.dashboard.models.MovieView
import kotlinx.android.synthetic.main.item_movie.view.*


class MoviesAdapter(
    private val onItemClick: (MovieView) -> Unit
) : RecyclerView.Adapter<MovieViewHolder>(){

    var data : MutableList<MovieView> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder.newInstance(parent)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        data[position].let {
            holder.bind(it, onItemClick)
        }
    }

    override fun getItemCount(): Int = data.size
}

class MovieViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    companion object{
        fun newInstance(viewGroup: ViewGroup) : MovieViewHolder{
            val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_movie, viewGroup, false)
            return MovieViewHolder(view)
        }
    }

    fun bind(movie: MovieView, onItemClick: (MovieView) -> Unit){
        itemView.textView_movieTitle.text = movie.title
        itemView.textView_movieRating.text = movie.rating.toString()

        itemView.cardView_item_movie.setOnClickListener {
            onItemClick.invoke(movie)
        }

        Glide
            .with(itemView.context)
            .load(movie.url)
            .centerCrop()
            .placeholder(R.drawable.illustration_placeholder)
            .into(itemView.imageView_movie)
    }

}