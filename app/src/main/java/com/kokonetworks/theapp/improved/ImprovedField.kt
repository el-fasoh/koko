package com.kokonetworks.theapp.improved

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.kokonetworks.theapp.Mole
import com.kokonetworks.theapp.R

class ImprovedField : ChipGroup {
    private val chips = Array<Chip?>(9) { null }
    private var chipGroup: ChipGroup? = null
    private var selectedChip: Chip? = null
    private var improvedMole: ImprovedMole? = null
    var currentSelected: Int = -1
    var score = 0

    var callback: GameCallback? = null
    private var taps = 0
    private var hops = 0

    constructor(context: Context) : super(context) {
        initializeViews(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initializeViews(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initializeViews(context)
    }

    private fun initializeViews(context: Context) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.improved_field_view, this, true)

        chips[0] = findViewById<View>(R.id.chip1) as Chip
        chips[1] = findViewById<View>(R.id.chip2) as Chip
        chips[2] = findViewById<View>(R.id.chip3) as Chip
        chips[3] = findViewById<View>(R.id.chip4) as Chip
        chips[4] = findViewById<View>(R.id.chip5) as Chip
        chips[5] = findViewById<View>(R.id.chip6) as Chip
        chips[6] = findViewById<View>(R.id.chip7) as Chip
        chips[7] = findViewById<View>(R.id.chip8) as Chip
        chips[8] = findViewById<View>(R.id.chip9) as Chip
        chipGroup = findViewById(R.id.chip_group)
    }

    fun startGame() {
        resetCircles()
        resetScore()
        chips.forEach {
            it?.let { chip: Chip ->
                chip.setOnClickListener {
                    taps++
                    if (chip.id == selectedChip?.id) {
                        Log.e("#####", "Score B4: $score | Level: "+improvedMole?.getCurrentLevel())
                        score += (improvedMole?.getCurrentLevel() ?: 1) * 2
                        Log.e("#####", "Score $score ")
                    } else {
                      endGame()
                    }
                }
            }
        }
        improvedMole = ImprovedMole(this)
        improvedMole?.startHopping()
    }

    fun setActive(index: Int) {
        if(hops != taps) {
            endGame()
            return
        }
        handler.post {
            hops++
            selectedChip?.isChecked = false
            selectedChip = chips[index]
            currentSelected = index
            chips[index]?.isChecked = true
        }
    }

    private fun endGame() {
        improvedMole?.stopHopping()
        taps = 0
        hops = 0
        handler.post {
            chipGroup?.clearCheck()
            callback?.onGameEnded(score)
        }
    }

    private fun resetCircles() {
        selectedChip = null
        chipGroup?.clearCheck()
    }

    private fun resetScore() {
        score = 0
    }

    fun getTotalChips() = chips.size
}