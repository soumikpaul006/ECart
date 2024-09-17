import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Add the 'quantity' column to the existing 'product' table with a default value of 1
        database.execSQL("ALTER TABLE product ADD COLUMN quantity INTEGER DEFAULT 1 NOT NULL")
    }
}
