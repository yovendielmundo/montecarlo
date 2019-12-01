package montecarlo

import common._
import org.scalameter._

import scala.util.Random

object MonteCarloRunner {

  val standardConfig: MeasureBuilder[Unit, Double] = config(
    Key.exec.minWarmupRuns -> 5,
    Key.exec.maxWarmupRuns -> 10,
    Key.exec.benchRuns     -> 10,
    Key.verbose            -> false
  ) withWarmer (new Warmer.Default)

  def main(args: Array[String]): Unit = {
    val iterations = 1000000
    println(s"Number of iterations: \t$iterations")

    val sequentialTime = standardConfig measure {
      MonteCarlo.monteCarloPiSeq(iterations)
    }
    println(s"Sequential time: \t$sequentialTime")

    val parallelTime = standardConfig measure {
      MonteCarlo.monteCarloPiPar(iterations)
    }
    println(s"Fork/Join time: \t$parallelTime")

    println("----------------------------------------------")
    println(s"Speedup: \t\t\t${sequentialTime.value / parallelTime.value} ${parallelTime.units}")

  }
}

object MonteCarlo {

  def mcCount(iterations: Int): Int = {
    val randomX = new Random
    val randomY = new Random

    def randomXY: Double = {
      val (x, y) = (randomX.nextDouble, randomY.nextDouble) // in [0,1]
      x * x + y * y
    }

    (0 until iterations).foldLeft(0) { case (acc, _) => if (randomXY < 1) acc + 1 else acc }
  }

  def monteCarloPiSeq(iterations: Int): Double =
    4.0 * mcCount(iterations) / iterations

  def monteCarloPiPar(iterations: Int): Double = {
    val ((pi1, pi2), (pi3, pi4)) = parallel(
      parallel(mcCount(iterations / 4), mcCount(iterations / 4)),
      parallel(mcCount(iterations / 4), mcCount(iterations / 4))
    )
    4.0 * (pi1 + pi2 + pi3 + pi4) / iterations
  }

  def monteCarloPiPar2(iterations: Int): Double = {
    val iter4                = iterations / 4
    val (pi1, pi2, pi3, pi4) = parallel(mcCount(iter4), mcCount(iter4), mcCount(iter4), mcCount(iter4))
    4.0 * (pi1 + pi2 + pi3 + pi4) / iterations
  }
}
