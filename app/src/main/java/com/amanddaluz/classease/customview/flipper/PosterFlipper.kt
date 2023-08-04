package com.amanddaluz.classease.customview.flipper

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ViewFlipper
import com.amanddaluz.classease.R
import com.amanddaluz.classease.databinding.ItemPosterBinding
import com.bumptech.glide.Glide

class PosterFlipper(context: Context, attrs: AttributeSet?) : ViewFlipper(context, attrs) {

    private var list = mutableListOf<PosterDomain>()

    fun setList(posterList: MutableList<PosterDomain>) {
        this.list = posterList
    }

    fun setLayout() {
        list.forEach {
            val binding = ItemPosterBinding.inflate(
                LayoutInflater.from(context),
                this,
                false
            )
            binding.apply {
                titlePoster.text = it.title

                Glide.with(context)
                    .load(R.drawable.ic_launcher_background)
                    .centerCrop()
                    .into(ivPoster)

                addView(root)
            }
        }
    }

    interface Item {
        // Métodos ou propriedades comuns dos objetos que serão armazenados na lista
    }

    // Exemplo de classe que implementa a interface Item
    data class PosterDomain(
        val title: String,
        val imageUrl: Drawable
    ) : Item
}