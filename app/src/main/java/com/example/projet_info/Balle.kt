package com.example.projet_info

import android.graphics.*

class Balle(val view: GameView, val obstacle1 : Obstacle1, val obstacle2: Obstacle2, val obstacle3: Obstacle3 ,val goal :Goal) {

    var balle = PointF()
    var vitesseX = 1000f
    var vitesseY = 1000f
    var Onscreen = false
    var rayon = 30f
    val paint = Paint()

    init {
        paint.color = Color.BLACK
    }

    fun bouge(x1 : Float, y1 : Float){
        balle.x = view.screenWidth / 2f
        balle.y = view.screenHeight
        vitesseX = x1
        vitesseY= -y1
        Onscreen = true
    }
    fun update(interval: Double) {
        if (Onscreen) {
            balle.x += (interval * vitesseX).toFloat()
            balle.y += (interval * vitesseY).toFloat()

            /* sortie d'écran */
            if (balle.x + rayon > view.screenWidth
                || balle.x - rayon < 0
            ) {
                vitesseX *= -1f
                balle.offset((vitesseX * interval).toFloat(), (vitesseY * interval).toFloat())
            }
            else if (balle.y - rayon < 0) {
                Onscreen = false
            }
            /* Obstacle1 */
            else if (balle.y - rayon < obstacle1.obstacle.bottom  && balle.y + rayon > obstacle1.obstacle.bottom && balle.x - rayon < obstacle1.obstacle.left && balle.x + rayon > obstacle1.obstacle.left){
                view.reduceTimeLeft()
            }

            else if (balle.y - rayon < obstacle1.obstacle.bottom && balle.y + rayon > obstacle1.obstacle.bottom && balle.x - rayon < obstacle1.obstacle.right && balle.x + rayon > obstacle1.obstacle.right){
                view.reduceTimeLeft()

            }
            /* Obstacle2 */
            else if (balle.y - rayon < obstacle2.obstacle.bottom  && balle.y + rayon > obstacle2.obstacle.bottom && balle.x - rayon < obstacle2.obstacle.left && balle.x + rayon > obstacle2.obstacle.left){
                view.reduceTimeLeft()
            }

            else if (balle.y - rayon < obstacle2.obstacle.bottom && balle.y + rayon > obstacle2.obstacle.bottom && balle.x - rayon < obstacle2.obstacle.right && balle.x + rayon > obstacle2.obstacle.right){
                view.reduceTimeLeft()

            }
            /* Obstacle3 */
            else if (balle.y - rayon < obstacle3.obstacle.bottom  && balle.y + rayon > obstacle3.obstacle.bottom && balle.x - rayon < obstacle3.obstacle.left && balle.x + rayon > obstacle3.obstacle.left){
                view.reduceTimeLeft()
            }

            else if (balle.y - rayon < obstacle3.obstacle.bottom && balle.y + rayon > obstacle3.obstacle.bottom && balle.x - rayon < obstacle3.obstacle.right && balle.x + rayon > obstacle3.obstacle.right){
                view.reduceTimeLeft()

            }
            /* Goal*/
            else if (balle.y - rayon < goal.goal.bottom  && balle.y + rayon > goal.goal.bottom && balle.x - rayon < goal.goal.left && balle.x + rayon > goal.goal.left) {
                view.increasescore()
                view.increaseTimeLeft()
            }
            else if (balle.y - rayon < goal.goal.bottom && balle.y + rayon > goal.goal.bottom && balle.x - rayon < goal.goal.right && balle.x + rayon > goal.goal.right){
                view.increasescore()
                view.increaseTimeLeft()
            }
            else if (balle.y - rayon < goal.goal.bottom && balle.y + rayon > goal.goal.bottom && balle.x - rayon  > goal.goal.left && balle.x + rayon < goal.goal.right){
                view.increasescore()
                view.increaseTimeLeft()
            }
        }
    }
    fun draw(canvas: Canvas) {
        canvas.drawCircle(balle.x, balle.y, rayon, paint)
        }
    fun resetBalle() {
        Onscreen = false
    }
}