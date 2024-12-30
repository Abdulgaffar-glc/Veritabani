import java.util.HashMap;
import java.util.Map;

public class AllowedTables {
    public static final Map<String, String> TABLE_COLUMN_MAP = new HashMap<>();

    static {
        TABLE_COLUMN_MAP.put("satis", "satisid");
        TABLE_COLUMN_MAP.put("alim", "alimid");
        TABLE_COLUMN_MAP.put("stok", "urunid");
        TABLE_COLUMN_MAP.put("uretim", "uretimid");
        TABLE_COLUMN_MAP.put("fire", "urunid");
        TABLE_COLUMN_MAP.put("iade", "iadeid");
        TABLE_COLUMN_MAP.put("hammadde", "urunid");
        TABLE_COLUMN_MAP.put("firma", "firmaid");
        TABLE_COLUMN_MAP.put("bayi", "bayiid");
    }
}
