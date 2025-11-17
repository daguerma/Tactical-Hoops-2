package com.tacticalhoops.views

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.tacticalhoops.models.Arrow
import com.tacticalhoops.models.Ball
import com.tacticalhoops.models.Play
import com.tacticalhoops.models.Player
import kotlin.math.sqrt

class BasketballCourtView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint().apply {
        isAntiAlias = true
    }

    private val courtPaint = Paint().apply {
        color = Color.parseColor("#2E7D32")
        style = Paint.Style.FILL
    }

    private val linePaint = Paint().apply {
        color = Color.WHITE
        style = Paint.Style.STROKE
        strokeWidth = 4f
    }

    private val playerPaint = Paint().apply {
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    private val playerTextPaint = Paint().apply {
        color = Color.WHITE
        textAlign = Paint.Align.CENTER
        textSize = 32f
        isAntiAlias = true
    }

    private val ballPaint = Paint().apply {
        color = Color.parseColor("#FF6F00")
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    private val arrowPaint = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 6f
        isAntiAlias = true
    }

    val play = Play()
    var drawMode = DrawMode.ADD_PLAYER_TEAM1
    private var selectedPlayer: Player? = null
    private var arrowStart: Pair<Float, Float>? = null

    private val playerRadius = 40f
    private val ballRadius = 25f

    enum class DrawMode {
        ADD_PLAYER_TEAM1,
        ADD_PLAYER_TEAM2,
        ADD_BALL,
        DRAW_ARROW,
        MOVE
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Draw court background
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), courtPaint)

        // Draw court lines
        drawCourtLines(canvas)

        // Draw arrows
        play.arrows.forEach { arrow ->
            drawArrow(canvas, arrow)
        }

        // Draw players
        play.players.forEach { player ->
            val color = if (player.team == 1) Color.parseColor("#1976D2") else Color.parseColor("#D32F2F")
            playerPaint.color = color
            canvas.drawCircle(player.x, player.y, playerRadius, playerPaint)
            canvas.drawText(
                player.id.toString(),
                player.x,
                player.y + 12f,
                playerTextPaint
            )
        }

        // Draw balls
        play.balls.forEach { ball ->
            canvas.drawCircle(ball.x, ball.y, ballRadius, ballPaint)
        }

        // Draw temporary arrow if being drawn
        arrowStart?.let { start ->
            // This will be drawn during drag
        }
    }

    private fun drawCourtLines(canvas: Canvas) {
        val w = width.toFloat()
        val h = height.toFloat()

        // Outer boundary
        canvas.drawRect(20f, 20f, w - 20f, h - 20f, linePaint)

        // Center line
        canvas.drawLine(w / 2f, 20f, w / 2f, h - 20f, linePaint)

        // Center circle
        canvas.drawCircle(w / 2f, h / 2f, 80f, linePaint)

        // Three-point lines (simplified)
        val threePointRadius = w / 3f
        canvas.drawArc(
            w / 2f - threePointRadius, 20f,
            w / 2f + threePointRadius, 20f + threePointRadius * 1.5f,
            180f, 180f, false, linePaint
        )
        canvas.drawArc(
            w / 2f - threePointRadius, h - 20f - threePointRadius * 1.5f,
            w / 2f + threePointRadius, h - 20f,
            0f, 180f, false, linePaint
        )

        // Paint area (key)
        val keyWidth = w / 4f
        canvas.drawRect(
            w / 2f - keyWidth / 2f, 20f,
            w / 2f + keyWidth / 2f, 20f + h / 6f,
            linePaint
        )
        canvas.drawRect(
            w / 2f - keyWidth / 2f, h - 20f - h / 6f,
            w / 2f + keyWidth / 2f, h - 20f,
            linePaint
        )

        // Hoops
        canvas.drawCircle(w / 2f, 50f, 15f, linePaint)
        canvas.drawCircle(w / 2f, h - 50f, 15f, linePaint)
    }

    private fun drawArrow(canvas: Canvas, arrow: Arrow) {
        arrowPaint.color = arrow.color
        canvas.drawLine(arrow.startX, arrow.startY, arrow.endX, arrow.endY, arrowPaint)

        // Draw arrowhead
        val angle = Math.atan2(
            (arrow.endY - arrow.startY).toDouble(),
            (arrow.endX - arrow.startX).toDouble()
        )
        val arrowHeadLength = 30f

        val x1 = arrow.endX - arrowHeadLength * Math.cos(angle - Math.PI / 6).toFloat()
        val y1 = arrow.endY - arrowHeadLength * Math.sin(angle - Math.PI / 6).toFloat()
        canvas.drawLine(arrow.endX, arrow.endY, x1, y1, arrowPaint)

        val x2 = arrow.endX - arrowHeadLength * Math.cos(angle + Math.PI / 6).toFloat()
        val y2 = arrow.endY - arrowHeadLength * Math.sin(angle + Math.PI / 6).toFloat()
        canvas.drawLine(arrow.endX, arrow.endY, x2, y2, arrowPaint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                handleTouchDown(event.x, event.y)
            }
            MotionEvent.ACTION_MOVE -> {
                handleTouchMove(event.x, event.y)
            }
            MotionEvent.ACTION_UP -> {
                handleTouchUp(event.x, event.y)
            }
        }
        return true
    }

    private fun handleTouchDown(x: Float, y: Float) {
        when (drawMode) {
            DrawMode.ADD_PLAYER_TEAM1 -> {
                val playerId = play.players.count { it.team == 1 } + 1
                play.players.add(Player(x, y, 1, playerId))
                invalidate()
            }
            DrawMode.ADD_PLAYER_TEAM2 -> {
                val playerId = play.players.count { it.team == 2 } + 1
                play.players.add(Player(x, y, 2, playerId))
                invalidate()
            }
            DrawMode.ADD_BALL -> {
                play.balls.add(Ball(x, y))
                invalidate()
            }
            DrawMode.DRAW_ARROW -> {
                arrowStart = Pair(x, y)
            }
            DrawMode.MOVE -> {
                selectedPlayer = findPlayerAt(x, y)
            }
        }
    }

    private fun handleTouchMove(x: Float, y: Float) {
        when (drawMode) {
            DrawMode.MOVE -> {
                selectedPlayer?.let { player ->
                    player.x = x
                    player.y = y
                    invalidate()
                }
            }
            else -> {}
        }
    }

    private fun handleTouchUp(x: Float, y: Float) {
        when (drawMode) {
            DrawMode.DRAW_ARROW -> {
                arrowStart?.let { start ->
                    play.arrows.add(Arrow(start.first, start.second, x, y, Color.BLACK))
                    arrowStart = null
                    invalidate()
                }
            }
            DrawMode.MOVE -> {
                selectedPlayer = null
            }
            else -> {}
        }
    }

    private fun findPlayerAt(x: Float, y: Float): Player? {
        return play.players.find { player ->
            val distance = sqrt((player.x - x) * (player.x - x) + (player.y - y) * (player.y - y))
            distance <= playerRadius
        }
    }

    fun clearBoard() {
        play.players.clear()
        play.balls.clear()
        play.arrows.clear()
        invalidate()
    }

    fun loadPlay(loadedPlay: Play) {
        play.players.clear()
        play.balls.clear()
        play.arrows.clear()
        
        play.players.addAll(loadedPlay.players)
        play.balls.addAll(loadedPlay.balls)
        play.arrows.addAll(loadedPlay.arrows)
        
        invalidate()
    }
}
