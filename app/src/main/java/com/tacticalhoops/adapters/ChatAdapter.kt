package com.tacticalhoops.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tacticalhoops.R
import com.tacticalhoops.models.ChatMessage
import java.text.SimpleDateFormat
import java.util.*

class ChatAdapter(
    private val messages: MutableList<ChatMessage>,
    private val onPlayClick: (ChatMessage) -> Unit
) : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    private val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

    class ChatViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textMessage: TextView = view.findViewById(R.id.textMessage)
        val textTime: TextView = view.findViewById(R.id.textTime)
        val textSender: TextView = view.findViewById(R.id.textSender)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_chat_message, parent, false)
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val message = messages[position]
        holder.textSender.text = message.sender
        holder.textMessage.text = if (message.play != null) {
            "[Play Shared] ${message.text}"
        } else {
            message.text
        }
        holder.textTime.text = dateFormat.format(Date(message.timestamp))

        if (message.play != null) {
            holder.itemView.setOnClickListener {
                onPlayClick(message)
            }
        }
    }

    override fun getItemCount() = messages.size

    fun addMessage(message: ChatMessage) {
        messages.add(message)
        notifyItemInserted(messages.size - 1)
    }
}
