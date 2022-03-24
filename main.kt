val alfabeto = CharRange('A', 'Z').toList()
val vocali = arrayOf('A', 'E', 'I', 'O', 'U')

val caratteriDispari = hashMapOf(
    '0' to 1, '1' to 0, '2' to 5, '3' to 7, '4' to 9, '5' to 13,
    '6' to 15, '7' to 17, '8' to 19, '9' to 21, 'A' to 1, 'B' to 0,
    'C' to 5, 'D' to 7, 'E' to 9, 'F' to 13, 'G' to 15, 'H' to 17,
    'I' to 19, 'J' to 21, 'K' to 2, 'L' to 4, 'M' to 18, 'N' to 20,
    'O' to 11, 'P' to 3, 'Q' to 6, 'R' to 8, 'S' to 12, 'T' to 14,
    'U' to 16, 'V' to 10, 'W' to 22, 'X' to 25, 'Y' to 24, 'Z' to 23
)
val caratteriPari = hashMapOf(
    '0' to 0, '1' to 1, '2' to 2, '3' to 3, '4' to 4, '5' to 5,
    '6' to 6, '7' to 7, '8' to 8, '9' to 9, 'A' to 0, 'B' to 1,
    'C' to 2, 'D' to 3, 'E' to 4, 'F' to 5, 'G' to 6, 'H' to 7,
    'I' to 8, 'J' to 9, 'K' to 10, 'L' to 11, 'M' to 12, 'N' to 13,
    'O' to 14, 'P' to 15, 'Q' to 16, 'R' to 17, 'S' to 18, 'T' to 19,
    'U' to 20, 'V' to 21, 'W' to 22, 'X' to 23, 'Y' to 24, 'Z' to 25
)

val valoriInvalidi = hashMapOf(
    arrayOf("À", "Á", "Â", "Ã") to "A",
    arrayOf("Ä", "Æ") to "AE",
    arrayOf("È", "É", "Ê", "Ë", "&") to "E",
    arrayOf("Ì", "Í", "Î", "Ï") to "I",
    arrayOf("Ò", "Ó", "Ô") to "O",
    arrayOf("Ö", "Œ") to "OE",
    arrayOf("Ù", "Ú", "Û") to "U",
    arrayOf("Ü") to "UE",
    arrayOf("Ç", "Č") to "C",
    arrayOf("Ñ") to "N",
    arrayOf("Š") to "S",
    arrayOf("ß") to "SS",
    arrayOf("Ž") to "Z",
    arrayOf("+", "-") to " "
)

fun main() {
    val nome = "".fixString()
    val cognome = "".fixString()
    val giorno = 0
    val mese = 0
    val anno = 0
    val sesso = 'M'
    val citta = "A000"

    val codice = generaCognome(cognome) + generaNome(nome) + generaData(giorno, mese, anno, sesso) + citta
    print("$codice${getCodiceControllo(codice)}")
}

fun getCodiceControllo(codice: String): Char {
    var valDisp = 0
    var valPari = 0

    codice.forEachIndexed { index, char ->
        if ((index + 1) % 2 == 0) {
            valPari += caratteriPari[char]!!
        } else {
            valDisp += caratteriDispari[char]!!
        }
    }

    return (64 + ((valDisp + valPari) % 26) + 1).toChar()
}

fun generaCognome(cognome: String): String {
    val cons = cognome.consonanti()
    val voc = cognome.vocali()

    return if (cons.size >= 3) {
        cons.subList(0, 3).joinToString("")
    } else {
        if (cons.size + voc.size >= 3) {
            cons.joinToString("") + voc.subList(0, 3 - cons.size).joinToString("")
        } else {
            cons.joinToString("") + voc.joinToString("") + "X".repeat(3 - cons.size - voc.size)
        }
    }
}

fun generaNome(nome: String): String {
    val cons = nome.consonanti()
    val voc = nome.vocali()

    return when {
        cons.size >= 4 -> "${cons[0]}${cons[2]}${cons[3]}"
        cons.size == 3 -> cons.joinToString("")
        cons.size + voc.size >= 3 -> {
            cons.joinToString("") + voc.subList(0, 3 - cons.size).joinToString("")
        }
        else -> {
            cons.joinToString("") + voc.joinToString("") +
                    "X".repeat(3 - cons.size - voc.size)
        }
    }
}

fun generaData(giorno: Int, mese: Int, anno: Int, sex: Char): String {
    var giornoS = giorno.toString()
    var meseS = ""
    var annoS = anno.toString()

    annoS = annoS.substring(annoS.length - 2)

    when (mese) {
        1 -> meseS = "A"
        2 -> meseS = "B"
        3 -> meseS = "C"
        4 -> meseS = "D"
        5 -> meseS = "E"
        6 -> meseS = "H"
        7 -> meseS = "L"
        8 -> meseS = "M"
        9 -> meseS = "P"
        10 -> meseS = "R"
        11 -> meseS = "S"
        12 -> meseS = "T"
    }

    if (sex == 'F') giornoS = (giorno + 40).toString()
    giornoS = giornoS.padStart(2, '0')

    return "$annoS$meseS$giornoS"
}

fun String.consonanti(): List<Char> = this.toList().filter { it in alfabeto.minus(vocali) }

fun String.vocali(): List<Char> = this.toList().filter { it in vocali }

fun String.fixString(): String {
    var s = this.toUpperCase()

    valoriInvalidi.forEach { array, char ->
        array.forEach {
            s = s.replace(it, char)
        }
    }

    return s
}
