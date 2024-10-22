package com.example.unscramble.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.unscramble.data.allWords

class GameViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(GameUiState())
    private lateinit var currentWord: String
    private var usedWords: MutableSet<String> = mutableSetOf()
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    init {
        resetGame()
    }

    private fun pickRandomWordAndShuffle(): String {

        currentWord = allWords.random()
        if (usedWords.contains(currentWord)) {
            return pickRandomWordAndShuffle()
        } else {
            usedWords.add(currentWord)
            return shuffleCurrentWord(currentWord)
        }
    }

    private fun shuffleCurrentWord(word: String): String {
        val tempWord = word.toCharArray()
        // Scramble the word
        tempWord.shuffle()
        while (String(tempWord).equals(word)) {
            tempWord.shuffle()
        }
        return String(tempWord)
    }

    fun resetGame() {
        usedWords.clear()
        _uiState.value = GameUiState(currentScrambledWord = pickRandomWordAndShuffle())
    }
}