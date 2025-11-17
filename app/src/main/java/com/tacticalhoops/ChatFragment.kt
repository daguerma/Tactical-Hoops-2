package com.tacticalhoops

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tacticalhoops.adapters.ChatAdapter
import com.tacticalhoops.models.ChatMessage
import com.tacticalhoops.models.Play

class ChatFragment : Fragment() {

    private lateinit var recyclerChat: RecyclerView
    private lateinit var editMessage: EditText
    private lateinit var btnSend: Button
    private lateinit var emptyText: TextView
    private lateinit var chatAdapter: ChatAdapter
    private val messages = mutableListOf<ChatMessage>()
    private var onPlayClick: ((Play) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerChat = view.findViewById(R.id.recyclerChat)
        editMessage = view.findViewById(R.id.editMessage)
        btnSend = view.findViewById(R.id.btnSend)
        emptyText = view.findViewById(R.id.emptyText)

        chatAdapter = ChatAdapter(messages) { message ->
            message.play?.let { play ->
                onPlayClick?.invoke(play)
            }
        }

        recyclerChat.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = chatAdapter
        }

        btnSend.setOnClickListener {
            val text = editMessage.text.toString().trim()
            if (text.isNotEmpty()) {
                addMessage(ChatMessage(text = text))
                editMessage.text.clear()
            }
        }

        updateEmptyState()
    }

    fun addMessage(message: ChatMessage) {
        chatAdapter.addMessage(message)
        recyclerChat.smoothScrollToPosition(messages.size - 1)
        updateEmptyState()
    }

    fun setOnPlayClickListener(listener: (Play) -> Unit) {
        onPlayClick = listener
    }

    private fun updateEmptyState() {
        emptyText.visibility = if (messages.isEmpty()) View.VISIBLE else View.GONE
    }

    companion object {
        fun newInstance() = ChatFragment()
    }
}
