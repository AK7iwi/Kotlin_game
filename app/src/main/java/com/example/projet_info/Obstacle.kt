package com.example.projet_info

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF

open class Obstacle (var x: Float, var y: Float, var diametre : Float ,var vitesseX : Float,var vitesseY : Float, var view: GameView)
{
    val obstacle = RectF(x, y, x + diametre  , y - diametre)
    val paint = Paint()
    var vitessex = vitesseX
    var vitessey = vitesseY

    fun update(interval: Double) {
        var deplacement1 = (interval * vitessex).toFloat()
        var deplacement2 = (interval * vitessey).toFloat()
        obstacle.offset(deplacement1, deplacement2)
        if (obstacle.left < 0 || obstacle.right > view.screenWidth) {
            vitessex *= -1f
            deplacement1 = (interval *  vitessex).toFloat()
            obstacle.offset(deplacement1, deplacement2)
        }
        if (obstacle.top < view.screenHeight / 8 || obstacle.bottom > (3 * (view.screenHeight) / 5)) {
            vitessey *= -1f
            deplacement2 = (interval * vitessey).toFloat()
            obstacle.offset(deplacement1, deplacement2)
        }

    }

    fun setRect() {
        obstacle.set(x, y, x + diametre , y - diametre )
        vitessex = vitesseX
        vitessey = vitesseY
    }

    fun draw(canvas: Canvas) {
        paint.color = Color.RED
        canvas.drawRect(obstacle, paint)
    }

    fun resetObstacle() {
        vitessex = vitesseX
        vitessey = vitesseY
        obstacle.set(x, y, x + diametre  , y - diametre)
    }
}

class Obstacle1(var x1 : Float, var y1 :Float, var diametre1: Float, var vitesseX1 : Float, var vitesseY1 : Float,var view1: GameView) : Obstacle(x1, y1, diametre1, vitesseX1,  vitesseY1,view1 )
{

}

class Obstacle2(var x2 : Float, var y2 :Float, var diametre2: Float, var vitesseX2: Float, var vitesseY2 : Float,var view2: GameView) : Obstacle(x2, y2, diametre2, vitesseX2,  vitesseY2,view2 )
{

}

class Obstacle3(var x3 : Float, var y3 :Float, var diametre3: Float, var vitesseX3 : Float, var vitesseY3 : Float,var view3: GameView) : Obstacle(x3, y3, diametre3, vitesseX3,  vitesseY3,view3 )
{

}
