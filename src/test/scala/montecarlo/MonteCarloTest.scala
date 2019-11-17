package montecarlo

import org.scalatest.FunSuite

class MonteCarloTest extends FunSuite {

  test("return same output when same input") {
    val iterations = 1000
    assert(MonteCarlo.monteCarloPiSeq(iterations) === MonteCarlo.monteCarloPiPar2(iterations))
    assert(MonteCarlo.monteCarloPiSeq(iterations) === MonteCarlo.monteCarloPiPar(iterations))
  }
}
