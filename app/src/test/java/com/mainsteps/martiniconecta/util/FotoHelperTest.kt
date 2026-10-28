package com.mainsteps.martiniconecta.util

import org.junit.Assert.assertEquals
import org.junit.Test

class FotoHelperTest {

    @Test
    fun `autoridad agrega sufijo fileprovider al paquete`() {
        assertEquals(
            "com.mainsteps.martiniconecta.fileprovider",
            FotoHelper.autoridad("com.mainsteps.martiniconecta"),
        )
    }
}
