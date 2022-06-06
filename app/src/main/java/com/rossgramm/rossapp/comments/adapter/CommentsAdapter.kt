package com.rossgramm.rossapp.comments.adapter

import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.rossgramm.rossapp.R
import com.rossgramm.rossapp.comments.data.Comment

class CommentsAdapter : RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder>() {
    class CommentsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val photo: ImageView = itemView.findViewById(R.id.avatar_comments)
        val comment: TextView = itemView.findViewById(R.id.comments_text)
    }

    private var comments = listOf<Comment>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_comments, parent, false)
        return CommentsViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {
        val comment = comments[position]

        Glide.with(holder.photo)
            .load(comments[position].photo)
            .placeholder(R.drawable.ic_unsetted_avatar)
            .into(holder.photo)

        val uid = comment.uid
        val timestamp = comment.timestamp

        StringBuilder().apply {
            append(comment.username)
            append(" ")
            append(comment.text)
        }.let {
            val spannableFinalCommentString = SpannableString(it)
            spannableFinalCommentString.setSpan(
                StyleSpan(android.graphics.Typeface.BOLD),
                0,
                comment.username.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            holder.comment.setText(spannableFinalCommentString, TextView.BufferType.SPANNABLE)
        }
    }

    fun updateComments(newComments: List<Comment>) {
        //val diffResult = DiffUtil.calculateDiff(SimpleCallback(comments, newComments) {it.id})
        this.comments = newComments
        notifyDataSetChanged()
        //diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int = comments.size
}