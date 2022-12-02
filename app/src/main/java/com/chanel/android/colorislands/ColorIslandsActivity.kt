package com.chanel.android.colorislands

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.chanel.android.colorislands.databinding.ActivityMainBinding
import kotlin.random.Random

const val NUM_ROWS = "com.chanel.android.colorislands.num_rows"
const val NUM_COLUMNS = "com.chanel.android.colorislands.num_columns"

class ColorIslandsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var squareAdapter: SquareAdapter
    private var squares: MutableList<Square> = mutableListOf()
    private val randomGenerator = Random(System.currentTimeMillis())

    private val numRows by lazy {
        intent.getIntExtra(NUM_ROWS, 10)
    }
    private val numColumns by lazy {
        intent.getIntExtra(NUM_COLUMNS, 10)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Generate the squares with random color assigned to it
        val totalSquares = numRows * numColumns
        for (i in 0 until totalSquares) {
            squares.add(Square(i, getRandomColor()))
        }

        // Set up adapter and onClick for each square
        squareAdapter = SquareAdapter(squares)
        squareAdapter.onItemClick = { square ->
            val posInList = squares.indexOf(square)
            val x = posInList / numColumns
            val y = posInList % numColumns
            fillValidSquares(x, y)
        }

        // Set recycler view properties
        binding.grid.apply {
            adapter = squareAdapter
            layoutManager = GridLayoutManager(this@ColorIslandsActivity, numColumns)
        }
    }

    // Randomly choose between black or white
    private fun getRandomColor(): Int {
        val isWhite = randomGenerator.nextBoolean()
        return if (isWhite) {
            Color.WHITE
        } else {
            Color.BLACK
        }
    }

    // Fill the square if it hasn't already been visited
    private fun processSquare(position: Int, square: Square) {
        square.color = Color.BLUE
        squareAdapter.notifyItemChanged(position)
    }

    // Given a position, fill the square if it is valid
    private fun fillValidSquares(x: Int, y: Int) {
        if (isOutOfBounds(x, y)) return

        val position = (x * numColumns) + (y % numColumns)
        val square = squares[position]

        // We only want to process white squares
        if (square.color != Color.WHITE) return

        processSquare(position, square)

        // Repeat for adjacent squares
        fillValidSquares(x-1, y)
        fillValidSquares( x+1, y)
        fillValidSquares(x, y+1)
        fillValidSquares(x, y-1)
    }

    // Checks if the given position is out of bounds of the grid
    private fun isOutOfBounds(x: Int, y: Int): Boolean {
        return (x < 0) || (y < 0) || (x > numRows - 1) || (y > numColumns - 1)
    }

    companion object {
        fun newIntent(packageContext: Context, numRows: Int?, numColumns: Int?): Intent {
            return Intent(packageContext, ColorIslandsActivity::class.java).apply {
                putExtra(NUM_ROWS, numRows)
                putExtra(NUM_COLUMNS, numColumns)
            }
        }
    }
}