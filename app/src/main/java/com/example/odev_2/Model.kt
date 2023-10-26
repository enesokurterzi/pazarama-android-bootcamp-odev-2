package com.example.odev_2

import android.content.ContentValues
import android.content.Context

data class Kategori(
    var K_ID: Int = 0,
    var Aciklama: String = ""
)

data class Parca(
    var P_ID:Int = 0,
    var Kategori_ID:Int = 0,
    var Adi:String = "",
    var StokAdedi:Int = 0,
    var Fiyati:Long = 0L
)

class KategoriRepository(c:Context)
{
    private var context:Context
    private var dbg: DBGateway

    init {
        context = c
        dbg = DBGateway(c)
    }

    fun kategorileriOlustur() {
        val db = dbg.writableDatabase

        val cur = db.rawQuery("Select * from Kategoriler", null)

        if (cur.count == 0) {
            var cv = ContentValues()
            cv.put("Aciklama", "Motor")
            db.insert("Kategoriler", null, cv)

            cv = ContentValues()
            cv.put("Aciklama", "Kaporta")
            db.insert("Kategoriler", null, cv)

            cv = ContentValues()
            cv.put("Aciklama", "Elektrik")
            db.insert("Kategoriler", null, cv)

            cv = ContentValues()
            cv.put("Aciklama", "Aksesuar")
            db.insert("Kategoriler", null, cv)
        }

        db.close()
        cur.close()
    }

    fun getKategoriler() : MutableList<Kategori> {
        val db = dbg.readableDatabase

        val cur = db.rawQuery("Select * from Kategoriler", null)

        val lst = mutableListOf<Kategori>()

        while (cur.moveToNext())
        {
            lst.add(
                Kategori(
                    cur.getInt(0),
                    cur.getString(1))
            )
        }

        db.close()
        cur.close()
        return lst
    }
}

class ParcaRepository(c:Context) {
    private var context:Context
    private var dbg: DBGateway

    init {
        context = c
        dbg = DBGateway(c)
    }

    fun parcaEkle(p: Parca) {
        val db = dbg.writableDatabase

        val cv = ContentValues()
        cv.put("KategoriID", p.Kategori_ID)
        cv.put("Adi", p.Adi)
        cv.put("StokAdedi", p.StokAdedi)
        cv.put("Fiyati", p.Fiyati)

        db.insert("Parcalar", null, cv)

        db.close()
    }

    fun parcalariOlustur() {
        parcaEkle(Parca(-1, 1, Adi = "Segman", StokAdedi = 10, Fiyati = 300L))
        parcaEkle(Parca(-1, 1, Adi = "Trigger Kayışı", StokAdedi = 15, Fiyati = 1300L))
        parcaEkle(Parca(-1, 1, Adi = "Krank Ana Yatağı", StokAdedi = 90, Fiyati = 700L))

        parcaEkle(Parca(-1, 2, Adi = "Kapı Sacı", StokAdedi = 10, Fiyati = 300L))
        parcaEkle(Parca(-1, 2, Adi = "Travers", StokAdedi = 15, Fiyati = 1300L))
        parcaEkle(Parca(-1, 2, Adi = "Braket", StokAdedi = 90, Fiyati = 700L))

        parcaEkle(Parca(-1, 3, Adi = "Lambda Sensörü", StokAdedi = 15, Fiyati = 1300L))
        parcaEkle(Parca(-1, 4, Adi = "Pandizot", StokAdedi = 90, Fiyati = 700L))
    }

    fun parcalarByKategoriID(kid:Int) : MutableList<Parca> {
        val lst = mutableListOf<Parca>()

        val db = dbg.readableDatabase

        val cursor =  db.rawQuery("Select * from Parcalar Where KategoriID = ?", arrayOf(kid.toString()))

        while (cursor.moveToNext())
        {
            lst.add(
                Parca(cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getString(2),
                    cursor.getInt(3),
                    cursor.getLong(4)
                )
            )
        }

        db.close()
        cursor.close()
        return lst
    }
}
