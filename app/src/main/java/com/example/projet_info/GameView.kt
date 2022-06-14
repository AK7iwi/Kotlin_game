package com.example.projet_info

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.os.Bundle
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity

class GameView @JvmOverloads constructor (context: Context, attributes: AttributeSet? = null, defStyleAttr: Int = 0): SurfaceView(context, attributes,defStyleAttr), SurfaceHolder.Callback, Runnable {
    lateinit var canvas: Canvas
    val backgroundPaint = Paint()
    val textPaint = Paint()
    var screenWidth = 0f
    var screenHeight = 0f
    var timeLeft = 0.0
    val tempsmalus = 3
    val tempsbonus = 1
    var score = 0
    var gameOver = false
    var drawing = false
    val activity = context as FragmentActivity
    var totalElapsedTime = 0.0
    lateinit var thread: Thread
    val obstacle1 = Obstacle1(0f, 0f, 0f,0f,0f, this)
    val obstacle2 = Obstacle2(0f, 0f, 0f,0f,0f, this)
    val obstacle3 = Obstacle3(0f, 0f, 0f,0f,0f, this)
    val goal = Goal(0f,0f,0f)
    var balle = Balle( this, obstacle1,obstacle2,obstacle3, goal)


    init {
        backgroundPaint.color = Color.DKGRAY
        textPaint.textSize= screenWidth/20
        textPaint.color = Color.BLACK
        timeLeft = 60.0
    }
    fun pause() {
        drawing = false
        thread.join()
    }
    fun resume() {
        drawing = true
        thread = Thread(this)
        thread.start()
    }
    override fun run() {
        var previousFrameTime = System.currentTimeMillis()
        while (drawing) {
            val currentTime = System.currentTimeMillis()
            val elapsedTimeMS = (currentTime-previousFrameTime).toDouble()
            totalElapsedTime += elapsedTimeMS / 1000.0
            updatePositions(elapsedTimeMS)
            draw()
            previousFrameTime = currentTime
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(e: MotionEvent): Boolean {
        val action = e.action
        if (action == MotionEvent.ACTION_DOWN
            || action == MotionEvent.ACTION_MOVE) {
            deplacement(e)
        }
        return true
    }
    fun deplacement(event: MotionEvent) {
        val touchPoint = Point(event.x.toInt(), event.y.toInt())
        val distanceX = (touchPoint.x).toFloat() - screenWidth / 2
        val distanceY = screenHeight - (touchPoint.y).toFloat()
        balle.bouge(distanceX, distanceY)
    }

    override fun onSizeChanged(w:Int, h:Int, oldw:Int, oldh:Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        screenWidth = w.toFloat()
        screenHeight = h.toFloat()

        obstacle1.x = screenWidth / 2
        obstacle1.y = screenHeight / 6
        obstacle1.diametre = screenWidth / 12
        obstacle1.vitesseX = - screenWidth/ 3
        obstacle1.vitesseY = screenHeight / 2
        obstacle1.setRect()

        obstacle2.x = screenWidth / 4
        obstacle2.y = screenHeight/ 6
        obstacle2.diametre = screenWidth / 12
        obstacle2.vitesseX = screenWidth /3
        obstacle2.vitesseY = -screenHeight /3
        obstacle2.setRect()

        obstacle3.x =   3 * screenWidth / 4
        obstacle3.y = screenHeight / 5
        obstacle3.diametre = screenWidth / 12
        obstacle3.vitesseX =  screenWidth / 3
        obstacle3.vitesseY = 0f
        obstacle3.setRect()

        goal.x = screenWidth / 2
        goal.y = screenHeight / 8
        goal.diametre = screenWidth / 12
        goal.setRect()

        textPaint.setTextSize(w / 20f)
        textPaint.isAntiAlias = true
    }

    fun updatePositions(elapsedTimeMS: Double) {
        val interval = elapsedTimeMS / 1000.0
        obstacle1.update(interval)
        obstacle2.update(interval)
        obstacle3.update(interval)
        balle.update(interval)
        timeLeft -= interval

        if (timeLeft <= 0.0) {
            timeLeft = 0.0
            gameOver = true
            drawing = false
            showGameOverDialog(R.string.BRAVO)
        }

    }
    fun draw() {
        if (holder.surface.isValid) {
            canvas = holder.lockCanvas()
            canvas.drawRect(0f, 0f, canvas.width.toFloat(),
                canvas.height.toFloat(), backgroundPaint)
            val formatted = String.format("%.2f", timeLeft)
            canvas.drawText("Il reste $formatted secondes  SCORE : $score",
                30f, 50f, textPaint)
            if (balle.Onscreen) {
                balle.draw(canvas)
            }
            obstacle1.draw(canvas)
            obstacle2.draw(canvas)
            obstacle3.draw(canvas)
            goal.draw(canvas)
            holder.unlockCanvasAndPost(canvas)
        }

    }
    fun reduceTimeLeft() {
        timeLeft -= tempsmalus
        balle.resetBalle()
    }

    fun increaseTimeLeft() {
        timeLeft += tempsbonus
        balle.resetBalle()
    }

    fun increasescore(){
        ++score
        balle.resetBalle()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int,
                                width: Int, height: Int) {}
    override fun surfaceCreated(holder: SurfaceHolder) {}
    override fun surfaceDestroyed(holder: SurfaceHolder) {}

    fun showGameOverDialog(messageId: Int) {
        class GameResult: DialogFragment() {
            @SuppressLint("StringFormatInvalid")
            override fun onCreateDialog(bundle: Bundle?): Dialog {
                val builder = AlertDialog.Builder(getActivity())
                builder.setTitle(resources.getString(messageId))
                builder.setMessage(
                    resources.getString(
                        R.string.result_format, score, totalElapsedTime
                    )
                )
                builder.setPositiveButton(R.string.reset_game,
                    DialogInterface.OnClickListener { _, _->newGame()}
                )
                return builder.create()
            }
        }
        activity.runOnUiThread(
            Runnable {
                val ft = activity.supportFragmentManager.beginTransaction()
                val prev =
                    activity.supportFragmentManager.findFragmentByTag("dialog")
                if (prev != null) {
                    ft.remove(prev)
                }
                ft.addToBackStack(null)
                val gameResult = GameResult()
                gameResult.setCancelable(false)
                gameResult.show(ft,"dialog")
            }
        )
    }

    fun newGame() {
        balle.resetBalle()
        obstacle1.resetObstacle()
        obstacle2.resetObstacle()
        obstacle3.resetObstacle()
        goal.resetGoal()
        timeLeft = 60.0
        totalElapsedTime = 0.0
        score = 0
        drawing = true
        if (gameOver) {
            gameOver = false
            thread = Thread(this)
            thread.start()
        }
    }
}