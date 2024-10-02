package com.example.practica1

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Referencias a los elementos del layout
        val inputValue = findViewById<EditText>(R.id.inputValue)
        val calculateButton = findViewById<Button>(R.id.calculateButton)
        val resultTextView = findViewById<TextView>(R.id.resultTextView)
        val operationSpinner = findViewById<Spinner>(R.id.operationSpinner)

        // Configurar el Spinner con las opciones
        val operations = arrayOf("Cerradura de Kleene", "Cerradura Positiva")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, operations)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        operationSpinner.adapter = adapter

        // Acción al hacer clic en el botón de cálculo
        calculateButton.setOnClickListener {
            val input = inputValue.text.toString()

            if (input.isNotEmpty()) {
                val n = input.toInt()
                val selectedOperation = operationSpinner.selectedItem.toString()
                val result = when (selectedOperation) {
                    "Cerradura de Kleene" -> calculateKleeneClosure(n)
                    "Cerradura Positiva" -> calculatePositiveClosure(n)
                    else -> "Operación no válida"
                }

                // Mostrar el resultado en el TextView
                resultTextView.text = result
            } else {
                // Si el campo de entrada está vacío, mostrar un mensaje de error
                Toast.makeText(this, "Por favor, ingrese un valor válido", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Función para calcular la cerradura de Kleene
    private fun calculateKleeneClosure(n: Int): String {
        val result = mutableListOf<String>()
        result.add("λ")  // Incluimos la cadena vacía
        for (i in 1..n) {
            result.addAll(generateBinaryStrings(i))
        }
        return result.joinToString(", ")
    }

    // Función para calcular la cerradura positiva
    private fun calculatePositiveClosure(n: Int): String {
        val result = mutableListOf<String>()
        for (i in 1..n) {
            result.addAll(generateBinaryStrings(i))
        }
        return result.joinToString(", ")
    }

    // Función auxiliar para generar todas las cadenas binarias de longitud `length`
    private fun generateBinaryStrings(length: Int): List<String> {
        val result = mutableListOf<String>()
        val max = 1 shl length  // 2^length
        for (i in 0 until max) {
            val binaryString = Integer.toBinaryString(i).padStart(length, '0')
            result.add(binaryString)
        }
        return result
    }
}
