package com.example.firechat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MessagesAdapter(var messagesList: List<Message>): RecyclerView.Adapter<MessageViewHolder>() {
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
    var itemView = LayoutInflater.from(parent.context).inflate(R.layout.message_list_item, parent, false)
    return MessageViewHolder(itemView)
  }
  
  override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
    var currentMessage = messagesList.get(position)
    holder.tvMessage.text = currentMessage.text
    holder.tvSender.text = currentMessage.sender
  }
  
  override fun getItemCount(): Int {
    return messagesList.size
  }
  
  override fun getItemViewType(position: Int): Int {
    return super.getItemViewType(position)
    
  }
}

class MessageViewHolder(var itemView: View): RecyclerView.ViewHolder(itemView){
  var tvSender = itemView.findViewById<TextView>(R.id.tvSender)
  var tvMessage = itemView.findViewById<TextView>(R.id.tvMessage)
}