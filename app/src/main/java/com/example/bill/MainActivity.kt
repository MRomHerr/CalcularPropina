package com.example.bill


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
import kotlin.math.ceil
import com.example.bill.ui.theme.BillTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BillTheme {
                CalculatorApp()
            }
        }
    }
}

@Composable
fun CalculatorApp() {
    var billAmount by remember { mutableStateOf("") } // monto de la cuenta ingresado
    var tipPercentage by remember { mutableStateOf("") } // porcentaje de la propina ingresado
    var roundUp by remember { mutableStateOf(false) } // estado del switch para redondear
    var tipAmount by remember { mutableStateOf(0.0) } // monto de la propina calculada

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Calculate Tip", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        // campo de texto para el monto de la cuenta
        TextField(
            value = billAmount,
            onValueChange = { billAmount = it },
            label = { Text("Bill Amount") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // campo de texto para el porcentaje de la propina
        TextField(
            value = tipPercentage,
            onValueChange = { tipPercentage = it },
            label = { Text("Tip Percentage") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // switch para decidir si redondear o no la propina
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Round up tip?")
            Switch(
                checked = roundUp,
                onCheckedChange = { roundUp = it }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // boton para calcular la propina
        Button(onClick = {
            val bill = billAmount.toDoubleOrNull() ?: 0.0
            val tipPercent = tipPercentage.toDoubleOrNull() ?: 0.0
            var tip = bill * (tipPercent / 100)
            if (roundUp) {
                tip = ceil(tip) // redondea hacia arriba si el switch esta activado
            }
            tipAmount = tip
        }) {
            Text("Calculate Tip")
        }

        Spacer(modifier = Modifier.height(32.dp))

        // mostrar el monto de la propina calculada
        Text(
            text = "Tip Amount: $${"%.2f".format(tipAmount)}",
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BillTheme {
        CalculatorApp()
    }
}
