import scala.util.Random
import scala.io.StdIn.readLine

/** *****************************************************************************
  * Representing a game
  *
  * We will represent the board as a string of four characters. Each character
  * will be one of the following: B = Blue, Y = Yellow, R = Red, G = Green
  */
type Color = Char
type Board = String
val validColors = List('B', 'Y', 'R', 'G')

/** Get a random color from the list of valid colors */
def getRandomColor(): Color =
  Random.shuffle(validColors).head

/** Given four colors, make a board from them */
def makeBoardFromColors(c1: Color, c2: Color, c3: Color, c4: Color): Board =
  List(c1, c2, c3, c4).mkString("")

/** Create a random board */
def getRandomBoard(): Board =
  makeBoardFromColors(getRandomColor(), getRandomColor(), getRandomColor(), getRandomColor())

/** Play one round of the game */
def playRound(board: Board): (Int, Int) =
  var firstGuess: Char = 'a'
  while (!validColors.contains(firstGuess)) {
    firstGuess = readLine("Enter a guess for spot 1: ").toCharArray().head
  }
  var secondGuess: Char = 'a'
  while (!validColors.contains(secondGuess)) {
    secondGuess = readLine("Enter a guess for spot 2: ").toCharArray().head
  }
  var thirdGuess: Char = 'a'
  while (!validColors.contains(thirdGuess)) {
    thirdGuess = readLine("Enter a guess for spot 3: ").toCharArray().head
  }
  var fourthGuess : Char = 'a'
  while (!validColors.contains(fourthGuess)) {
    fourthGuess = readLine("Enter a guess for spot 4: ").toCharArray().head
  }
  val guess = makeBoardFromColors(firstGuess, secondGuess, thirdGuess, fourthGuess)
  scoreGuess(board, guess)

/** Score a guess
  *
  * A score is a tuple of two integers. The first integer is the number of
  * correct positions, and the second integer is the number of remaining correct
  * colors.
  */
def scoreGuess(board: Board, guess: Board): (Int, Int) = {

  // The initial score is (0, 0)
  var correctPositions = 0
  var correctColors = 0

  // Get the unique colors on the board
  val boardColors = board.toSet

  // Check each guess position against the corresponding board position
  // or (if there is not a match at that position) against the remainder of
  // the board.
  for (i <- 0 to 3) {
    if (guess(i) == board(i)) {
      correctPositions += 1
    } else if (boardColors.contains(guess(i))) {
      correctColors += 1
    }
  }

  (correctPositions, correctColors)
}

/** *****************************************************************************
  * Main program
  */

// When true, the program will print out the board at the start of the game
val DEBUG = true

@main
def mastermind() = {

  // Create a new board
  val board = getRandomBoard()

  if (DEBUG) {
    println(s"[DEBUG] The board is $board")
  }

  // Play rounds until the user guesses the board
  var score = (0, 0)
  while (score != (4, 0)) {
    score = playRound(board)
    val (correctPlace, correctColor) = score
    println(s"$correctPlace color(s) are in the correct place.")
    println(s"$correctColor color(s) are correct but in the wrong place.\n")
  }

  // End the game
  println(s"Congratulations! You figured out the board was $board")
}
