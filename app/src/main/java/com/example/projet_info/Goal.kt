package com.example.projet_info

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF

class Goal(var x: Float, var y: Float, var diametre : Float ) {
    val goal = RectF(x, y, x + diametre  , y - diametre)
    val paint = Paint()

    fun setRect() {
        goal.set(x, y, x + diametre,  y - diametre)
    }
    fun draw(canvas: Canvas) {
        paint.color = Color.GRAY
        canvas.drawRect(goal, paint)
    }

    fun resetGoal() {
        goal.set(x, y, x + diametre, y- diametre)
    }

}

