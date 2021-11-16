package com.example.firechat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firechat.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.lang.IllegalStateException
import java.lang.RuntimeException

class MainActivity : AppCompatActivity() {
  lateinit var binding: ActivityMainBinding
  lateinit var database: FirebaseDatabase
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
    database = FirebaseDatabase.getInstance()
  }
  
  override fun onResume() {
    super.onResume()
    readMessagesFromDatabase()
    binding.btnSend.setOnClickListener {
      var text = binding.etMessage.text.toString()
      if (text.isBlank() || text.isEmpty()){
        binding.etMessage.setError("Message is required")
      }
      else{
        postMessage(text)
      }
    }
  }
  
  fun postMessage(text: String){
    var dbRef = database.getReference("Messages")
    var message = Message("acbd", text)
    dbRef.push().setValue(message)
    binding.etMessage.text.clear()
  }
  
  fun readMessagesFromDatabase(){
    var messages = mutableListOf<Message>()
    var dbRef = database.getReference("Messages")
    dbRef.addValueEventListener(object : ValueEventListener{
      override fun onDataChange(snapshot: DataSnapshot) {
        for (messageSnapshot in snapshot.children){
          var message = messageSnapshot.getValue(Message::class.java)
          messages.add(message!!)
        }
        var adapter = MessagesAdapter(messages)
        binding.rvMessages.layoutManager = LinearLayoutManager(this@MainActivity)
        binding.rvMessages.adapter = adapter
      }
  
      override fun onCancelled(error: DatabaseError) {
        //
      }
    })
  }
}

data class Message(var sender: String="", var text: String="")