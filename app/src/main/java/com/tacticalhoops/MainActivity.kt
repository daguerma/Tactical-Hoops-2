package com.tacticalhoops

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.tacticalhoops.models.ChatMessage

class MainActivity : AppCompatActivity() {

    private lateinit var tabLayout: TabLayout
    private lateinit var boardFragment: BoardFragment
    private lateinit var chatFragment: ChatFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tabLayout = findViewById(R.id.tabLayout)

        // Create fragments
        boardFragment = BoardFragment.newInstance()
        chatFragment = ChatFragment.newInstance()

        // Set up communication between fragments
        boardFragment.setOnPlaySharedListener { play ->
            chatFragment.addMessage(
                ChatMessage(
                    text = "Shared a new play",
                    play = play
                )
            )
            // Switch to chat tab to show the shared play
            tabLayout.selectTab(tabLayout.getTabAt(1))
        }

        chatFragment.setOnPlayClickListener { play ->
            boardFragment.loadPlay(play)
            // Switch to board tab to show the play
            tabLayout.selectTab(tabLayout.getTabAt(0))
            Toast.makeText(this, "Play loaded on board", Toast.LENGTH_SHORT).show()
        }

        // Show initial fragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.contentFrame, boardFragment)
            .commit()

        // Set up tab selection
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.contentFrame, boardFragment)
                            .commit()
                    }
                    1 -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.contentFrame, chatFragment)
                            .commit()
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }
}
