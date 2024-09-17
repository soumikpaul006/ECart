import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Add the 'quantity' column to the existing 'product' table with a default value of 1
        database.execSQL("ALTER TABLE product ADD COLUMN quantity INTEGER DEFAULT 1 NOT NULL")
    }
}


val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {

        database.execSQL("""
            CREATE TABLE IF NOT EXISTS address_table (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                userId TEXT NOT NULL,
                addressLine TEXT NOT NULL,
                city TEXT NOT NULL,
                state TEXT NOT NULL,
                zipCode TEXT NOT NULL
            )
        """.trimIndent())
    }
}
