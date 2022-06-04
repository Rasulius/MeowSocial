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
        if (comments[position].photo == null) {
            Glide.with(holder.photo)
                .load(R.drawable.ic_unsetted_avatar)
                .into(holder.photo)
        } else {
            Glide.with(holder.photo)
                .load(comments[position].photo)
                .into(holder.photo)
        }

        val uid = comment.uid
        val timestamp = comment.timestamp
        val username = comment.username.plus(" ")
        val commentText = comment.text
        val finalCommentString = username + commentText
        val spannableFinalCommentString = SpannableString (finalCommentString)
        spannableFinalCommentString.setSpan(StyleSpan(android.graphics.Typeface.BOLD), 0, username.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        holder.comment.setText(spannableFinalCommentString, TextView.BufferType.SPANNABLE)

            //holder.comment.setText(comment.text)
    }

    fun updateComments(newComments: List<Comment>) {
        //val diffResult = DiffUtil.calculateDiff(SimpleCallback(comments, newComments) {it.id})
        this.comments = newComments
        notifyDataSetChanged()
        //diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int = comments.size
}