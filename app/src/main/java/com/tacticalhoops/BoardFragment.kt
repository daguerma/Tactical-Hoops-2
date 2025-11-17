package com.tacticalhoops

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.tacticalhoops.models.Play
import com.tacticalhoops.views.BasketballCourtView

class BoardFragment : Fragment() {

    private lateinit var courtView: BasketballCourtView
    private var onPlayShared: ((Play) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_board, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        courtView = view.findViewById(R.id.courtView)

        val btnAddPlayerTeam1 = view.findViewById<Button>(R.id.btnAddPlayerTeam1)
        val btnAddPlayerTeam2 = view.findViewById<Button>(R.id.btnAddPlayerTeam2)
        val btnAddBall = view.findViewById<Button>(R.id.btnAddBall)
        val btnDrawArrow = view.findViewById<Button>(R.id.btnDrawArrow)
        val btnClear = view.findViewById<Button>(R.id.btnClear)
        val btnShare = view.findViewById<Button>(R.id.btnShare)

        btnAddPlayerTeam1.setOnClickListener {
            courtView.drawMode = BasketballCourtView.DrawMode.ADD_PLAYER_TEAM1
            updateButtonStates(btnAddPlayerTeam1)
        }

        btnAddPlayerTeam2.setOnClickListener {
            courtView.drawMode = BasketballCourtView.DrawMode.ADD_PLAYER_TEAM2
            updateButtonStates(btnAddPlayerTeam2)
        }

        btnAddBall.setOnClickListener {
            courtView.drawMode = BasketballCourtView.DrawMode.ADD_BALL
            updateButtonStates(btnAddBall)
        }

        btnDrawArrow.setOnClickListener {
            courtView.drawMode = BasketballCourtView.DrawMode.DRAW_ARROW
            updateButtonStates(btnDrawArrow)
        }

        btnClear.setOnClickListener {
            courtView.clearBoard()
            Toast.makeText(requireContext(), "Board cleared", Toast.LENGTH_SHORT).show()
        }

        btnShare.setOnClickListener {
            if (courtView.play.players.isEmpty()) {
                Toast.makeText(requireContext(), "Add players to create a play", Toast.LENGTH_SHORT).show()
            } else {
                val playCopy = courtView.play.copy(
                    players = courtView.play.players.map { it.copy() }.toMutableList(),
                    balls = courtView.play.balls.map { it.copy() }.toMutableList(),
                    arrows = courtView.play.arrows.map { it.copy() }.toMutableList()
                )
                onPlayShared?.invoke(playCopy)
                Toast.makeText(requireContext(), getString(R.string.play_shared), Toast.LENGTH_SHORT).show()
            }
        }

        // Set default mode
        updateButtonStates(btnAddPlayerTeam1)
    }

    private fun updateButtonStates(activeButton: Button) {
        view?.let { v ->
            listOf(
                v.findViewById<Button>(R.id.btnAddPlayerTeam1),
                v.findViewById<Button>(R.id.btnAddPlayerTeam2),
                v.findViewById<Button>(R.id.btnAddBall),
                v.findViewById<Button>(R.id.btnDrawArrow)
            ).forEach { button ->
                button.alpha = if (button == activeButton) 1.0f else 0.5f
            }
        }
    }

    fun setOnPlaySharedListener(listener: (Play) -> Unit) {
        onPlayShared = listener
    }

    fun loadPlay(play: Play) {
        courtView.loadPlay(play)
    }

    companion object {
        fun newInstance() = BoardFragment()
    }
}
