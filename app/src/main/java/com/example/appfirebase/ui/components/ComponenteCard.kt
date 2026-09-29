package com.example.appfirebase.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.appfirebase.data.model.TipoComponente

fun TipoComponente.icone(): ImageVector = when (this) {
    TipoComponente.PLACA        -> Icons.Filled.Memory       // chip
    TipoComponente.SENSOR       -> Icons.Filled.Sensors      // sensor
    TipoComponente.AMPLIFICADOR -> Icons.Filled.GraphicEq    // onda sonora
    TipoComponente.CABO         -> Icons.Filled.Cable        // cabo

}

